package com.android.sjsofteducationapp;

import java.io.IOException;
import java.util.ArrayList;

import com.android.sjsofteducationapp.adapter.HorizontalListViewAdapter;
import com.android.sjsofteducationapp.database.EducationDBControler;
import com.android.sjsofteducationapp.model.Home;
import com.sileria.android.view.HorzListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class MainActivity extends MasterActivity implements OnClickListener, OnItemClickListener {

	HorzListView listView;
	HorizontalListViewAdapter adapter;
	ArrayList<Home> data;
	ImageView image, leftArrow, rightArrow;
	int position = 0;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		listView = (HorzListView) findViewById(R.id.listView);
		adapter = new HorizontalListViewAdapter(getApplicationContext(), R.layout.item_home, data);

		// adapter = new Hori(getApplicationContext(), data);
		listView.setAdapter(adapter);
		// listView.setHasFixedSize(true);
		// listView.setLayoutManager(
		// new LinearLayoutManager(getApplicationContext(),
		// LinearLayoutManager.HORIZONTAL, false));
		// listView.setItemAnimator(new DefaultItemAnimator());
		listView.setOnItemClickListener(this);

		image = (ImageView) findViewById(R.id.image);
		leftArrow = (ImageView) findViewById(R.id.leftarrow);
		rightArrow = (ImageView) findViewById(R.id.rightarrow);
		image.setOnClickListener(this);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
		
		EducationDBControler dbController = EducationDBControler.getInstance(MainActivity.this);
		try {
			dbController.createDataBase();
		} catch (IOException e) {
		}

	}

	private void initData() {
		data = new ArrayList<Home>();
		data.add(new Home(1, "ABC", R.drawable.ic_home_text));
		data.add(new Home(2, "School", R.drawable.ic_home_school));
		data.add(new Home(3, "Animals", R.drawable.ic_home_animal));
		data.add(new Home(4, "Number", R.drawable.ic_home_number));
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
				listView.setSelection(position);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String title = ((Home) arg0.getAdapter().getItem(arg2)).getTitle();

		arg1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_in));
		if (title.equalsIgnoreCase("Animals")) {
			Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mb.stop();
	}

}
