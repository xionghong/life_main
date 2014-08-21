package com.example.lifehelp_main.weather;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.adapter.GridViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AddCityAty extends Activity {
	// 定义相关
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			String address = location.getAddrStr();
			String city = location.getCity();
			System.out.println(city + "\n" + address);
			if (city.endsWith("市")) {
				city = city.substring(0, city.length() - 1);
			}
			Intent intent = getIntent();
			intent.putExtra("city_name", city);
			setResult(RESULT_OK, intent);
			finish();
		}
	};

	private String[] citys = { "定位", "北京", "上海", "广州", "深圳", "珠海", "佛山", "南京",
			"苏州", "杭州", "济南", "青岛", "郑州", "石家庄", "福州", "厦门", "武汉", "长沙", "成都",
			"重庆", "太原", "沈阳", "南宁", "西安", "贵阳", "安顺" };
	private GridView gridView;
	private GridViewAdapter adapter;
	/** 返回按钮 **/
	private ImageButton btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_add_city);
		initLocation();

		gridView = (GridView) findViewById(R.id.gridView);
		gridView.getViewTreeObserver().addOnGlobalLayoutListener(
				globalLayoutListener);
		gridView.setOnItemClickListener(itemCLickListener);
		btnBack = (ImageButton) findViewById(R.id.btnBack_add_city);
		btnBack.setOnClickListener(clickListener);
	}

	/** 初始化定位相关的 **/
	private void initLocation() {
		// 声明LocationClient类
		mLocationClient = new LocationClient(getApplicationContext());
		// 创建一个“设置”
		LocationClientOption option = new LocationClientOption();
		// 设置定位参数
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		mLocationClient.setLocOption(option);
		// 注册监听函数
		mLocationClient.registerLocationListener(myListener);
	}

	private OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			setResult(RESULT_OK);
			finish();
		}
	};
	/** 为GridView设置的item单击监听 **/
	OnItemClickListener itemCLickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == 0) {
				// 定位
				mLocationClient.start();
			} else {
				String cityName = adapter.getItem(position);
				Intent intent = getIntent();
				intent.putExtra("city_name", cityName);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	};

	private boolean isFirst = true;
	OnGlobalLayoutListener globalLayoutListener = new OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {
			if (isFirst) {
				isFirst = false;
				int itemHeight = gridView.getHeight() / 8;
				adapter = new GridViewAdapter(citys, AddCityAty.this,
						itemHeight);
				gridView.setAdapter(adapter);
			}
		}
	};

	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
	};
}
