package com.android.sjsofteducationapp;

import com.android.sjsofteducationapp.service.BackGroundMusicService;
import com.android.sjsofteducationapp.utils.Media;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MasterActivity extends Activity {

	BackGroundMusicService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		service = BackGroundMusicService.getInstance(getApplicationContext());
	}

	@Override
	protected void onPause() {
		super.onPause();
		Media media = service.getMedia(getApplicationContext());
		if (media.isPlaying()) {
			media.pause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		service.getMedia(getApplicationContext()).setPlay();
	}

	@Override
	protected void onStart() {
		super.onStart();
		service.getMedia(getApplicationContext()).setPlay();
	}
}
