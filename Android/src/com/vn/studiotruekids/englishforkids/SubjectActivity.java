package com.vn.studiotruekids.englishforkids;

import java.io.File;
import java.util.ArrayList;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.vn.studiotruekids.englishforkids.adapter.SubjectAdapter;
import com.vn.studiotruekids.englishforkids.model.Home;
import com.vn.studiotruekids.englishforkids.utils.GetDataFromDB;
import com.vn.studiotruekids.englishforkids.utils.Sound;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import it.sephiroth.android.library.widget.HListView;

public class SubjectActivity extends MasterActivity
		implements OnClickListener, it.sephiroth.android.library.widget.AdapterView.OnItemClickListener {

	private HListView listView;
	private SubjectAdapter adapter;
	private ArrayList<Home> data;
	private ImageView image, leftArrow, rightArrow, home;

	private String title, bg_image;
	private GetDataFromDB gdfdb;

	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);

		mAdView = (AdView) findViewById(R.id.adView);

		Intent intent = getIntent();
		title = intent.getStringExtra("HOME_TITLE");
		bg_image = intent.getStringExtra("HOME_BG");

		if (title == null) {
			finish();
		}

		new initData().execute("");

		listView = (HListView) findViewById(R.id.listView);

		image = (ImageView) findViewById(R.id.image);
		String fileImage = Environment.getExternalStorageDirectory() + "/Sjsoft/Home/Content/" + bg_image + ".jsc";
		File file = new File(fileImage);
		if (file.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			image.setImageBitmap(myBitmap);
		}

		leftArrow = (ImageView) findViewById(R.id.leftarrow);
		rightArrow = (ImageView) findViewById(R.id.rightarrow);
		home = (ImageView) findViewById(R.id.home);
		image.setOnClickListener(this);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
		home.setOnClickListener(this);
		listView.setOnItemClickListener(this);
	}

	private class initData extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			gdfdb = new GetDataFromDB(getApplicationContext());
			data = gdfdb.getDataFromDB(title.toLowerCase());
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter = new SubjectAdapter(getApplicationContext(), R.layout.item_subject, data, title);
			listView.setAdapter(adapter);
			listView.setSelector(R.drawable.listview_onclick);
			super.onPostExecute(result);
		}

	}

	@Override
	protected void onResume() {
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		if (adapter != null)
			if (gdfdb != null) {
				data = gdfdb.getDataFromDB(title.toLowerCase());
				this.adapter.notifyDataSetChanged(data);
			}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// int width = 0;
		// int lWidth = 0;
		// int marginInPX = 25;
		// if(android.os.Build.VERSION.SDK_INT <= 10) {
		// Display display = getWindowManager().getDefaultDisplay();
		// width = display.getWidth();
		// lWidth = - width;
		// } else {
		// DisplayMetrics metrics = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// width = metrics.widthPixels + marginInPX;
		// lWidth = - metrics.widthPixels - marginInPX;
		// }
		int mWidth = listView.getMeasuredWidth();
		switch (v.getId()) {
		case R.id.image:
			break;
		case R.id.leftarrow:
			Sound.playSound(Sound.SOUND_BUTTON_ONCLICK);
			YoYo.with(Techniques.BounceInLeft).playOn(leftArrow);
			listView.smoothScrollBy(-mWidth, 1000);
			break;
		case R.id.rightarrow:
			Sound.playSound(Sound.SOUND_BUTTON_ONCLICK);
			YoYo.with(Techniques.BounceInRight).playOn(rightArrow);
			listView.smoothScrollBy(mWidth, 1000);
			break;
		case R.id.home:
			Sound.playSound(Sound.SOUND_BUTTON_ONCLICK);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position,
			long id) {
		Sound.playSound(Sound.SOUND_BUTTON_ONCLICK);
		view.startAnimation(AnimationUtils.loadAnimation(SubjectActivity.this, R.anim.abc_fade_in));
		// Home home = (Home) parent.getAdapter().getItem(position);
		Intent intent = new Intent(SubjectActivity.this, StudyActivity.class);
		// intent.putExtra("SUBJECT", home);
		intent.putExtra("HOME_BG", bg_image);
		intent.putExtra("TITLE", title);
		intent.putExtra("POSITION", position);
		startActivity(intent);
	}

}
