package com.vn.studiotruekids.englishforkids.utils;

import java.util.ArrayList;

import com.vn.studiotruekids.englishforkids.database.EducationDBControler;
import com.vn.studiotruekids.englishforkids.model.Home;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class GetDataFromDB {

	EducationDBControler dbController;
	ArrayList<Home> data;

	public GetDataFromDB(Context context) {
		dbController = EducationDBControler.getInstance(context);
	}

	public ArrayList<Home> getArrayListEducation() {
		return data;
	}

	public ArrayList<Home> getDataFromDB(String name) {
		data = new ArrayList<Home>();
		String sql = "Select * from subject where chude = '" + name + "';";
		Cursor cursor = dbController.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String content_image = cursor.getString(cursor.getColumnIndex("content_image"));
				String bg_image = cursor.getString(cursor.getColumnIndex("bg_image"));
				String success = cursor.getString(cursor.getColumnIndex("success"));

				data.add(new Home(id, title, icon, content_image, bg_image, success));
				Log.d("ToanNM", "done get Data from DB : " + data.size() + "isSuccess: " + success);
			} while (cursor.moveToNext());
		}
		return data;
	}

	public ArrayList<Home> getHomeDataFromDB() {
		data = new ArrayList<Home>();
		Cursor cursor = dbController.query("home", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String bg_image = cursor.getString(cursor.getColumnIndex("bg_image"));

				data.add(new Home(id, title, icon, bg_image));
			} while (cursor.moveToNext());
		}
		return data;
	}
}
