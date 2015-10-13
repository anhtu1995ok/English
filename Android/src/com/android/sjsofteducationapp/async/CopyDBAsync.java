package com.android.sjsofteducationapp.async;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.android.sjsofteducationapp.database.CopyDBControler;
import com.android.sjsofteducationapp.database.EducationDBControler;

public class CopyDBAsync extends AsyncTask<Void, Integer, Void> {
	private Context context;
	private File file;
	private CopyDBControler copyDBControler;
	private EducationDBControler educationDBControler;

	public CopyDBAsync(Context context, File file) {
		this.context = context;
		this.file = file;
		copyDBControler = CopyDBControler.getInstance(context);
		educationDBControler = EducationDBControler.getInstance(context);
	}

	@Override
	protected void onPreExecute() {
		try {
			copyDBControler.copyDataBase(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		String sql = "SELECT * FROM subject";
		Cursor cursor = copyDBControler.rawQuery(sql, null);
		if (cursor.moveToFirst())
			do {
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String urlImage = cursor.getString(cursor
						.getColumnIndex("url_image"));
				String contentImage = cursor.getString(cursor
						.getColumnIndex("content_image"));

				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("url_image", urlImage);
				values.put("content_image", contentImage);
				values.put("bg_image", "");
				values.put("isSuccess", "");
				educationDBControler.insert("subject", null, values);
			} while (cursor.moveToNext());
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		this.onCopyDBListener.onFinished();
		super.onPostExecute(result);
	}

	private OnCopyDBListener onCopyDBListener;

	public interface OnCopyDBListener {
		void onFinished();
	}

	public void setOnCopyDBListener(OnCopyDBListener onCopyDBListener) {
		this.onCopyDBListener = onCopyDBListener;
	}

}
