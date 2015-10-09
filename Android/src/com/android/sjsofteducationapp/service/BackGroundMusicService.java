//package com.android.sjsofteducationapp.service;
//
//import com.android.sjsofteducationapp.R;
//import com.android.sjsofteducationapp.utils.Media;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//
//public class BackGroundMusicService extends Service {
//
//	Media media;
//	private static BackGroundMusicService mInstance;
//
//	@Override
//	public IBinder onBind(Intent arg0) {
//		return null;
//	}
//	
//	public static BackGroundMusicService getInstance(Context context) {
//		if (mInstance == null){
//			mInstance = new BackGroundMusicService(context);
//		}
//		return mInstance;
//	}
//	
//	private BackGroundMusicService(Context context) {
//		media = Media.getInstance(context, R.raw.thememusic);
//	}
//	
//	public Media getMedia(Context context){
//		return Media.getInstance(context, R.raw.thememusic);
//	}
//
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		
//		media = Media.getInstance(getBaseContext(), R.raw.thememusic);
//
//		return START_NOT_STICKY;
//	}
//
//	// @Override
//	// protected void onPause() {
//	// // TODO Auto-generated method stub
//	// super.onPause();
//	// if (media.isPlaying()) {
//	// media.pause();
//	// }
//	// }
//	//
//	// @Override
//	// protected void onStart() {
//	// // TODO Auto-generated method stub
//	// super.onStart();
//	// media.setPlay();
//	// }
//
//	public void stop() {
//		media.stop();
//		stopSelf();
//	}
//
//	@Override
//	public void onDestroy() {
//		stopSelf();
//		super.onDestroy();
//
//	}
//
//}
