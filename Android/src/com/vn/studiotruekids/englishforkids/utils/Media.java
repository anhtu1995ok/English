package com.vn.studiotruekids.englishforkids.utils;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;

public class Media {
	MediaPlayer mp;
	Context mContext;

	public final static int MEDIA_STATE_PLAYING = 1;
	public final static int MEDIA_STATE_PAUSED = 2;
	ArrayList<OnMediaStateChangedListener> mCallbacks;

	public interface OnMediaStateChangedListener {
		public void onStateChanged(int newState);
	}

	public void addOnMediaStateChangeListener(OnMediaStateChangedListener listener) {
		if (mCallbacks == null)
			mCallbacks = new ArrayList<Media.OnMediaStateChangedListener>();
		mCallbacks.add(listener);
	}

	private Media(Context context, int fileName) {
		mContext = context;
		mp = MediaPlayer.create(context, fileName);
//		mp.setLooping(true);
		mp.setVolume(10, 10);
		try {
			mp.start();
		} catch (Exception e) {
			
		}
		
	}

	private static Media mInstance;

	public static Media getInstance(Context context, int fileName) {
		if (mInstance == null)
			mInstance = new Media(context , fileName);
		return mInstance;
	}

	public boolean stop() {
		mp.stop();
		return true;
	}

	public boolean start() {
		mp.start();
		if (mCallbacks != null && mCallbacks.size() > 0) {
			for (OnMediaStateChangedListener listener : mCallbacks) {
				if (listener != null)
					listener.onStateChanged(MEDIA_STATE_PLAYING);
			}
		}
		return true;
	}

	public void setPlay() {
		mInstance.start();
	}

	public boolean pause() {
		if (isPlaying()) {
			mp.pause();
			if (mCallbacks != null && mCallbacks.size() > 0) {
				for (OnMediaStateChangedListener listener : mCallbacks) {
					if (listener != null)
						listener.onStateChanged(MEDIA_STATE_PAUSED);
				}
			}
		}
		return true;
	}

	public boolean isPlaying() {
		return mp.isPlaying();
	}

	public void destroy() {
		mp.release();
		mp = null;
	}
}
