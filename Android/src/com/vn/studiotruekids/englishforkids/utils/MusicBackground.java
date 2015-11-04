package com.vn.studiotruekids.englishforkids.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class MusicBackground extends AsyncTask<String, String, String> {

	private Context context;
	static MusicBackground mInstance;

	protected String doInBackground(String... params) {
		init();
		start();
		return null;
	}

	MediaPlayer player;
	private int raw;
	boolean isLooping;

	@SuppressWarnings("deprecation")
	public void init() {
		try {
			player = MediaPlayer.create(context, raw);
			player.setLooping(isLooping); // Set looping
			AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			if(audioManager.isWiredHeadsetOn()){
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) /2, 0);
				
			} else {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
			}
			
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setVolume(10, 10);
		} catch (Exception eee) {
		}

	}

	public void start() {
		try {
			player.start();

		} catch (Exception eee) {
		}

	}

	public void pause() {
		try {
			if (isPlaying()) {
				player.pause();
			}
		} catch (Exception eee) {
		}

	}

	public int getRaw() {
		return raw;
	}

	public boolean isPlaying() {
		return player.isPlaying();
	}

	public void stop() {
		try {
			player.stop();
		} catch (Exception eee) {
		}

	}

	public MusicBackground(Context context, int rawSound, boolean loopingMode) {
		this.context = context;
		this.isLooping = loopingMode;
		this.raw = rawSound;
		init();
	}

	public static MusicBackground getInstance(Context context, int rawSound, boolean loopingMode) {
		if (mInstance == null)
			mInstance = new MusicBackground(context, rawSound, loopingMode);
		return mInstance;
	}

}
