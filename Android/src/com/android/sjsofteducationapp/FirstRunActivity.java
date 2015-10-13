package com.android.sjsofteducationapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.sjsofteducationapp.async.CopyDBAsync;
import com.android.sjsofteducationapp.async.CopyDBAsync.OnCopyDBListener;
import com.android.sjsofteducationapp.async.UnzipAsync;
import com.android.sjsofteducationapp.async.UnzipAsync.OnUnzipListener;
import com.android.sjsofteducationapp.database.EducationDBControler;
import com.android.sjsofteducationapp.utils.SPUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class FirstRunActivity extends Activity {
	private ProgressBar progressBar;
	private TextView msg;
	private UnzipAsync unzipAsync;
	private String root;
	private AlertDialog alertDialog;

	private String zipFileUpdateName;
	private String dbFileUpdateName;
	private String urlFileUpdate;
	private int version;
	private EducationDBControler educationDBControler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_run);
		root = Environment.getExternalStorageDirectory().toString();

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		msg = (TextView) findViewById(R.id.msg);
		boolean firstRun = SPUtil.getInstance(FirstRunActivity.this).get(SPUtil.KEY_FIRST_RUN, true);
		if (firstRun) {
			unzipData();
		} else {
			checkUpdate();
		}
		educationDBControler = EducationDBControler.getInstance(FirstRunActivity.this);
	}

	@Override
	protected void onPause() {
		if (unzipAsync != null)
			unzipAsync.cancel();
		super.onPause();
	}

	private void unzipData() {
		msg.setText(getResources().getString(R.string.unzip));
		try {
			InputStream inputStream = getAssets().open("image/content.zip");
			unzipAsync = new UnzipAsync(FirstRunActivity.this, inputStream, root + "/");
			unzipAsync.execute();
			unzipAsync.setOnUnzipListener(new OnUnzipListener() {

				@Override
				public void onUnzip(int progress) {
					progressBar.setProgress(progress);
				}

				@Override
				public void onSuccess() {
					SPUtil.getInstance(FirstRunActivity.this).set(SPUtil.KEY_FIRST_RUN, false);
					startMainActivity();
				}

				@Override
				public void onStart() {
				}

				@Override
				public void onCancel() {
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkUpdate() {
		msg.setText(getResources().getString(R.string.check_update));
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getResources().getString(R.string.url_check_update), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				try {
					JSONObject object = new JSONObject(response.toString());
					zipFileUpdateName = object.getString("zipname");
					dbFileUpdateName = object.getString("dataname");
					urlFileUpdate = object.getString("url");
					version = object.getInt("version");

					int currentVersion = EducationDBControler.getInstance(getApplicationContext()).getVersion();
					Log.d("ToanNM", "currentVersion : " + currentVersion + " , version : " + version);
					if (currentVersion < version) {
						showDialogUpdate();
					} else {
						startMainActivity();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.d("TuNT", "json: " + response.toString());
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				startMainActivity();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				startMainActivity();
				Log.d("TuNT", "onFailure2: " + responseString);
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
	}

	private void startMainActivity() {
		Intent intent = new Intent(FirstRunActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void showDialogUpdate() {
		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(FirstRunActivity.this);
		builder.setTitle(getResources().getString(R.string.app_name));
		builder.setMessage(getResources().getString(R.string.update));
		builder.setNegativeButton(getResources().getString(R.string.yes), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				startDownload(urlFileUpdate);
			}
		});
		builder.setNeutralButton(getResources().getString(R.string.no), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startMainActivity();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}

	private void startDownload(String url) {
		msg.setText(getResources().getString(R.string.downloading));
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new FileAsyncHttpResponseHandler(this) {
			@Override
			public void onSuccess(int statusCode, Header[] headers, File response) {
				msg.setText(getResources().getString(R.string.unzip));
				try {
					UnzipAsync unzipAsync = new UnzipAsync(FirstRunActivity.this, new FileInputStream(response),
							root + "/");
					unzipAsync.setOnUnzipListener(new OnUnzipListener() {

						@Override
						public void onUnzip(int progress) {
							progressBar.setProgress(progress);
						}

						@Override
						public void onSuccess() {
							File file = new File(root + "/Sjsoft/" + dbFileUpdateName);
							startCopyDB(file);
						}

						@Override
						public void onStart() {
						}

						@Override
						public void onCancel() {
						}
					});
					unzipAsync.execute();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				int p = bytesWritten / (totalSize / 100);
				Log.d("TuNT", "p: " + p);
				progressBar.setProgress(p);
				super.onProgress(bytesWritten, totalSize);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2, File arg3) {
				Log.d("TuNT", "onFailure downloadfile: ");
				startMainActivity();
			}
		});
	}

	private void startCopyDB(File file) {
		educationDBControler.setVersion(version);
		CopyDBAsync copyDBAsync = new CopyDBAsync(FirstRunActivity.this, file);
		copyDBAsync.setOnCopyDBListener(new OnCopyDBListener() {

			@Override
			public void onFinished() {
				startMainActivity();
			}
		});
		copyDBAsync.execute();
	}
}
