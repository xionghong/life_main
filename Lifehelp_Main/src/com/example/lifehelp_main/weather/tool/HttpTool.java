package com.example.lifehelp_main.weather.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpTool {

	private static HttpTool istance = new HttpTool();
	private HttpTool(){
	}
	public static HttpTool getIstance(){
		return istance;
	}
	public String getContent(String urlStr){
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(8*1000);
			conn.setConnectTimeout(8*1000);
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
