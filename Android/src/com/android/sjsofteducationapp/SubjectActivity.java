package com.android.sjsofteducationapp;

import java.util.ArrayList;

import com.android.sjsofteducationapp.adapter.SubjectAdapter;
import com.android.sjsofteducationapp.model.Home;
import com.sileria.android.view.HorzListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class SubjectActivity extends Activity implements OnItemClickListener, OnClickListener {

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
		listView.setOnItemClickListener(this);

		image = (ImageView) findViewById(R.id.image);
		leftArrow = (ImageView) findViewById(R.id.leftarrow);
		rightArrow = (ImageView) findViewById(R.id.rightarrow);
		home = (ImageView) findViewById(R.id.home);
		image.setOnClickListener(this);
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
		home.setOnClickListener(this);
	}

	private void initData() {
		data = new ArrayList<Home>();
		data.add(new Home(1, "Pig", R.drawable.ic_pig, true));
		data.add(new Home(2, "Duck", R.drawable.ic_duck, true));
		data.add(new Home(3, "Dog", R.drawable.ic_dog, true));
		data.add(new Home(4, "Chicken", R.drawable.ic_chicken, false));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Home home = (Home) arg0.getAdapter().getItem(arg2);
		String title = home.getTitle();
		Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image:
			Toast.makeText(getApplicationContext(), "Zoo", Toast.LENGTH_SHORT).show();
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
		case R.id.home:
			finish();
			break;
		default:
			break;
		}
	}
}
