package com.android.sjsofteducationapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.sjsofteducationapp.model.ImageDrag;
import com.meg7.widget.SvgImageView;

public class StudyActivity extends MasterActivity implements OnClickListener {
	private LinearLayout mainLayout, layoutContent;
	private SvgImageView image1, image2, image3, image4;
	private SvgImageView imageDrag1, imageDrag2, imageDrag3, imageDrag4;
	private ImageView imageContent;
	private ImageView back;
	private Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
	private ArrayList<SvgImageView> arrImage, arrImageDrag;
	private ArrayList<ImageDrag> imageDrags;
	private ArrayList<Integer> idRawSvgs;
	private ArrayList<Integer> idRawSvgRandoms;
	private long seed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_study);

		seed = System.nanoTime();
		
		createArraySvg();
		initView();
		createArrayImage();
		createArrayImageDrag();
	}

	private void initView() {
		mainLayout = (LinearLayout) findViewById(R.id.main_layout);
		layoutContent = (LinearLayout) findViewById(R.id.layout_content);
		imageContent = (ImageView) findViewById(R.id.image_content);
		back = (ImageView) findViewById(R.id.back);
		image1 = (SvgImageView) findViewById(R.id.image1);
		image2 = (SvgImageView) findViewById(R.id.image2);
		image3 = (SvgImageView) findViewById(R.id.image3);
		image4 = (SvgImageView) findViewById(R.id.image4);
		imageDrag1 = (SvgImageView) findViewById(R.id.image_drag1);
		imageDrag2 = (SvgImageView) findViewById(R.id.image_drag2);
		imageDrag3 = (SvgImageView) findViewById(R.id.image_drag3);
		imageDrag4 = (SvgImageView) findViewById(R.id.image_drag4);

		layoutContent.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						int width = layoutContent.getWidth();
						int height = layoutContent.getHeight() - 16;
						int pading = layoutContent.getPaddingTop();

						int marginTop = 30;
						int marginLeft = 60;

						int iWidth = height / 2 - (marginTop * 2);
						int iHeight = height / 2 - (marginTop * 2);

						bitmap1 = createImage(getBitmapFromView(imageContent),
								(width / 2) - iWidth - marginLeft,
								marginTop + 8, iWidth, iHeight);
						bitmap2 = createImage(getBitmapFromView(imageContent),
								(width / 2) + marginLeft, marginTop + 8,
								iWidth, iHeight);
						bitmap3 = createImage(getBitmapFromView(imageContent),
								(width / 2) - iWidth - marginLeft, (height / 2)
										+ marginTop + 8, iWidth, iHeight);
						bitmap4 = createImage(getBitmapFromView(imageContent),
								(width / 2) + marginLeft, (height / 2)
										+ marginTop + 8, iWidth, iHeight);

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
						params.topMargin = marginTop + 8;
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
						params2.topMargin = marginTop + 8;
						params2.leftMargin = marginLeft * 2;
						imageDrag2.setLayoutParams(params2);
						// /////
						LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
								iWidth, iHeight);
						params4.topMargin = marginTop;
						params4.leftMargin = marginLeft * 2;
						imageDrag4.setLayoutParams(params4);

						createView();
						layoutContent.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
		back.setOnClickListener(this);
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
			imageDrag.getImageView().setOnTouchListener(
					new View.OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
										Toast.makeText(StudyActivity.this,
												"success", Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									Toast.makeText(StudyActivity.this, "false",
											Toast.LENGTH_SHORT).show();
									// imageTouch.setVisibility(View.VISIBLE);
								}
								break;
							}
							return true;
						}
					});
		}
	}

	// kiem tra khi drag
	private boolean checkSuccess() {
		for (int i = 0; i < imageDrags.size(); i++) {
			if (!imageDrags.get(i).isDrag())
				return false;
		}
		return true;
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
		// Drawable bgDrawable =view.getBackground();
		// if (bgDrawable!=null)
		// bgDrawable.draw(canvas);
		// else
		// canvas.drawColor(Color.WHITE);
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

		default:
			break;
		}

	}
}
