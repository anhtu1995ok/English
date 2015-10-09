package com.android.sjsofteducationapp;

import com.android.sjsofteducationapp.utils.MusicBackground;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MasterActivity extends Activity {

	MusicBackground mb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		mb = new MusicBackground(getApplicationContext(), R.raw.thememusic, true);
		mb.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (MusicBackground.getInstance(getApplicationContext(), R.raw.thememusic, true).isPlaying()) {
			mb.pause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
}
