package com.android.sjsofteducationapp.adapter;

import java.io.File;
import java.util.ArrayList;

import com.android.sjsofteducationapp.R;
import com.android.sjsofteducationapp.model.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class SubjectAdapter extends ArrayAdapter<Home> {
	private Context context;
	int resource;
	ArrayList<Home> arrayList;
	private String title;

	public SubjectAdapter(Context context, int resource, ArrayList<Home> arrayList, String title) {
		super(context, resource, arrayList);
		this.context = context;
		this.resource = resource;
		this.arrayList = arrayList;
		this.title = title;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	public void notifyDataSetChanged(ArrayList<Home> list) {
		this.arrayList = list;
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder = null;

		Home home = arrayList.get(position);

//		if (convertView == null) {
			convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource,
					null);
//			holder = new ViewHolder();
//			holder.image = (ImageView) convertView.findViewById(R.id.image);
//			holder.star = (ImageView) convertView.findViewById(R.id.star);

//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}

		// int imageUrl = home.getUrl_img();
		// holder.image.setImageResource(imageUrl);
			ImageView image = (ImageView) convertView.findViewById(R.id.image);
			ImageView star = (ImageView) convertView.findViewById(R.id.star);
		try {
			//
			String imageFile = Environment.getExternalStorageDirectory() + "/Sjsoft/Subject/Icon/" + title + "/"
					+ home.getIcon();
			Log.d("TuNT", "SubJect image : " + imageFile);
			File file = new File(imageFile);
			if (file.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				image.setImageBitmap(myBitmap);

			}
			//
			String success = home.getSuccess();
			if (success.toLowerCase().equals("done")) {
				star.setVisibility(View.VISIBLE);
			} else {
				star.setVisibility(View.GONE);
//				convertView.getDisplay().getDisplayId();
			}
		} catch (Exception e) {

		}

		return convertView;
	}

//	private static class ViewHolder {
//		ImageView image, star;
//	}

}
