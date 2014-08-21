package com.example.lifehelp_main.weather.adapter;

import java.util.List;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.WeatherConstant;
import com.example.lifehelp_main.weather.entity.WeatherSub;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeatherListAdapter extends BaseAdapter{
	private List<WeatherSub> weathers;
	private Context context;
	private LayoutParams params;
	/**
	 * @param weathers
	 * @param context
	 */
	public WeatherListAdapter(List<WeatherSub> weathers, Context context) {
		super();
		this.weathers = weathers;
		this.context = context;
		params = new LayoutParams(LayoutParams.MATCH_PARENT, WeatherConstant.itemHeight);
	}

	@Override
	public int getCount() {
		return weathers.size();
	}

	@Override
	public WeatherSub getItem(int position) {
		return weathers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_weather, null);
			convertView.setLayoutParams(params);
			holder = new ViewHolder();
			holder.tvDtae = (TextView) convertView.findViewById(R.id.weather_item_dateTv);
			holder.tvWeather = (TextView) convertView.findViewById(R.id.weather_item_weatherTv);
			holder.tvTem = (TextView) convertView.findViewById(R.id.weather_item_temTv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		WeatherSub weatherSub = getItem(position);
		holder.tvDtae.setText(handleDate(weatherSub.getDate()));
		holder.tvWeather.setText(handleWeather(weatherSub.getWeatherDay(),weatherSub.getWeatherNight()));
		holder.tvTem.setText(handleTem(weatherSub.getTemHigh(),weatherSub.getTemLow()));
		return convertView;
	}

	/**处理温度**/
	private String handleTem(String temHigh, String temLow) {
		String tem1 = temHigh.split(" ")[1];
		String tem2 = temLow.split(" ")[1];
		tem2 = tem2.replace("℃", "");
		return tem2+"~"+tem1;
	}
	/**处理天气**/
	private String handleWeather(String weatherDay, String weatherNight) {
		if(weatherDay.equals(weatherNight)){
			return weatherDay;
		}
		return weatherDay+"转"+weatherNight;
	}
	/**处理天气**/
	private String handleDate(String date) {
		//TODO
		return date;
	}
	class ViewHolder{
		TextView tvDtae;
		TextView tvWeather;
		TextView tvTem;
	}
	/**更换数据**/
	public void reflushData(List<WeatherSub> weathers){
		this.weathers.clear();
		this.weathers.addAll(weathers);
		notifyDataSetChanged();
	}
}
