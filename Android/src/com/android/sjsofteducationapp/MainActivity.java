package com.android.sjsofteducationapp;

import java.io.IOException;
import java.util.ArrayList;

import com.android.sjsofteducationapp.adapter.HorizontalListViewAdapter;
import com.android.sjsofteducationapp.database.EducationDBControler;
import com.android.sjsofteducationapp.model.Home;
import com.android.sjsofteducationapp.utils.GetDataFromDB;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import it.sephiroth.android.library.widget.HListView;

public class MainActivity extends MasterActivity
		implements OnClickListener, it.sephiroth.android.library.widget.AdapterView.OnItemClickListener {

	HListView listView;
	HorizontalListViewAdapter adapter;
	ArrayList<Home> data;
	ImageView image, leftArrow, rightArrow, share, moreapp;
	int position = 0;
	Intent intent;

	private Animation aFlicker;
	private boolean flicker = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initData();
		EducationDBControler dbController = EducationDBControler.getInstance(MainActivity.this);
		try {
			dbController.createDataBase();
			new initData().execute("");
		} catch (IOException e) {
		}
		listView = (HListView) findViewById(R.id.listView);

		// adapter = new Hori(getApplicationContext(), data);

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

		share = (ImageView) findViewById(R.id.share);
		moreapp = (ImageView) findViewById(R.id.moreapp);
		startMoreappFlicker();
		share.setOnClickListener(this);
		moreapp.setOnClickListener(this);

	}

	// private void initData() {
	// data = new ArrayList<Home>();
	// data.add(new Home(1, "ABC", R.drawable.ic_home_text));
	// data.add(new Home(2, "School", R.drawable.ic_home_school));
	// data.add(new Home(3, "Animals", R.drawable.ic_home_animal));
	// data.add(new Home(4, "Number", R.drawable.ic_home_number));
	// }
	private class initData extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			GetDataFromDB gdfb = new GetDataFromDB(getApplicationContext());
			data = gdfb.getHomeDataFromDB();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter = new HorizontalListViewAdapter(getApplicationContext(), R.layout.item_home, data);
			listView.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}

	@Override
	public void onClick(View v) {
		int max = data.size();
		switch (v.getId()) {
		case R.id.image:
			break;
		case R.id.leftarrow:
			listView.smoothScrollToPosition(0);
			break;
		case R.id.rightarrow:
			listView.smoothScrollToPosition(max);
			break;
		case R.id.share:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getText(R.string.share_text));
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
			break;
		case R.id.moreapp:
			String url = "https://play.google.com/store/apps/developer?id=Vareco+Mobile";
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// Intent intent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse("market://developer?id=" + appID));
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mb.stop();
	}

	private void startMoreappFlicker() {
		aFlicker = new TranslateAnimation(0, 0, 0, 0);
		aFlicker.setDuration(200);
		aFlicker.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				if (flicker) {
					flicker = false;
					moreapp.setImageResource(R.drawable.ic_moreapp2);
				} else {
					flicker = true;
					moreapp.setImageResource(R.drawable.ic_moreapp1);
				}
				moreapp.startAnimation(animation);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		moreapp.startAnimation(aFlicker);
	}

	@Override
	public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position,
			long id) {
		String title = ((Home) parent.getAdapter().getItem(position)).getTitle();
		String bg_image = ((Home) parent.getAdapter().getItem(position)).getBg_image();

		view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_fade_in));
		// if (title.equalsIgnoreCase("Animals")) {
		Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
		intent.putExtra("HOME_TITLE", title.toLowerCase());
		intent.putExtra("HOME_BG", bg_image);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
