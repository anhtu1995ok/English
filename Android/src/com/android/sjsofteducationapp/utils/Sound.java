package com.android.sjsofteducationapp.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.android.sjsofteducationapp.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;

public class Sound {

	private static SoundPool soundPool;
	private static MusicBackground mService;
	private static int[] soundRes = new int[] { R.raw.pop, R.raw.sonic, R.raw.cartoon_slide_whistle_ascend_version_2,
			R.raw.comedy_pop_finger_in_mouth_001, R.raw.ring3 };
	public static int SOUND_ITEM_ONCLICK;
	public static int SOUND_BUTTON_ONCLICK;
	public static int SOUND_RING_CUCCESS;
	public static int SOUND_RING_TOUCH;
	public static int SOUND_RING_ERROR;

	private static boolean loaded = false;
	private static Context mContext;

	@SuppressWarnings("deprecation")
	public static void initSoundRes(Context context) {
		if (loaded)
			return;
		mContext = context;
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		SOUND_ITEM_ONCLICK = soundPool.load(mContext, soundRes[0], 1);
		SOUND_BUTTON_ONCLICK = soundPool.load(mContext, soundRes[1], 1);
		SOUND_RING_CUCCESS = soundPool.load(mContext, soundRes[2], 1);
		SOUND_RING_TOUCH = soundPool.load(mContext, soundRes[3], 1);
		SOUND_RING_ERROR = soundPool.load(mContext, soundRes[4], 1);

	}

	public static void playMusic(int musicRaw) {
		if (mService == null)
			initBackGroundMusic(musicRaw);
		// else if (mService.getRaw() == musicRaw) {
		// Log.v("", "recall play music: " );
		// mService.start();
		// } else {
		// mService.stop();
		// mService = null;
		// initBackGroundMusic(musicRaw);
		else
			mService.start();

	}

	public static void pauseMusic() {
		// musicOn = false;
		if (mService != null)
			mService.pause();
	}

	public static void stopMusic() {
		if (mService != null)
			mService.stop();
	}

	public static void initBackGroundMusic(int musicRaw) {
		try {
			// new Thread() {
			// public void run() {
			mService = new MusicBackground(mContext, musicRaw, true);
			mService.execute("");
			// }
			//
			// }.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	SharedPreferences mPrefs;

	public static void playSound(int sound) {
		soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f);
	}

	private static Timer updateFromDBTimer;

	public static void doUpdate(int sound, long tick) {
		timePlay += tick;
		if (timePlay >= MAX_TIME)
			stopTimer();
		else {
			Log.v("", "playsound");
			playSound(sound);
		}
	}

	static long timePlay = 0;
	static long MAX_TIME = 0;

	public static void playSound(int sound, long time, long tick) {
		MAX_TIME = time;
		timePlay = 0;
		final int msound = sound;
		final long mtick = tick;
		stopTimer();
		if (updateFromDBTimer == null) {
			// monitorTimer.cancel();
			updateFromDBTimer = new Timer();
			final Runnable mRunnable = new Runnable() {
				public void run() {
					doUpdate(msound, mtick);
				}
			};
			final Handler mHandler = new Handler();
			updateFromDBTimer.schedule(new TimerTask() {

				public void run() {
					mHandler.post(mRunnable);
				}
			}, 0, tick);
		}
	}

	public static void stopTimer() {

		if (updateFromDBTimer != null)
			updateFromDBTimer.cancel();
		updateFromDBTimer = null;

	}
}
