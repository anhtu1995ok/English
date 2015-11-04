package com.vn.studiotruekids.englishforkids.adapter;

import java.io.File;
import java.util.ArrayList;

import com.vn.studiotruekids.englishforkids.R;
import com.vn.studiotruekids.englishforkids.model.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalListViewAdapter extends ArrayAdapter<Home> {
	private Context context;
	int resource;
	ArrayList<Home> arrayList;

	public HorizontalListViewAdapter(Context context, int resource, ArrayList<Home> arrayList) {
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
//		ViewHolder holder = null;

		Home home = arrayList.get(position);

//		if (convertView == null) {
			convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource,
					null);
//			holder = new ViewHolder();
//			holder.image = (ImageView) convertView.findViewById(R.id.image);
//			holder.title = (TextView) convertView.findViewById(R.id.title);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		ImageView image = (ImageView) convertView.findViewById(R.id.image);
		TextView title = (TextView) convertView.findViewById(R.id.title);

		String imageFile = Environment.getExternalStorageDirectory() + "/Sjsoft/Home/Icon/" + home.getIcon() + ".jsc";
		final String name = home.getTitle();
		File file = new File(imageFile);
		if (file.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			image.setImageBitmap(myBitmap);
		}
		try {
			Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/brlnsb.ttf");
			title.setTypeface(type);

			title.setText(name);
		} catch (Exception e) {
		}

		return convertView;
	}

//	private static class ViewHolder {
//		ImageView image;
//		TextView title;
//	}

}
