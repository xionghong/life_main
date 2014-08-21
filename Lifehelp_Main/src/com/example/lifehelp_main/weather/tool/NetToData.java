package com.example.lifehelp_main.weather.tool;



import com.example.lifehelp_main.weather.entity.Weather;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

public class NetToData {
	private NetToData(){
		
	}
	private static final NetToData istance = new NetToData();
	public static NetToData getIstance(){
		return istance;
	}
	
	public void getWeather(String urlStr,OnCompleteListener listener,Message msg){
		new Thread(new GetWeatherThread(urlStr, listener, msg)).start();
	};
	public interface OnCompleteListener{
		void onSuccess(Message msg);
		void onError();
	}
	class GetWeatherThread implements Runnable{
		private String urlStr;
		private OnCompleteListener listener;
		private Message msg;
		public GetWeatherThread(String urlStr, OnCompleteListener listener,
				Message msg) {
			super();
			this.urlStr = urlStr;
			this.listener = listener;
			this.msg = msg;
		}
		@Override
		public void run() {
			String xml = HttpTool.getIstance().getContent(urlStr);
			if(TextUtils.isEmpty(xml)){
				listener.onError();
				return;
			}
			Weather weather = XmlTool.getInstance().getWeatherFromXml(xml);
			Bundle data = new Bundle();
			data.putSerializable("weather", weather);
			msg.setData(data);
			listener.onSuccess(msg);
		}
	}
}
