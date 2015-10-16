package com.android.sjsofteducationapp;

import com.android.sjsofteducationapp.utils.MusicBackground;
import com.android.sjsofteducationapp.utils.Sound;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MasterActivity extends Activity {

	MusicBackground mb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		mb = MusicBackground.getInstance(getApplicationContext(), R.raw.thememusic, true);
		mb.start();
		
		Sound.initSoundRes(getApplicationContext());
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mb.isPlaying()) {
			mb.pause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mb.start();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
}
