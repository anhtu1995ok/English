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

	public SubjectAdapter(Context context, int resource, ArrayList<Home> arrayList) {
		super(context, resource, arrayList);
		this.context = context;
		this.resource = resource;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	public void notifyDataSetChanged(ArrayList<Home> list) {
		arrayList = list;
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		Home home = arrayList.get(position);

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.star = (ImageView) convertView.findViewById(R.id.star);

			convertView.setTag(holder);
		}

//		int imageUrl = home.getUrl_img();
//		holder.image.setImageResource(imageUrl);

		try {
			// 
			String imageFile = Environment.getExternalStorageDirectory() + "/Sjsoft/UrlImage/" + home.getUrl_image();
			Log.d("ToanNM", "SubJect image : " + imageFile);
			File file = new File(imageFile);
			if (file.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				holder.image.setImageBitmap(myBitmap);

			}
			//
			String success = home.getSuccess();
			if (success.toLowerCase().equals("done")) {
				holder.star.setVisibility(View.VISIBLE);
			} else {
				holder.star.setVisibility(View.GONE);
			}
		} catch (Exception e) {

		}

		return convertView;
	}

	private class ViewHolder {
		ImageView image, star;
	}

}
