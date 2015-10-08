package com.android.sjsofteducationapp;

import com.android.sjsofteducationapp.service.BackGroundMusicService;
import com.android.sjsofteducationapp.utils.Media;

import android.app.Activity;
import android.os.Bundle;

public class MasterActivity extends Activity {

	BackGroundMusicService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
