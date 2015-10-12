package com.android.sjsofteducationapp.async;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class UnzipAsync extends AsyncTask<Void, Integer, Void> {
	private static int BUFFER_SIZE = 8192;
	private Context context;
	private String zipPathFile;
	private InputStream zipInputStream;
	private String zipLocation;
	private ProgressBar progressBar;
	private boolean cancel = false;

	public UnzipAsync(Context context, String zipPathFile, String zipLocation,
			ProgressBar progressBar) {
		this.context = context;
		this.zipPathFile = zipPathFile;
		this.zipLocation = zipLocation;
		this.progressBar = progressBar;
	}

	public UnzipAsync(Context context, InputStream zipInputStream,
			String zipLocation, ProgressBar progressBar) {
		this.context = context;
		this.zipInputStream = zipInputStream;
		this.zipLocation = zipLocation;
		this.progressBar = progressBar;
	}

	public UnzipAsync(Context context, InputStream zipInputStream,
			String zipLocation) {
		this.context = context;
		this.zipInputStream = zipInputStream;
		this.zipLocation = zipLocation;
	}

	@Override
	protected void onPreExecute() {
		if (progressBar != null)
			this.progressBar.setProgress(0);
		if (onUnzipListener != null)
			onUnzipListener.onStart();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (zipPathFile != null) {
			int size;
			byte[] buffer = new byte[BUFFER_SIZE];

			try {
				if (!zipLocation.endsWith("/")) {
					zipLocation += "/";
				}
				File f = new File(zipLocation);
				if (!f.isDirectory()) {
					f.mkdirs();
				}
				ZipInputStream zin = new ZipInputStream(
						new BufferedInputStream(
								new FileInputStream(zipPathFile), BUFFER_SIZE));
				try {
					long sizeUnzip = 0;
					long totalSize = zipInputStream.available();
					ZipEntry ze = null;
					while ((ze = zin.getNextEntry()) != null) {
						sizeUnzip += ze.getSize();
						long p = sizeUnzip / (totalSize / 100);
						if (p > 100)
							p = 100;
						publishProgress((int) p);
						String path = zipLocation + ze.getName();
						File unzipFile = new File(path);

						if (ze.isDirectory()) {
							if (!unzipFile.isDirectory()) {
								unzipFile.mkdirs();
							}
						} else {
							// check for and create parent directories if they
							// don't
							// exist
							File parentDir = unzipFile.getParentFile();
							if (null != parentDir) {
								if (!parentDir.isDirectory()) {
									parentDir.mkdirs();
								}
							}

							// unzip the file
							FileOutputStream out = new FileOutputStream(
									unzipFile, false);
							BufferedOutputStream fout = new BufferedOutputStream(
									out, BUFFER_SIZE);
							try {
								while ((size = zin.read(buffer, 0, BUFFER_SIZE)) != -1) {
									fout.write(buffer, 0, size);
								}

								zin.closeEntry();
							} finally {
								fout.flush();
								fout.close();
							}
						}
					}
				} finally {
					zin.close();
				}
			} catch (Exception e) {

			}
		} else if (zipInputStream != null) {
			int size;
			byte[] buffer = new byte[BUFFER_SIZE];

			try {
				if (!zipLocation.endsWith("/")) {
					zipLocation += "/";
				}
				File f = new File(zipLocation);
				if (!f.isDirectory()) {
					f.mkdirs();
				}
				ZipInputStream zin = new ZipInputStream(
						new BufferedInputStream(zipInputStream, BUFFER_SIZE));
				try {
					long sizeUnzip = 0;
					long totalSize = zipInputStream.available();
					ZipEntry ze = null;
					while ((ze = zin.getNextEntry()) != null) {
						sizeUnzip += ze.getSize();
						long p = sizeUnzip / (totalSize / 100);
						if (p > 100)
							p = 100;
						publishProgress((int) p);
						String path = zipLocation + ze.getName();
						File unzipFile = new File(path);

						if (ze.isDirectory()) {
							if (!unzipFile.isDirectory()) {
								unzipFile.mkdirs();
							}
						} else {
							// check for and create parent directories if they
							// don't
							// exist
							File parentDir = unzipFile.getParentFile();
							if (null != parentDir) {
								if (!parentDir.isDirectory()) {
									parentDir.mkdirs();
								}
							}

							// unzip the file
							FileOutputStream out = new FileOutputStream(
									unzipFile, false);
							BufferedOutputStream fout = new BufferedOutputStream(
									out, BUFFER_SIZE);
							try {
								while ((size = zin.read(buffer, 0, BUFFER_SIZE)) != -1) {
									fout.write(buffer, 0, size);
								}

								zin.closeEntry();
							} finally {
								fout.flush();
								fout.close();
							}
						}
					}
				} finally {
					zin.close();
				}
			} catch (Exception e) {

			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (progressBar != null)
			progressBar.setProgress(values[0]);
		if (onUnzipListener != null)
			onUnzipListener.onUnzip(values[0]);
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Void result) {
		if (onUnzipListener != null)
			onUnzipListener.onSuccess();
		super.onPostExecute(result);
	}

	private OnUnzipListener onUnzipListener;

	public interface OnUnzipListener {
		void onStart();

		void onUnzip(int progress);

		void onSuccess();

		void onCancel();
	}

	public void setOnUnzipListener(OnUnzipListener onUnzipListener) {
		this.onUnzipListener = onUnzipListener;
	}

	public void cancel() {
		cancel = true;
		this.onCancelled();
		this.onUnzipListener.onCancel();
	}
}
