package com.vn.studiotruekids.englishforkids.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {
	// Ten file
	public static final String PREF_FILE_NAME = "SharedPref";
	// Ten key
	public static final String KEY_AGREE_RUN = "agree_start";
	public static final String KEY_FIRST_RUN = "first_run";
	private SharedPreferences pref;

	public SPUtil(Context context) {
		this.pref = context.getSharedPreferences(PREF_FILE_NAME,
				Context.MODE_PRIVATE);
	}

	private static SPUtil prefUtils;

	public static SPUtil getInstance(Context context) {
		if (prefUtils == null) {
			prefUtils = new SPUtil(context);
		}
		return prefUtils;
	}

	public void set(String keyName, Boolean value) {
		Editor editor = pref.edit();
		editor.putBoolean(keyName, value);
		editor.commit();
	}

	public void set(String keyName, String value) {
		Editor editor = pref.edit();
		editor.putString(keyName, value);
		editor.commit();
	}

	public void set(String keyName, int value) {
		Editor editor = pref.edit();
		editor.putInt(keyName, value);
		editor.commit();
	}

	public void set(String keyName, long value) {
		Editor editor = pref.edit();
		editor.putLong(keyName, value);
		editor.commit();
	}

	public boolean get(String key, Boolean defaultValue) {
		boolean ret;
		ret = pref.getBoolean(key, defaultValue);
		return ret;
	}

	public String get(String key, String defaultValue) {
		String ret;
		ret = pref.getString(key, defaultValue);
		return ret;
	}

	public int get(String key, int defaultValue) {
		int ret;
		ret = pref.getInt(key, defaultValue);
		return ret;
	}

	public long get(String key, long defaultValue) {
		long ret;
		ret = pref.getLong(key, defaultValue);
		return ret;
	}
}
