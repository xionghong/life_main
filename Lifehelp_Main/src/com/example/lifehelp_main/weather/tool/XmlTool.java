package com.example.lifehelp_main.weather.tool;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.lifehelp_main.weather.entity.Weather;
import com.example.lifehelp_main.weather.entity.WeatherSub;
import com.example.lifehelp_main.weather.entity.Zhishu;

import android.util.Xml;

public class XmlTool {
	private XmlTool() {
	}

	private static final XmlTool instance = new XmlTool();

	public static final XmlTool getInstance() {
		return instance;
	}

	public Weather getWeatherFromXml(String xmlStr) {
		XmlPullParser parser = Xml.newPullParser();
		StringReader reader = new StringReader(xmlStr);
		Weather weather = null;
		List<WeatherSub> weatherSubs = null;
		WeatherSub weatherSub = null;
		List<Zhishu> zhishus = null;
		Zhishu zhishu = null;
		boolean isWeather = false;
		boolean isDay = true;
		try {
			parser.setInput(reader);
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					weather = new Weather();
					break;
				case XmlPullParser.START_TAG:
					String tagName = parser.getName();
					if ("city".equals(tagName)) {
						weather.setCity(parser.nextText());
						break;
					}
					if ("updatetime".equals(tagName)) {
						weather.setUpdatetime(parser.nextText());
						break;
					}
					if ("wendu".equals(tagName)) {
						weather.setWendu(parser.nextText());
						break;
					}
					if ("shidu".equals(tagName)) {
						weather.setShidu(parser.nextText());
						break;
					}
					if ("fengli".equals(tagName)) {
						if (!isWeather) {
							weather.setFengli(parser.nextText());
						} else {
							weatherSub.setFengli(parser.nextText());
						}
						break;
					}
					if ("fengxiang".equals(tagName)) {
						if (!isWeather) {
							weather.setFengxiang(parser.nextText());
						} else {
							weatherSub.setFengxiang(parser.nextText());
						}
						break;
					}
					if ("weather".equals(tagName)) {
						weatherSub = new WeatherSub();
						break;
					}
					if ("date".equals(tagName)) {
						weatherSub.setDate(parser.nextText());
						break;
					}
					if ("high".equals(tagName)) {
						weatherSub.setTemHigh(parser.nextText());
						break;
					}
					if ("low".equals(tagName)) {
						weatherSub.setTemLow(parser.nextText());
						break;
					}
					if ("type".equals(tagName)) {
						if (isDay) {
							weatherSub.setWeatherDay(parser.nextText());
						} else {
							weatherSub.setWeatherNight(parser.nextText());
						}
						isDay = !isDay;
						break;
					}
					if ("forecast".equals(tagName)) {
						weatherSubs = new ArrayList<WeatherSub>();
						isWeather = true;
						break;
					}
					if ("zhishus".equals(tagName)) {
						zhishus = new ArrayList<Zhishu>();
						break;
					}
					if ("zhishu".equals(tagName)) {
						zhishu = new Zhishu();
						break;
					}
					if ("name".equals(tagName)) {
						zhishu.setName(parser.nextText());
						break;
					}
					if ("value".equals(tagName)) {
						zhishu.setValue(parser.nextText());
						break;
					}
					if ("detail".equals(tagName)) {
						zhishu.setDetail(parser.nextText());
						break;
					}
					break;
				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if ("weather".equals(tagName)) {
						weatherSubs.add(weatherSub);
						weatherSub = null;
						break;
					}
					if ("forecast".equals(tagName)) {
						weather.setWeatherSubs(weatherSubs);
						break;
					}
					if ("zhishu".equals(tagName)) {
						zhishus.add(zhishu);
						zhishu = null;
						break;
					}
					if ("zhishus".equals(tagName)) {
						weather.setZhishus(zhishus);
						break;
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weather;
	}
}
