package com.vn.studiotruekids.englishforkids.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CopyDBControler extends SQLiteOpenHelper {
	// The Android's default system path of your application database.
	private static String DB_PATH;
	// Data Base Name.
	private static final String DATABASE_NAME = "sjsoft_education_update.sqlite";
	// Data Base Version.
	private static final int DATABASE_VERSION = 1;
	// Table Names of Data Base.
	static final String TABLE_Name = "subject";

	public Context context;
	static SQLiteDatabase sqliteDataBase;

	private static CopyDBControler controler;

	@SuppressLint("SdCardPath")
	private CopyDBControler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
	}

	public static CopyDBControler getInstance(Context context) {
		if (controler == null) {
			controler = new CopyDBControler(context);
		}
		return controler;
	}

	public void createDataBase() throws IOException {
		// check if the database exists
		boolean databaseExist = checkDataBase();

		if (!databaseExist) {
			this.getWritableDatabase();
		}
	}

	public boolean checkDataBase() {
		File databaseFile = new File(DB_PATH + DATABASE_NAME);
		return databaseFile.exists();
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transferring byte stream.
	 * */
	public void copyDataBase(File file) throws IOException {
		byte[] buffer = new byte[1024];
		OutputStream myOutput = null;
		int length;
		// Open your local db as the input stream
		InputStream myInput = null;
		try {
			myInput = new FileInputStream(file);
			myOutput = new FileOutputStream(DB_PATH + DATABASE_NAME);
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myOutput.close();
			myOutput.flush();
			myInput.close();
			Log.i("Database", "New database has been copied to device!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method opens the data base connection. First it create the path up
	 * till data base of the device. Then create connection with data base.
	 */
	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * This Method is used to close the data base connection.
	 */
	@Override
	public synchronized void close() {
		if (sqliteDataBase != null)
			sqliteDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("TuNT", "onCreate");
		try {
			createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		if (sqliteDataBase == null)
			openDataBase();
		Cursor cursor = sqliteDataBase.query(table, columns, selection,
				selectionArgs, groupBy, having, orderBy);
		return cursor;
	}

	public Cursor rawQuery(String stSql, String[] data) {
		if (sqliteDataBase == null)
			openDataBase();

		Cursor cursor = sqliteDataBase.rawQuery(stSql, data);
		return cursor;
	}

	public void setSuccess(long id) {
		ContentValues values = new ContentValues();
		values.put("isSuccess", "done");

		String where = "id" + " = " + id;
		update("subject", values, where, null);
	}

	public int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		if (sqliteDataBase == null)
			openDataBase();

		int ret = sqliteDataBase.update(table, values, whereClause, whereArgs);

		return ret;
	}
}