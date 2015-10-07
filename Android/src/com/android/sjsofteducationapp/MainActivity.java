package com.android.sjsofteducationapp;

import java.util.ArrayList;

import com.android.sjsofteducationapp.adapter.HorizontalListViewAdapter;
import com.android.sjsofteducationapp.model.Home;
import com.sileria.android.view.HorzListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener {

	HorzListView listView;
	HorizontalListViewAdapter adapter;
	ArrayList<Home> data;
	ImageView image, leftArrow, rightArrow;
	int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		listView = (HorzListView) findViewById(R.id.listView);
		adapter = new HorizontalListViewAdapter(getApplicationContext(), R.layout.item_home, data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		image = (ImageView) findViewById(R.id.image);
		leftArrow = (ImageView) findViewById(R.id.leftarrow);
		rightArrow = (ImageView) findViewById(R.id.rightarrow);
		image.setOnClickListener(this);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
	}

	private void initData() {
		data = new ArrayList<Home>();
		data.add(new Home(1, "ABC", R.drawable.ic_home_text));
		data.add(new Home(2, "School", R.drawable.ic_home_school));
		data.add(new Home(3, "Animals", R.drawable.ic_home_animal));
		data.add(new Home(4, "Number", R.drawable.ic_home_number));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Home home = (Home) arg0.getAdapter().getItem(arg2);
		String title = home.getTitle();
		Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
		if (title.equalsIgnoreCase("Animals")) {
			Intent intent = new Intent(this, SubjectActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image:
			Toast.makeText(getApplicationContext(), "BackToSchool", Toast.LENGTH_SHORT).show();
			break;
		case R.id.leftarrow:
			if (position != 0) {
				position--;
				listView.setSelection(position);
			}
			break;
		case R.id.rightarrow:

			if (position < 1) {
				position++;
				listView.setSelection(position);
			}

			break;
		default:
			break;
		}
	}
}
