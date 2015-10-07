package com.android.sjsofteducationapp.adapter;

import java.util.ArrayList;

import com.android.sjsofteducationapp.R;
import com.android.sjsofteducationapp.model.Home;

import android.content.Context;
import android.graphics.Typeface;
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
		final ViewHolder holder;

		Home home = arrayList.get(position);

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);

			convertView.setTag(holder);
		}

		int imageUrl = home.getUrl_image();
		String title = home.getTitle();
		holder.image.setImageResource(imageUrl);

		try {
			Typeface type = Typeface.createFromAsset(context.getAssets(), "brlnsb.ttf");
			holder.title.setTypeface(type);

			holder.title.setText(title);
		} catch (Exception e) {
		}

		return convertView;
	}

	private class ViewHolder {
		ImageView image;
		TextView title;
	}

}
