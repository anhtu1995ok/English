package com.android.sjsofteducationapp;

import java.util.ArrayList;

import com.android.sjsofteducationapp.model.ImageDrag;
import com.meg7.widget.SvgImageView;

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

public class StudyActivity extends MasterActivity implements OnClickListener {
	private LinearLayout mainLayout, layoutContent;
	private SvgImageView image1, image2, image3, image4;
	private SvgImageView imageDrag1, imageDrag2, imageDrag3, imageDrag4;
	private ImageView imageContent;
	private ImageView back;
	private ArrayList<ImageDrag> imageDrags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_study);

		initView();
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
		// mainLayout.setOnDragListener(new OnDragListener() {
		//
		// @Override
		// public boolean onDrag(View arg0, DragEvent arg1) {
		// if (arg1.getAction() == DragEvent.ACTION_DROP) {
		// imageTouch.setVisibility(View.VISIBLE);
		// return true;
		// }
		// return false;
		// }
		// });
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

						Bitmap bitmap1 = createImage(getBitmapFromView(imageContent), (width / 2) - iWidth - marginLeft, marginTop + 8, iWidth, iHeight);
						image1.setImageBitmap(bitmap1);
						Bitmap bitmap2 = createImage(getBitmapFromView(imageContent), (width / 2) + marginLeft, marginTop + 8, iWidth, iHeight);
						image2.setImageBitmap(bitmap2);
						Bitmap bitmap3 = createImage(getBitmapFromView(imageContent), (width / 2) - iWidth - marginLeft, (height/2) + marginTop + 8, iWidth, iHeight);
						image3.setImageBitmap(bitmap3);
						Bitmap bitmap4 = createImage(getBitmapFromView(imageContent), (width / 2) + marginLeft, (height/2) + marginTop + 8, iWidth, iHeight);
						image4.setImageBitmap(bitmap4);
						
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

						// Log.d("TuNT", "w" + layoutContent.getWidth());
						// Log.d("TuNT", "h" + layoutContent.getHeight());
						// Log.d("TuNT", "x" + layoutContent.getX());
						// Log.d("TuNT", "y" + layoutContent.getY());

						layoutContent.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
		back.setOnClickListener(this);
		imageDrags = new ArrayList<ImageDrag>();
		imageDrags.add(new ImageDrag(image1, imageDrag1, R.raw.shape_circle_2, 0));
		imageDrags.add(new ImageDrag(image2, imageDrag2, R.raw.shape_flower, 1));
		imageDrags.add(new ImageDrag(image3, imageDrag3, R.raw.shape_heart, 2));
		imageDrags.add(new ImageDrag(image4, imageDrag4, R.raw.shape_star, 3));
		for (int i = 0; i < imageDrags.size(); i++) {
			final int p = i;
			imageDrags.get(p).getImageView().setSvgRaw(imageDrags.get(p).getIdRawDrag());
			imageDrags.get(p).getDragImageView().setSvgRaw(imageDrags.get(p).getIdRawDrag());
			imageDrags.get(p).getImageView()
					.setOnTouchListener(new View.OnTouchListener() {
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

			imageDrags.get(p).getDragImageView()
					.setOnDragListener(new View.OnDragListener() {
						@Override
						public boolean onDrag(View v, DragEvent event) {
							switch (event.getAction()) {
							case DragEvent.ACTION_DROP:
								if (((Integer) event.getLocalState()) == imageDrags
										.get(p).getPosition()) {
									imageDrags.get(p).getDragImageView()
											.setVisibility(View.INVISIBLE);
									imageDrags.get(p).getImageView()
											.setVisibility(View.INVISIBLE);
									imageDrags.get(p).setDrag(true);
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
	
	private Bitmap createImage(Bitmap bitmap, int startX, int startY, int width, int height){
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, startX, startY, width, height);
		return newBitmap;
	}

	 public static Bitmap getBitmapFromView(View view) {
	        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(returnedBitmap);
//	        Drawable bgDrawable =view.getBackground();
//	        if (bgDrawable!=null) 
//	            bgDrawable.draw(canvas);
//	        else 
//	            canvas.drawColor(Color.WHITE);
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
