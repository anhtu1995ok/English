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
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String contentImage = cursor.getString(cursor
						.getColumnIndex("content_image"));
				String bgImage = cursor.getString(cursor
						.getColumnIndex("bg_image"));
				String chuDe = cursor.getString(cursor.getColumnIndex("chude"));

				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("icon", icon);
				values.put("content_image", contentImage);
				values.put("bg_image", bgImage);
				values.put("chude", chuDe);
				values.put("success", "");
				educationDBControler.insert("subject", null, values);
			} while (cursor.moveToNext());

		String sql2 = "SELECT * FROM home";
		Cursor cursor2 = copyDBControler.rawQuery(sql2, null);
		if (cursor2.moveToFirst()) {
			do {
				String title = cursor2.getString(cursor2
						.getColumnIndex("title"));
				String icon = cursor2.getString(cursor2.getColumnIndex("icon"));
				String bgImage = cursor2.getString(cursor2
						.getColumnIndex("bg_image"));

				ContentValues values = new ContentValues();
				values.put("title", title);
				values.put("icon", icon);
				values.put("bg_image", bgImage);
				educationDBControler.insert("home", null, values);
			} while (cursor2.moveToNext());
		}

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
