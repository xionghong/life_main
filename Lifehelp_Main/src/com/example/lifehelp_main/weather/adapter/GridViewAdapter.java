package com.example.lifehelp_main.weather.adapter;


import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.db.WeatherDbHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter{
	private String[] citys;
	private Context context;
	private LayoutParams params;
	private WeatherDbHelper helper;
	/**
	 * @param citys
	 * @param context
	 */
	public GridViewAdapter(String[] citys, Context context,int itemHeight) {
		super();
		this.citys = citys;
		this.context = context;
		params = new LayoutParams(LayoutParams.MATCH_PARENT, itemHeight);
		helper = new WeatherDbHelper(context);
	}

	@Override
	public int getCount() {
		return citys.length;
	}

	@Override
	public String getItem(int position) {
		return citys[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_gridview, null);
			convertView.setLayoutParams(params);
			holder = new ViewHolder();
			holder.tvCity = (TextView) convertView.findViewById(R.id.tv_item_gridview);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_item_gridview);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvCity.setText(getItem(position));
		if(helper.existCity(getItem(position))){
			holder.iv.setVisibility(View.VISIBLE);
			convertView.setClickable(true);
			holder.tvCity.setTextColor(0x77444444);
		}else{
			holder.iv.setVisibility(View.GONE);
			convertView.setClickable(false);
			holder.tvCity.setTextColor(0xff444444);
		}
		return convertView;
	}

	class ViewHolder{
		TextView tvCity;
		ImageView iv;
	}
}
