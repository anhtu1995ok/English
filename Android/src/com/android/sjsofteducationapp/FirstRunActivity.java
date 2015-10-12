package com.android.sjsofteducationapp;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.android.sjsofteducationapp.async.UnzipAsync;
import com.android.sjsofteducationapp.async.UnzipAsync.OnUnzipListener;

public class FirstRunActivity extends Activity {
	private ProgressBar progressBar;
	private UnzipAsync unzipAsync;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_run);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		try {
			InputStream inputStream = getAssets().open("image/content.zip");
			String root = Environment.getExternalStorageDirectory().toString();
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
					Intent intent = new Intent(FirstRunActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
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

	@Override
	protected void onPause() {
		unzipAsync.cancel();
		super.onPause();
	}
}
