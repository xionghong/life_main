package com.example.lifehelp_main.weather;

import java.io.File;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.db.WeatherDbHelper;
import com.example.lifehelp_main.weather.entity.Weather;
import com.example.lifehelp_main.weather.tool.CopyFile;
import com.example.lifehelp_main.weather.tool.HttpTool;
import com.example.lifehelp_main.weather.tool.XmlTool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class WeatherActivity extends FragmentActivity {

	private FrameLayout containerViewPager, containerBottom, containerListView;
	private WeatherDbHelper helper;

	private ViewPager viewPager;
	private TextView tvCityName;
	private LinearLayout indicatorGroup;
	private ListView listView;
	private TextView btnAddCity;
	private Controler controler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeatherConstant.init(this);
		setContentView(R.layout.weather_main);
		initLayoutParams();
		if (isFirstRun()) {
			copyDb();
		}
		helper = new WeatherDbHelper(this);
		initViews();
		checkMyCity();

		new Thread() {
			public void run() {
				String str = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100";
				String xmlStr = HttpTool.getIstance().getContent(str);
				System.out.println(xmlStr);
				Weather weather = XmlTool.getInstance().getWeatherFromXml(
						xmlStr);
				System.out.println(weather.toString());
			};
		}.start();
	}

	/** 初始化控件 **/
	private void initViews() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tvCityName = (TextView) findViewById(R.id.tvCityName);
		indicatorGroup = (LinearLayout) findViewById(R.id.indicatorGroup);
		listView = (ListView) findViewById(R.id.listView);
		btnAddCity = (TextView) findViewById(R.id.tvAdd);
		controler = new Controler(viewPager, tvCityName, indicatorGroup,
				listView, btnAddCity, this);
	}

	/** 检查有没有添加过城市 **/
	private void checkMyCity() {
		if (!helper.existCity()) {
			Intent intent = new Intent(this, AddCityAty.class);
			startActivityForResult(intent, 1);
		}
	}

	/** 设置各个容器的布局参数，设置高度 **/
	private void initLayoutParams() {
		containerViewPager = (FrameLayout) findViewById(R.id.containerViewPager);
		containerListView = (FrameLayout) findViewById(R.id.containerListView);
		containerBottom = (FrameLayout) findViewById(R.id.containerBottom);

		LayoutParams params = (LayoutParams) containerViewPager
				.getLayoutParams();
		params.height = WeatherConstant.heightContainerViewPager;
		containerViewPager.setLayoutParams(params);

		params = (LayoutParams) containerListView.getLayoutParams();
		params.height = WeatherConstant.heightContainerListView;
		containerListView.setLayoutParams(params);
		containerListView
				.scrollTo(0, -WeatherConstant.heightContainerViewPager);

		params = (LayoutParams) containerBottom.getLayoutParams();
		params.height = WeatherConstant.heightContainerBottom;
		containerBottom.setLayoutParams(params);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == RESULT_OK) {
			if (arg2 != null) {
				// 代表添加城市成功
				String city = arg2.getStringExtra("city_name");
				// 插入数据库
				helper.addCity(city);
				// TODO 更新界面
				controler.reflushAddCity();
			} else {
				// 没添加城市
				if (!helper.existCity()) {
					finish();
				}
			}
		}
	}

	/** 判断程序是否是第一次运行 **/
	private boolean isFirstRun() {
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Editor editor = sharedPreferences.edit();
		if (isFirstRun) {
			editor.putBoolean("isFirstRun", false);
			editor.commit();
		}
		return isFirstRun;
	}

	/** 复制数据库 **/
	private void copyDb() {
		File dbFile = getDatabasePath("weathercity.db");
		CopyFile.copyFileFromResToPhone(dbFile.getParent(), dbFile.getName(),
				getResources().openRawResource(R.raw.weathercity));
	}

}
