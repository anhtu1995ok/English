package com.android.sjsofteducationapp;

import com.android.sjsofteducationapp.utils.MusicBackground;
import com.android.sjsofteducationapp.utils.SPUtil;
import com.android.sjsofteducationapp.utils.Sound;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MasterActivity extends Activity {

	MusicBackground mb;
	AudioManager audioManager;
	SPUtil sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		sp = SPUtil.getInstance(getApplicationContext());
		getCurrentVolume();

		mb = MusicBackground.getInstance(getApplicationContext(), R.raw.thememusic, true);
		mb.start();

		Sound.initSoundRes(getApplicationContext());
	}

	void setMaxVolume() {
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.isWiredHeadsetOn()) {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
					audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2, 0);

		} else {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
					audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		}
	}

	void setVolume(int volume) {
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.isWiredHeadsetOn()) {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

		} else {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
		}
	}

	void setVolume() {
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.isWiredHeadsetOn()) {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
					audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2, 0);

		} else {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
					audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3 * 2, 0);
		}
	}

	void getCurrentVolume() {
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		sp.set("CURRENT_THEME", volume);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		setVolume(sp.get("CURRENT_THEME", 0));
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
