package com.android.sjsofteducationapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import com.android.sjsofteducationapp.database.EducationDBControler;
import com.android.sjsofteducationapp.model.Home;
import com.android.sjsofteducationapp.model.ImageDrag;
import com.android.sjsofteducationapp.utils.GetDataFromDB;
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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
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
	private LinearLayout layoutContent;
	private FrameLayout frameContent;
	private ProgressBar progressLoadImage;
	private SvgImageView image1, image2, image3, image4;
	private SvgImageView imageDrag1, imageDrag2, imageDrag3, imageDrag4;
	private ImageView imageContent;
	private ImageView back, replay, bottom_image, next;
	private TextView tvTitle;
	private Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
	private ArrayList<SvgImageView> arrImage, arrImageDrag;
	private ArrayList<ImageDrag> imageDrags;
	private ArrayList<Integer> idRawSvgs;
	private long seed;
	private File file;
	private MediaPlayer mpOnClick;
	private TextToSpeech textToSpeech;
	private String textSpeech, bg_image;
	//
	private EducationDBControler db;
	Home home;
	private ArrayList<Home> data;
	String title;
	int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_study);

		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				Intent intent = getIntent();
				title = intent.getStringExtra("TITLE");
				GetDataFromDB gdfdb = new GetDataFromDB(getApplicationContext());
				data = gdfdb.getDataFromDB(title.toLowerCase());
				position = intent.getIntExtra("POSITION", 0);
				bg_image = intent.getStringExtra("HOME_BG");
				home = data.get(position);
				if (home == null) {
					finish();
				}

				textSpeech = home.getTitle();
				textToSpeech = new TextToSpeech(StudyActivity.this, new OnInitListener() {

					@Override
					public void onInit(int status) {
						if (status != TextToSpeech.ERROR) {
							textToSpeech.setLanguage(Locale.ENGLISH);
						}
					}
				});
				file = loadFile(home.getContent_image());
				if (file == null) {
					finish();
				}

				seed = System.nanoTime();
				initView();
				loadImage();
				db = EducationDBControler.getInstance(StudyActivity.this);
			}
		});

	}

	@Override
	protected void onPause() {
		if (textToSpeech != null) {
			if (textToSpeech.isSpeaking()) {
				textToSpeech.stop();
			}
		}
		super.onPause();
	}

	private void initView() {
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
		bottom_image = (ImageView) findViewById(R.id.bottom_image);

		next = (ImageView) findViewById(R.id.rightarrow);
		next.setOnClickListener(this);

		try {
			Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/brlnsb.ttf");
			tvTitle.setTypeface(type);
			tvTitle.setText(textSpeech);
		} catch (Exception e) {
		}

		tvTitle.setVisibility(View.INVISIBLE);
		replay.setVisibility(View.INVISIBLE);

		frameContent.setVisibility(View.INVISIBLE);
		progressLoadImage.setVisibility(View.VISIBLE);

		back.setOnClickListener(this);
		replay.setOnClickListener(this);
	}

	private void loadImage() {
		Glide.with(StudyActivity.this).load(file).into(new GlideDrawableImageViewTarget(imageContent) {
			@Override
			public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
				super.onResourceReady(drawable, anim);
				new LoadContent();
			}

			@Override
			public void onLoadFailed(Exception e, Drawable errorDrawable) {
				super.onLoadFailed(e, errorDrawable);
			}
		});
	}

	// tao array svg
	private void createArraySvg() {
		idRawSvgs = new ArrayList<Integer>();
		idRawSvgs.add(R.raw.shape3);
		idRawSvgs.add(R.raw.shape4);
		idRawSvgs.add(R.raw.shape8);
		idRawSvgs.add(R.raw.shape9);
		idRawSvgs.add(R.raw.shape10);
		idRawSvgs.add(R.raw.shape11);
		idRawSvgs.add(R.raw.shape12);
		idRawSvgs.add(R.raw.shape13);
		idRawSvgs.add(R.raw.shape14);
		idRawSvgs.add(R.raw.shape15);
		idRawSvgs.add(R.raw.shape17);
		idRawSvgs.add(R.raw.shape18);
		idRawSvgs.add(R.raw.shape20);
		idRawSvgs.add(R.raw.shape21);
		idRawSvgs.add(R.raw.shape22);
		idRawSvgs.add(R.raw.shape23);
		idRawSvgs.add(R.raw.shape24);
		idRawSvgs.add(R.raw.shape25);
		idRawSvgs.add(R.raw.shape26);
		idRawSvgs.add(R.raw.shape27);
		idRawSvgs.add(R.raw.shape28);
		idRawSvgs.add(R.raw.shape29);
		idRawSvgs.add(R.raw.shape30);
		idRawSvgs.add(R.raw.shape32);
		idRawSvgs.add(R.raw.shape34);
		idRawSvgs.add(R.raw.shape35);
		idRawSvgs.add(R.raw.shape36);
		idRawSvgs.add(R.raw.shape37);
		idRawSvgs.add(R.raw.shape38);
		idRawSvgs.add(R.raw.shape39);
		idRawSvgs.add(R.raw.shape40);

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
		imageDrags.add(new ImageDrag(arrImage.get(0), imageDrag1, bitmap1, idRawSvgs.get(0), 0));
		imageDrags.add(new ImageDrag(arrImage.get(1), imageDrag2, bitmap2, idRawSvgs.get(1), 1));
		imageDrags.add(new ImageDrag(arrImage.get(2), imageDrag3, bitmap3, idRawSvgs.get(2), 2));
		imageDrags.add(new ImageDrag(arrImage.get(3), imageDrag4, bitmap4, idRawSvgs.get(3), 3));
		for (int i = 0; i < imageDrags.size(); i++) {
			final int p = i;
			final ImageDrag imageDrag = imageDrags.get(p);
			imageDrag.getImageView().setImageBitmap(imageDrag.getBitmap());
			imageDrag.getImageView().setSvgRaw(imageDrags.get(p).getIdRawDrag());
			imageDrag.getDragImageView().setSvgRaw(imageDrags.get(p).getIdRawDrag());
			imageDrag.getDragImageView().invalidate();
			imageDrag.getImageView().setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						startMussicOnclick(R.raw.comedy_pop_finger_in_mouth_001);
						// ringTouch.start();
						ClipData data = ClipData.newPlainText("", "");
						View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
						v.startDrag(data, shadowBuilder, imageDrags.get(p).getPosition(), 0);
						return true;
					} else {
						return false;
					}
				}
			});

			imageDrag.getDragImageView().setOnDragListener(new View.OnDragListener() {
				@Override
				public boolean onDrag(View v, DragEvent event) {
					switch (event.getAction()) {
					case DragEvent.ACTION_DROP:
						if (((Integer) event.getLocalState()) == imageDrag.getPosition()) {
							imageDrag.getDragImageView().setVisibility(View.INVISIBLE);
							imageDrag.getImageView().setVisibility(View.INVISIBLE);
							imageDrag.setDrag(true);

							if (checkSuccess()) {
								new ParticleSystem(StudyActivity.this, 100, R.drawable.ic_star_dropdown, 800)
										.setSpeedRange(0.1f, 0.25f).oneShot(imageContent, 50);
								new ParticleSystem(StudyActivity.this, 100, R.drawable.ic_star_dropdown, 800)
										.setSpeedRange(0.1f, 0.25f).oneShot(imageDrag1, 50);
								new ParticleSystem(StudyActivity.this, 100, R.drawable.ic_star_dropdown, 800)
										.setSpeedRange(0.1f, 0.25f).oneShot(imageDrag2, 50);
								new ParticleSystem(StudyActivity.this, 100, R.drawable.ic_star_dropdown, 800)
										.setSpeedRange(0.1f, 0.25f).oneShot(imageDrag3, 50);
								new ParticleSystem(StudyActivity.this, 100, R.drawable.ic_star_dropdown, 800)
										.setSpeedRange(0.1f, 0.25f).oneShot(imageDrag4, 50);
								if (mpOnClick != null)
									if (mpOnClick.isPlaying())
										mpOnClick.stop();
								textToSpeech.speak(textSpeech, TextToSpeech.QUEUE_FLUSH, null);
								YoYo.with(Techniques.DropOut).playOn(tvTitle);
								tvTitle.setVisibility(View.VISIBLE);
								replay.setVisibility(View.VISIBLE);

								image1.setVisibility(View.GONE);
								image2.setVisibility(View.GONE);
								image3.setVisibility(View.GONE);
								image4.setVisibility(View.GONE);

								if (position < data.size() - 1) {
									YoYo.with(Techniques.BounceIn).playOn(next);
									next.setVisibility(View.VISIBLE);
								}

								db.setSuccess(home.getId());
							} else {
								new ParticleSystem(StudyActivity.this, 100, R.drawable.ic_star_dropdown, 800)
										.setSpeedRange(0.1f, 0.25f).oneShot(v, 50);
								// ringSuccess.start();
								startMussicOnclick(R.raw.cartoon_slide_whistle_ascend_version_2);
							}
						} else {
							YoYo.with(Techniques.Swing).playOn(v);
						}
						// break;
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
		File myDir = new File(root + "/Sjsoft/Subject/Content/" + title + "/");
		File file = new File(myDir, fileName);
		return file;
	}

	private Bitmap createImage(Bitmap bitmap, int startX, int startY, int width, int height) {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, startX, startY, width, height);
		return newBitmap;
	}

	public static Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
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

		case R.id.rightarrow:
			YoYo.with(Techniques.Bounce).playOn(next);
			Intent intent = new Intent(this, StudyActivity.class);
			intent.putExtra("HOME_BG", bg_image);
			intent.putExtra("TITLE", title);
			intent.putExtra("POSITION", position + 1);
			finish();
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private class LoadContent extends AsyncTask<Void, Void, Void> {
		File file;

		public LoadContent() {
			execute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			String fileImage = Environment.getExternalStorageDirectory() + "/Sjsoft/Home/Content/" + bg_image;
			file = new File(fileImage);

			createArraySvg();
			createArrayImage();
			createArrayImageDrag();

			Bitmap bitmap = getBitmapFromView(imageContent);
			FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) imageDrag1.getLayoutParams();
			int x1 = (int) imageDrag1.getX() + imageContent.getPaddingLeft();
			int y1 = layoutContent.getPaddingTop();
			bitmap1 = createImage(bitmap, x1, y1, imageDrag1.getWidth(), imageDrag1.getHeight());
			int x2 = (int) (x1 + imageDrag1.getWidth() + layoutParams1.rightMargin + imageDrag2.getX());
			bitmap2 = createImage(bitmap, x2, layoutContent.getPaddingTop(), imageDrag2.getWidth(),
					imageDrag2.getHeight());
			FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) imageDrag3.getLayoutParams();
			int y3 = y1 + imageDrag1.getHeight() + layoutParams1.bottomMargin + layoutParams3.topMargin;
			bitmap3 = createImage(bitmap, x1, y3, imageDrag3.getWidth(), imageDrag3.getHeight());
			bitmap4 = createImage(bitmap, x2, y3, imageDrag4.getWidth(), imageDrag4.getHeight());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Glide.with(StudyActivity.this).load(file).into(bottom_image);
			frameContent.setVisibility(View.VISIBLE);
			progressLoadImage.setVisibility(View.INVISIBLE);
			createView();
			super.onPostExecute(result);
		}
	}
	Thread thread;
	private void startMussicOnclick(int raw) {
		final int r = raw;
		
		if (mpOnClick != null)
			if (mpOnClick.isPlaying())
				mpOnClick.stop();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				mpOnClick = MediaPlayer.create(StudyActivity.this, r);
				mpOnClick.start();
			}
		};
		thread = new Thread(runnable);
		thread.start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mpOnClick != null)
			if (mpOnClick.isPlaying())
				mpOnClick.stop();
	}
}
