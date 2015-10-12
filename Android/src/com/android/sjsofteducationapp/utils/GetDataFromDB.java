package com.android.sjsofteducationapp.utils;

import java.util.ArrayList;

import com.android.sjsofteducationapp.database.EducationDBControler;
import com.android.sjsofteducationapp.model.Home;

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

	public ArrayList<Home> getDataFromDB() {
		data = new ArrayList<Home>();
		Cursor cursor = dbController.query("subject", null, null, null, null,
				null, null);
		cursor.moveToFirst();
		do {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String url_image = cursor.getString(cursor.getColumnIndex("url_image"));
			String content_image = cursor.getString(cursor.getColumnIndex("content_image"));
			String bg_image = cursor.getString(cursor.getColumnIndex("bg_image"));
			
			data.add(new Home(id, title, url_image, content_image, bg_image));
		} while (cursor.moveToNext());
		Log.d("ToanNM", "done get Data from DB : " + data.size());
		return data;
	}
}
