package com.android.sjsofteducationapp;

import java.io.File;
import java.util.ArrayList;

import com.android.sjsofteducationapp.adapter.SubjectAdapter;
import com.android.sjsofteducationapp.model.Home;
import com.android.sjsofteducationapp.utils.GetDataFromDB;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);

		Intent intent = getIntent();
		title = intent.getStringExtra("HOME_TITLE");
		bg_image = intent.getStringExtra("HOME_BG");

		if (title == null) {
			finish();
		}

		new initData().execute("");
		// initData();

		listView = (HListView) findViewById(R.id.listView);

		image = (ImageView) findViewById(R.id.image);
		String fileImage = Environment.getExternalStorageDirectory() + "/Sjsoft/Home/Content/" + bg_image;
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
			GetDataFromDB gdfdb = new GetDataFromDB(getApplicationContext());
			data = gdfdb.getDataFromDB(title.toLowerCase());
			Log.d("ToanNM", "sData: " + data.size());
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
		super.onResume();
		new initData().execute("");
	}

	@Override
	public void onClick(View v) {
		int max = data.size();
		switch (v.getId()) {
		case R.id.image:
			break;
		case R.id.leftarrow:
			listView.smoothScrollToPosition(0);
			YoYo.with(Techniques.BounceInLeft).playOn(leftArrow);
			break;
		case R.id.rightarrow:
			listView.smoothScrollToPosition(max);
			YoYo.with(Techniques.BounceInRight).playOn(rightArrow);
			break;
		case R.id.home:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position,
			long id) {
		view.startAnimation(AnimationUtils.loadAnimation(SubjectActivity.this, R.anim.abc_fade_in));
//		Home home = (Home) parent.getAdapter().getItem(position);
		Intent intent = new Intent(SubjectActivity.this, StudyActivity.class);
//		intent.putExtra("SUBJECT", home);
		intent.putExtra("HOME_BG", bg_image);
		intent.putExtra("TITLE", title);
		intent.putExtra("POSITION", position);
		startActivity(intent);
	}

}
