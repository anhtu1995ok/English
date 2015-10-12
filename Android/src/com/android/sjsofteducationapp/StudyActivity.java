package com.android.sjsofteducationapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import com.android.sjsofteducationapp.database.EducationDBControler;
import com.android.sjsofteducationapp.model.Home;
import com.android.sjsofteducationapp.model.ImageDrag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.meg7.widget.SvgImageView;
import com.plattysoft.leonids.ParticleSystem;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StudyActivity extends Activity implements OnClickListener {
	private LinearLayout mainLayout, layoutContent;
	private FrameLayout frameContent;
	private ProgressBar progressLoadImage;
	private SvgImageView image1, image2, image3, image4;
	private SvgImageView imageDrag1, imageDrag2, imageDrag3, imageDrag4;
	private ImageView imageContent;
	private ImageView back, replay;
	private TextView tvTitle;
	private Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
	private ArrayList<SvgImageView> arrImage, arrImageDrag;
	private ArrayList<ImageDrag> imageDrags;
	private ArrayList<Integer> idRawSvgs;
	private long seed;
	private File file;
	private MediaPlayer ringSuccess, ringError, ringTouch;
	private TextToSpeech textToSpeech;
	private String textSpeech;
	
	private EducationDBControler db;
	Home home;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_study);

		Intent intent = getIntent();
		home = (Home) intent.getSerializableExtra("SUBJECT");
		if (home == null)
			finish();
		textSpeech = home.getTitle();

		textToSpeech = new TextToSpeech(StudyActivity.this,
				new OnInitListener() {

					@Override
					public void onInit(int status) {
						if (status != TextToSpeech.ERROR) {
							textToSpeech.setLanguage(Locale.ENGLISH);
						}
					}
				});

		ringSuccess = MediaPlayer.create(StudyActivity.this,
				R.raw.cartoon_slide_whistle_ascend_version_2);
		ringTouch = MediaPlayer.create(StudyActivity.this,
				R.raw.comedy_pop_finger_in_mouth_001);
		ringError = MediaPlayer.create(StudyActivity.this, R.raw.ring3);
		seed = System.nanoTime();

		file = loadFile(home.getContent_image());
		if (file == null)
			finish();
		createArraySvg();
		initView();
		createArrayImage();
		createArrayImageDrag();
		
		db = EducationDBControler.getInstance(StudyActivity.this);
	}

	@Override
	protected void onPause() {
		textToSpeech.stop();
		super.onPause();
	}

	private void initView() {
		mainLayout = (LinearLayout) findViewById(R.id.main_layout);
		frameContent = (FrameLayout) findViewById(R.id.frame_content);
		progressLoadImage = (ProgressBar) findViewById(R.id.progress_load_image);
		layoutContent = (LinearLayout) findViewById(R.id.layout_content);
		imageContent = (ImageView) findViewById(R.id.image_content);
		back = (ImageView) findViewById(R.id.back);
		replay = (ImageView) findViewById(R.id.replay);
		tvTitle = (TextView) findViewById(R.id.title);
		image1 = (SvgImageView) findViewById(R.id.image1);
		image2 = (SvgImageView) findViewById(R.id.image2);
		image3 = (SvgImageView) findViewById(R.id.image3);
		image4 = (SvgImageView) findViewById(R.id.image4);
		imageDrag1 = (SvgImageView) findViewById(R.id.image_drag1);
		imageDrag2 = (SvgImageView) findViewById(R.id.image_drag2);
		imageDrag3 = (SvgImageView) findViewById(R.id.image_drag3);
		imageDrag4 = (SvgImageView) findViewById(R.id.image_drag4);

		tvTitle.setText(textSpeech);
		tvTitle.setVisibility(View.INVISIBLE);
		replay.setVisibility(View.INVISIBLE);
		loadImage();
		back.setOnClickListener(this);
		replay.setOnClickListener(this);
	}

	private void loadImage() {
		frameContent.setVisibility(View.INVISIBLE);
		progressLoadImage.setVisibility(View.VISIBLE);
		Glide.with(StudyActivity.this).load(file)
				.into(new GlideDrawableImageViewTarget(imageContent) {
					@Override
					public void onResourceReady(GlideDrawable drawable,
							GlideAnimation anim) {
						super.onResourceReady(drawable, anim);
						frameContent.setVisibility(View.VISIBLE);
						progressLoadImage.setVisibility(View.INVISIBLE);
						layoutSize();
					}

					@Override
					public void onLoadFailed(Exception e, Drawable errorDrawable) {
						super.onLoadFailed(e, errorDrawable);
					}
				});
	}

	private void layoutSize() {
		int width = layoutContent.getWidth();
		int height = layoutContent.getHeight();
		int pading = 16;

		int marginTop = 20;
		int marginLeft = 40;

		int iWidth = height / 2 - (marginTop * 2);
		int iHeight = height / 2
				- ((marginTop * 2) + imageContent.getPaddingBottom());

		bitmap1 = createImage(getBitmapFromView(imageContent), (width / 2)
				- iWidth - marginLeft, marginTop + (pading / 2), iWidth,
				iHeight);
		bitmap2 = createImage(getBitmapFromView(imageContent), (width / 2)
				+ marginLeft, marginTop + (pading / 2), iWidth, iHeight);
		bitmap3 = createImage(getBitmapFromView(imageContent), (width / 2)
				- iWidth - marginLeft, (height / 2) + marginTop + (pading / 2),
				iWidth, iHeight);
		bitmap4 = createImage(getBitmapFromView(imageContent), (width / 2)
				+ marginLeft, (height / 2) + marginTop + (pading / 2), iWidth,
				iHeight);

		LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
				iWidth, iHeight);
		params5.topMargin = marginTop;
		params5.bottomMargin = marginTop;
		image1.setLayoutParams(params5);
		image2.setLayoutParams(params5);
		image3.setLayoutParams(params5);
		image4.setLayoutParams(params5);
		// /////
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				iWidth, iHeight);
		params.topMargin = marginTop + (pading / 2);
		params.leftMargin = (width / 2) - iWidth - marginLeft;
		imageDrag1.setLayoutParams(params);
		// /////
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				iWidth, iHeight);
		params3.topMargin = marginTop;
		params3.leftMargin = (width / 2) - iWidth - marginLeft;
		imageDrag3.setLayoutParams(params3);
		// /////
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				iWidth, iHeight);
		params2.topMargin = marginTop + (pading / 2);
		params2.leftMargin = marginLeft * 2;
		imageDrag2.setLayoutParams(params2);
		// /////
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
				iWidth, iHeight);
		params4.topMargin = marginTop;
		params4.leftMargin = marginLeft * 2;
		imageDrag4.setLayoutParams(params4);

		createView();
	}

	// tao array svg
	private void createArraySvg() {
		idRawSvgs = new ArrayList<Integer>();
		idRawSvgs.add(R.raw.shape_5);
		idRawSvgs.add(R.raw.shape_circle_2);
		idRawSvgs.add(R.raw.shape_flower);
		idRawSvgs.add(R.raw.shape_heart);
		idRawSvgs.add(R.raw.shape_star);
		idRawSvgs.add(R.raw.shape_star_2);
		idRawSvgs.add(R.raw.shape_star_3);
		Collections.shuffle(idRawSvgs, new Random(seed));
	}

	private void createArrayImage() {
		arrImage = new ArrayList<SvgImageView>();
		arrImage.add(image1);
		arrImage.add(image2);
		arrImage.add(image3);
		arrImage.add(image4);
		Collections.shuffle(arrImage, new Random(seed));
	}

	private void createArrayImageDrag() {
		arrImageDrag = new ArrayList<SvgImageView>();
		arrImageDrag.add(imageDrag1);
		arrImageDrag.add(imageDrag2);
		arrImageDrag.add(imageDrag3);
		arrImageDrag.add(imageDrag4);
		Collections.shuffle(arrImageDrag, new Random(seed));
	}

	// khoi tao view
	private void createView() {
		imageDrags = new ArrayList<ImageDrag>();
		imageDrags.add(new ImageDrag(arrImage.get(0), imageDrag1, bitmap1,
				idRawSvgs.get(0), 0));
		imageDrags.add(new ImageDrag(arrImage.get(1), imageDrag2, bitmap2,
				idRawSvgs.get(1), 1));
		imageDrags.add(new ImageDrag(arrImage.get(2), imageDrag3, bitmap3,
				idRawSvgs.get(2), 2));
		imageDrags.add(new ImageDrag(arrImage.get(3), imageDrag4, bitmap4,
				idRawSvgs.get(3), 3));
		for (int i = 0; i < imageDrags.size(); i++) {
			final int p = i;
			final ImageDrag imageDrag = imageDrags.get(p);
			imageDrag.getImageView().setImageBitmap(imageDrag.getBitmap());
			imageDrag.getImageView()
					.setSvgRaw(imageDrags.get(p).getIdRawDrag());
			imageDrag.getDragImageView().setSvgRaw(
					imageDrags.get(p).getIdRawDrag());
			imageDrag.getDragImageView().invalidate();
			imageDrag.getImageView().setOnTouchListener(
					new View.OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (event.getAction() == MotionEvent.ACTION_DOWN) {
								ringTouch.start();
								ClipData data = ClipData.newPlainText("", "");
								View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
										v);
								v.startDrag(data, shadowBuilder, imageDrags
										.get(p).getPosition(), 0);
								return true;
							} else {
								return false;
							}
						}
					});

			imageDrag.getDragImageView().setOnDragListener(
					new View.OnDragListener() {
						@Override
						public boolean onDrag(View v, DragEvent event) {
							switch (event.getAction()) {
							case DragEvent.ACTION_DROP:
								if (((Integer) event.getLocalState()) == imageDrag
										.getPosition()) {
									imageDrag.getDragImageView().setVisibility(
											View.INVISIBLE);
									imageDrag.getImageView().setVisibility(
											View.INVISIBLE);
									imageDrag.setDrag(true);

									if (checkSuccess()) {
										new ParticleSystem(StudyActivity.this,
												100,
												R.drawable.ic_star_dropdown,
												800).setSpeedRange(0.1f, 0.25f)
												.oneShot(imageContent, 50);
										new ParticleSystem(StudyActivity.this,
												100,
												R.drawable.ic_star_dropdown,
												800).setSpeedRange(0.1f, 0.25f)
												.oneShot(imageDrag1, 50);
										new ParticleSystem(StudyActivity.this,
												100,
												R.drawable.ic_star_dropdown,
												800).setSpeedRange(0.1f, 0.25f)
												.oneShot(imageDrag2, 50);
										new ParticleSystem(StudyActivity.this,
												100,
												R.drawable.ic_star_dropdown,
												800).setSpeedRange(0.1f, 0.25f)
												.oneShot(imageDrag3, 50);
										new ParticleSystem(StudyActivity.this,
												100,
												R.drawable.ic_star_dropdown,
												800).setSpeedRange(0.1f, 0.25f)
												.oneShot(imageDrag4, 50);

										textToSpeech.speak(textSpeech,
												TextToSpeech.QUEUE_FLUSH, null);
										YoYo.with(Techniques.DropOut).playOn(
												tvTitle);
										tvTitle.setVisibility(View.VISIBLE);
										replay.setVisibility(View.VISIBLE);
										
										db.setSuccess(home.getId());
									} else {
										new ParticleSystem(StudyActivity.this,
												100,
												R.drawable.ic_star_dropdown,
												800).setSpeedRange(0.1f, 0.25f)
												.oneShot(v, 50);
										ringSuccess.start();
									}
								} else {
									YoYo.with(Techniques.Swing).playOn(v);
								}
								break;
							}
							return true;
						}
					});
		}
	}

	private void replay() {
		textToSpeech.speak(textSpeech, TextToSpeech.QUEUE_FLUSH, null);
	}

	// kiem tra khi drag
	private boolean checkSuccess() {
		for (int i = 0; i < imageDrags.size(); i++) {
			if (!imageDrags.get(i).isDrag())
				return false;
		}
		return true;
	}

	private File loadFile(String fileName) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/Sjsoft/ContentImage/");
		File file = new File(myDir, fileName);
		return file;
	}

	private Bitmap createImage(Bitmap bitmap, int startX, int startY,
			int width, int height) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, startX, startY, width,
				height);
		return newBitmap;
	}

	public static Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),
				view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		view.draw(canvas);
		return returnedBitmap;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.replay:
			replay();
			break;
		default:
			break;
		}

	}
}
