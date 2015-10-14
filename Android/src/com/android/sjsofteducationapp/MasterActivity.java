package com.android.sjsofteducationapp;

import com.android.sjsofteducationapp.utils.MusicBackground;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MasterActivity extends Activity {

	MusicBackground mb;
	MediaPlayer itemMedia, buttonMedia, homeMedia;
//	Media mp1, mp2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		mb = MusicBackground.getInstance(getApplicationContext(), R.raw.thememusic, true);
		mb.start();
		
		itemMedia = MediaPlayer.create(getApplicationContext(), R.raw.sonic);
		itemMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
		buttonMedia = MediaPlayer.create(getApplicationContext(), R.raw.pop);
		buttonMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		homeMedia = MediaPlayer.create(getApplicationContext(), R.raw.bumble);
		itemMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
		mb.start();
	}
}
