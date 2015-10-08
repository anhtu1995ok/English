package com.android.sjsofteducationapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.sjsofteducationapp.adapter.SubjectAdapter;
import com.android.sjsofteducationapp.model.Home;
import com.sileria.android.view.HorzListView;

public class SubjectActivity extends Activity implements OnClickListener {

	HorzListView listView;
	SubjectAdapter adapter;
	ArrayList<Home> data;
	ImageView image, leftArrow, rightArrow, home;
	int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);

		initData();
		listView = (HorzListView) findViewById(R.id.listView);
		adapter = new SubjectAdapter(getApplicationContext(), R.layout.item_subject, data);
		listView.setAdapter(adapter);

		image = (ImageView) findViewById(R.id.image);
		leftArrow = (ImageView) findViewById(R.id.leftarrow);
		rightArrow = (ImageView) findViewById(R.id.rightarrow);
		home = (ImageView) findViewById(R.id.home);
		image.setOnClickListener(this);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
		home.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				arg1.startAnimation(AnimationUtils.loadAnimation(SubjectActivity.this, R.anim.abc_fade_in));
				Intent intent = new Intent(SubjectActivity.this, StudyActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initData() {
		data = new ArrayList<Home>();
		data.add(new Home(1, "Pig", R.drawable.ic_pig, true));
		data.add(new Home(2, "Duck", R.drawable.ic_duck, true));
		data.add(new Home(3, "Dog", R.drawable.ic_dog, true));
		data.add(new Home(4, "Chicken", R.drawable.ic_chicken, false));
	}

	@Override
	public void onClick(View v) {
		int x = listView.getScrollX();
		switch (v.getId()) {
		case R.id.image:
			Toast.makeText(getApplicationContext(), "Zoo", Toast.LENGTH_SHORT).show();
			break;
		case R.id.leftarrow:
			if (position != 0 && x > 0) {
				position--;
				listView.setSelection(position);
			}
			break;
		case R.id.rightarrow:
			if (position < 1 && x > 0) {
				position++;
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
