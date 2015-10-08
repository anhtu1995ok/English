package com.android.sjsofteducationapp.adapter;

import java.util.ArrayList;

import com.android.sjsofteducationapp.R;
import com.android.sjsofteducationapp.SubjectActivity;
import com.android.sjsofteducationapp.model.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
			holder.main = (LinearLayout) convertView.findViewById(R.id.main);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);

			convertView.setTag(holder);
		}

		int imageUrl = home.getUrl_image();
		final String title = home.getTitle();
		holder.image.setImageResource(imageUrl);

		try {
			Typeface type = Typeface.createFromAsset(context.getAssets(), "brlnsb.ttf");
			holder.title.setTypeface(type);

			holder.title.setText(title);
		} catch (Exception e) {
		}
		
		holder.main.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				holder.main.startAnimation(AnimationUtils.loadAnimation(context,
						R.anim.abc_fade_in));
				Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
				if (title.equalsIgnoreCase("Animals")) {
					Intent intent = new Intent(context, SubjectActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			}
		});

		return convertView;
	}

	private class ViewHolder {
		ImageView image;
		TextView title;
		LinearLayout main;
	}

}
