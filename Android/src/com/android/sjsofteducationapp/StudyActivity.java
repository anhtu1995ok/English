package com.android.sjsofteducationapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.sjsofteducationapp.model.ImageDrag;

public class StudyActivity extends Activity implements OnClickListener {
	private LinearLayout mainLayout, layoutContent;
	private ImageView image1, image2, image3, image4;
	private ImageView imageDrag1, imageDrag2, imageDrag3, imageDrag4;
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
		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);
		image3 = (ImageView) findViewById(R.id.image3);
		image4 = (ImageView) findViewById(R.id.image4);
		imageDrag1 = (ImageView) findViewById(R.id.image_drag1);
		imageDrag2 = (ImageView) findViewById(R.id.image_drag2);
		imageDrag3 = (ImageView) findViewById(R.id.image_drag3);
		imageDrag4 = (ImageView) findViewById(R.id.image_drag4);
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
		imageDrags.add(new ImageDrag(image1, imageDrag1, R.drawable.img1,
				R.drawable.img_drag_1, 0));
		imageDrags.add(new ImageDrag(image2, imageDrag2, R.drawable.img2,
				R.drawable.img_drag_1, 1));
		imageDrags.add(new ImageDrag(image3, imageDrag4, R.drawable.img4,
				R.drawable.img_drag_1, 2));
		imageDrags.add(new ImageDrag(image4, imageDrag3, R.drawable.img3,
				R.drawable.img_drag_1, 3));

		for (int i = 0; i < imageDrags.size(); i++) {
			final int p = i;
			imageDrags.get(p).getImageView()
					.setImageResource(imageDrags.get(p).getIdDrawable());
			imageDrags.get(p).getImageView()
					.setOnTouchListener(new View.OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (event.getAction() == MotionEvent.ACTION_DOWN) {
								// imageTouch =
								// imageDrags.get(p).getImageView();
								// imageTouch.setVisibility(View.INVISIBLE);
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
									// Glide.with(DragImageActivity.this).load(imageDrags.get(p).getFile()).into(dragImages.get(p).getDragImageView());
									imageDrags.get(p).getDragImageView()
											.setVisibility(View.INVISIBLE);
									// imageDrags.get(p).getDragImageView().setImageResource(imageDrags.get(p).getIdDrawable());
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
