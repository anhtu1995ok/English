package com.android.sjsofteducationapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import com.android.sjsofteducationapp.async.UnzipAsync;
import com.android.sjsofteducationapp.async.UnzipAsync.OnUnzipListener;
import com.android.sjsofteducationapp.utils.SPUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class FirstRunActivity extends Activity {
	private ProgressBar progressBar;
	private UnzipAsync unzipAsync;
	private boolean newVersion = false;
	private String root;
	private AlertDialog alertDialog;

	private String zipFileUpdateName;
	private String dbFileUpdateName;
	private String urlFileUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_run);
		root = Environment.getExternalStorageDirectory().toString();

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		boolean firstRun = SPUtil.getInstance(FirstRunActivity.this).get(
				SPUtil.KEY_FIRST_RUN, true);
		if (firstRun) {
			unzipData();
		} else {
			checkUpdate();
		}
	}

	@Override
	protected void onPause() {
		if (unzipAsync != null)
			unzipAsync.cancel();
		super.onPause();
	}

	private void unzipData() {
		try {
			InputStream inputStream = getAssets().open("image/content.zip");
			unzipAsync = new UnzipAsync(FirstRunActivity.this, inputStream,
					root + "/Sjsoft/");
			unzipAsync.execute();
			unzipAsync.setOnUnzipListener(new OnUnzipListener() {

				@Override
				public void onUnzip(int progress) {
					progressBar.setProgress(progress);
				}

				@Override
				public void onSuccess() {
					SPUtil.getInstance(FirstRunActivity.this).set(
							SPUtil.KEY_FIRST_RUN, false);
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
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getResources().getString(R.string.url_check_update),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						showDialogUpdate();
						try {
							JSONObject object = new JSONObject(response
									.toString());
							zipFileUpdateName = object.getString("zipname");
							dbFileUpdateName = object.getString("dataname");
							urlFileUpdate = object.getString("url");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Log.d("TuNT", "json: " + response.toString());
						super.onSuccess(statusCode, headers, response);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						super.onFailure(statusCode, headers, throwable,
								errorResponse);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						startMainActivity();
						Log.d("TuNT", "onFailure2: " + responseString);
						super.onFailure(statusCode, headers, responseString,
								throwable);
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
		builder.setNegativeButton(getResources().getString(R.string.yes),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.dismiss();
						startDownload(urlFileUpdate);
					}
				});
		builder.setNeutralButton(getResources().getString(R.string.no),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startMainActivity();
					}
				});
		alertDialog = builder.create();
		alertDialog.show();
	}

	private void startDownload(String url) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new FileAsyncHttpResponseHandler(/* Context */this) {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					File response) {
				try {
					UnzipAsync unzipAsync = new UnzipAsync(FirstRunActivity.this, new FileInputStream(response), root + "/Sjsoft/");
					unzipAsync.setOnUnzipListener(new OnUnzipListener() {
						
						@Override
						public void onUnzip(int progress) {
							progressBar.setProgress(progress);
						}
						
						@Override
						public void onSuccess() {
							startMainActivity();
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
				int p = bytesWritten/(totalSize/100);
				Log.d("TuNT", "p: "+p);
				progressBar.setProgress(p);
				super.onProgress(bytesWritten, totalSize);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					File arg3) {
				Log.d("TuNT", "onFailure downloadfile: ");
			}
		});
	}

//	public void copyFile(File src, File dst) throws IOException {
//		InputStream in = new FileInputStream(src);
//		OutputStream out = new FileOutputStream(dst);
//
//		byte[] buf = new byte[1024];
//		int len;
//		while ((len = in.read(buf)) > 0) {
//			out.write(buf, 0, len);
//		}
//		in.close();
//		out.close();
//	}
}
