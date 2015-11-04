package com.vn.studiotruekids.englishforkids;

import java.io.IOException;
import java.util.ArrayList;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.vn.studiotruekids.englishforkids.adapter.HorizontalListViewAdapter;
import com.vn.studiotruekids.englishforkids.database.EducationDBControler;
import com.vn.studiotruekids.englishforkids.model.Home;
import com.vn.studiotruekids.englishforkids.utils.GetDataFromDB;
import com.vn.studiotruekids.englishforkids.utils.Sound;

import android.content.Intent;
import android.media.MediaPlayer;
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
import it.sephiroth.android.library.widget.AbsHListView;
import it.sephiroth.android.library.widget.AbsHListView.OnScrollListener;
import it.sephiroth.android.library.widget.HListView;

public class MainActivity extends MasterActivity implements OnClickListener,
		it.sephiroth.android.library.widget.AdapterView.OnItemClickListener {
	InterstitialAd mInterstitialAd;

	HListView listView;
	HorizontalListViewAdapter adapter;
	ArrayList<Home> data;
	ImageView image, leftArrow, rightArrow, share, moreapp;
	Intent intent;

	private Animation aFlicker;
	private boolean flicker = false;

	MediaPlayer itemMedia, buttonMedia;
	int visibleItem, totalItem;

	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-8818569204723527/7877358093");
		AdRequest adRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest);

		mAdView = (AdView) findViewById(R.id.adView);

		EducationDBControler dbController = EducationDBControler
				.getInstance(MainActivity.this);
		try {
			dbController.createDataBase();
			new initData().execute("");
		} catch (IOException e) {
		}
		listView = (HListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsHListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsHListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				visibleItem = visibleItemCount;
				totalItem = totalItemCount;

			}
		});

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

		itemMedia = MediaPlayer.create(getApplicationContext(), R.raw.sonic);

		buttonMedia = MediaPlayer.create(getApplicationContext(), R.raw.pop);

	}

	private class initData extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			GetDataFromDB gdfb = new GetDataFromDB(getApplicationContext());
			data = gdfb.getHomeDataFromDB();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			adapter = new HorizontalListViewAdapter(getApplicationContext(),
					R.layout.item_home, data);
			listView.setAdapter(adapter);
			listView.setSelector(R.drawable.listview_onclick);
			super.onPostExecute(result);
		}

	}

	@Override
	public void onClick(View v) {
//		int width = 0;
//		int lWidth = 0;
//		int marginInPX = 25;
//		if (android.os.Build.VERSION.SDK_INT <= 10) {
//			Display display = getWindowManager().getDefaultDisplay();
//			width = display.getWidth();
//			lWidth = -width;
//		} else {
//			DisplayMetrics metrics = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay().getMetrics(metrics);
//			width = metrics.widthPixels + marginInPX;
//			lWidth = -metrics.widthPixels - marginInPX;
//		}
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
		case R.id.share:
			YoYo.with(Techniques.Bounce).playOn(share);
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT,
					getResources().getText(R.string.share_text));
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources()
					.getText(R.string.send_to)));
			break;
		case R.id.moreapp:
			YoYo.with(Techniques.Bounce).playOn(moreapp);
			String url = "https://play.google.com/store/apps/developer?id=TrueKids!";
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
		mb.pause();
	}

	@Override
	protected void onResume() {
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		super.onResume();
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

			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		moreapp.startAnimation(aFlicker);
	}

	@Override
	public void onItemClick(
			it.sephiroth.android.library.widget.AdapterView<?> parent,
			View view, int position, long id) {
		Sound.playSound(Sound.SOUND_BUTTON_ONCLICK);
		String title = ((Home) parent.getAdapter().getItem(position))
				.getTitle();
		String bg_image = ((Home) parent.getAdapter().getItem(position))
				.getBg_image();

		view.startAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.abc_fade_in));
		// if (title.equalsIgnoreCase("Animals")) {
		Intent intent = new Intent(getApplicationContext(),
				SubjectActivity.class);
		intent.putExtra("HOME_TITLE", title);
		intent.putExtra("HOME_BG", bg_image);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		mInterstitialAd.show();
		super.onBackPressed();
	}
	
}
