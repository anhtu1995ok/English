package com.android.sjsofteducationapp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.android.sjsofteducationapp.model.ImageDrag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.meg7.widget.SvgImageView;

public class StudyActivity extends MasterActivity implements OnClickListener {
	private LinearLayout mainLayout, layoutContent;
	private FrameLayout frameContent;
	private ProgressBar progressLoadImage;
	private SvgImageView image1, image2, image3, image4;
	private SvgImageView imageDrag1, imageDrag2, imageDrag3, imageDrag4;
	private ImageView imageContent;
	private ImageView back, next;
	private Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
	private ArrayList<SvgImageView> arrImage, arrImageDrag;
	private ArrayList<ImageDrag> imageDrags;
	private ArrayList<Integer> idRawSvgs;
	private long seed;
	private File file;
	private ArrayList<String> arrFileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_study);
		createFiles();
		seed = System.nanoTime();

		arrFileName = new ArrayList<String>();
		arrFileName.add("image.jpg");
		arrFileName.add("image2.jpg");
		arrFileName.add("image3.jpg");
		Collections.shuffle(arrFileName, new Random(seed));
		file = loadFile(arrFileName.get(0));
		if (file == null)
			finish();
		createArraySvg();
		initView();
		createArrayImage();
		createArrayImageDrag();
	}

	private void initView() {
		mainLayout = (LinearLayout) findViewById(R.id.main_layout);
		frameContent = (FrameLayout) findViewById(R.id.frame_content);
		progressLoadImage = (ProgressBar) findViewById(R.id.progress_load_image);
		layoutContent = (LinearLayout) findViewById(R.id.layout_content);
		imageContent = (ImageView) findViewById(R.id.image_content);
		back = (ImageView) findViewById(R.id.back);
		next = (ImageView) findViewById(R.id.next);
		image1 = (SvgImageView) findViewById(R.id.image1);
		image2 = (SvgImageView) findViewById(R.id.image2);
		image3 = (SvgImageView) findViewById(R.id.image3);
		image4 = (SvgImageView) findViewById(R.id.image4);
		imageDrag1 = (SvgImageView) findViewById(R.id.image_drag1);
		imageDrag2 = (SvgImageView) findViewById(R.id.image_drag2);
		imageDrag3 = (SvgImageView) findViewById(R.id.image_drag3);
		imageDrag4 = (SvgImageView) findViewById(R.id.image_drag4);

		loadImage();
		back.setOnClickListener(this);
		next.setOnClickListener(this);
	}
	private void loadImage(){
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
		int height = layoutContent.getHeight() - 16;
		int pading = layoutContent.getPaddingTop();

		int marginTop = 30;
		int marginLeft = 60;

		int iWidth = height / 2 - (marginTop * 2);
		int iHeight = height / 2 - (marginTop * 2);

		bitmap1 = createImage(getBitmapFromView(imageContent), (width / 2)
				- iWidth - marginLeft, marginTop + 8, iWidth, iHeight);
		bitmap2 = createImage(getBitmapFromView(imageContent), (width / 2)
				+ marginLeft, marginTop + 8, iWidth, iHeight);
		bitmap3 = createImage(getBitmapFromView(imageContent), (width / 2)
				- iWidth - marginLeft, (height / 2) + marginTop + 8, iWidth,
				iHeight);
		bitmap4 = createImage(getBitmapFromView(imageContent), (width / 2)
				+ marginLeft, (height / 2) + marginTop + 8, iWidth, iHeight);

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
			Log.d("TuNT", "id raw: "+imageDrags.get(p).getIdRawDrag());
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
										next();
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
	
	private void next(){
		Collections.shuffle(idRawSvgs, new Random(seed));
		Collections.shuffle(arrImage, new Random(seed));
		Collections.shuffle(arrImageDrag, new Random(seed));
		
		for(int i = 0; i<imageDrags.size(); i++){
			ImageDrag imageDrag = imageDrags.get(i);
//			imageDrag.getImageView().setImageBitmap(imageDrag.getBitmap());
//			imageDrag.getImageView()
//					.setSvgRaw(imageDrags.get(i).getIdRawDrag());
//			imageDrag.getDragImageView().setSvgRaw(
//					imageDrags.get(i).getIdRawDrag());
			
			imageDrag.getImageView().setVisibility(View.VISIBLE);
			imageDrag.getDragImageView().setVisibility(View.VISIBLE);
			imageDrag.setDrag(false);
		}
		
		Collections.shuffle(arrFileName, new Random(seed));
		file = loadFile(arrFileName.get(0));
		if (file == null)
			finish();
		loadImage();
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
		File myDir = new File(root + "/english");
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
		// Drawable bgDrawable =view.getBackground();
		// if (bgDrawable!=null)
		// bgDrawable.draw(canvas);
		// else
		// canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		return returnedBitmap;
	}
	
	private void createFiles() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/english");
        myDir.mkdirs();
        String fname = "image.jpg";
        String fname2 = "image2.jpg";
        String fname3 = "image3.jpg";
        File file = new File(myDir, fname);
        if (!file.exists()) {
            try {
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.image)).getBitmap();
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        File file2 = new File(myDir, fname2);
        if (!file2.exists()) {
            try {
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.image2)).getBitmap();
                FileOutputStream out = new FileOutputStream(file2);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        File file3 = new File(myDir, fname3);
        if (!file3.exists()) {
            try {
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.image3)).getBitmap();
                FileOutputStream out = new FileOutputStream(file3);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.next:
			next();
			break;
		default:
			break;
		}

	}
}
