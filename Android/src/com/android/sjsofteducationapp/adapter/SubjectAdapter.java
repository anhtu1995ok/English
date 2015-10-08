package com.android.sjsofteducationapp.adapter;

import java.util.ArrayList;

import com.android.sjsofteducationapp.R;
import com.android.sjsofteducationapp.model.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
			holder.main = (LinearLayout) convertView.findViewById(R.id.main);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.star = (ImageView) convertView.findViewById(R.id.star);

			convertView.setTag(holder);
		}

		int imageUrl = home.getUrl_image();
		holder.image.setImageResource(imageUrl);
		try {
			boolean success = home.isSuccess();
			if (success) {
				holder.star.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {

		}

		return convertView;
	}

	private class ViewHolder {
		ImageView image, star;
		LinearLayout main;
	}

}
