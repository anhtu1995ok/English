package com.android.sjsofteducationapp;

import java.io.File;
import java.util.ArrayList;

import com.android.sjsofteducationapp.adapter.SubjectAdapter;
import com.android.sjsofteducationapp.model.Home;
import com.android.sjsofteducationapp.utils.GetDataFromDB;
import com.sileria.android.view.HorzListView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class SubjectActivity extends MasterActivity implements OnClickListener {

	private HorzListView listView;
	private SubjectAdapter adapter;
	private ArrayList<Home> data;
	private ImageView image, leftArrow, rightArrow, home;
	private int position = 0;
	
	private String title, bg_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
		
		Intent intent = getIntent();
		title = intent.getStringExtra("HOME_TITLE");
		bg_image = intent.getStringExtra("HOME_BG");
		
		if(title == null){
			finish();
		}
		
		new initData().execute("");
		// initData();

		listView = (HorzListView) findViewById(R.id.listView);

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
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				arg1.startAnimation(AnimationUtils.loadAnimation(SubjectActivity.this, R.anim.abc_fade_in));
				Home home = (Home) arg0.getAdapter().getItem(arg2);
				Intent intent = new Intent(SubjectActivity.this, StudyActivity.class);
				intent.putExtra("SUBJECT", home);
				intent.putExtra("HOME_BG", bg_image);
				startActivity(intent);
			}
		});
	}

	private class initData extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			GetDataFromDB gdfdb = new GetDataFromDB(getApplicationContext());
			data = gdfdb.getDataFromDB(title);
			Log.d("ToanNM", "sData: " + data.size());
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter = new SubjectAdapter(getApplicationContext(), R.layout.item_subject, data);
			listView.setAdapter(adapter);
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
			if (position > 0) {
				position--;
				listView.setSelection(position);
			}
			if (position == 0) {
				listView.setAdapter(adapter);
			}
			break;
		case R.id.rightarrow:
			if (position < max - 3) {
				position++;
				listView.setSelection(0);
				listView.setSelection(position);
			}
			break;
		case R.id.home:
			finish();
			break;
		default:
			break;
		}
	}

}
