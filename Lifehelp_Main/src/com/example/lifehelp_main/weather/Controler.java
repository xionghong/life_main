package com.example.lifehelp_main.weather;

import java.util.ArrayList;
import java.util.List;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.adapter.WeatherListAdapter;
import com.example.lifehelp_main.weather.adapter.WeatherPagerAdapter;
import com.example.lifehelp_main.weather.db.WeatherDbHelper;
import com.example.lifehelp_main.weather.entity.Weather;
import com.example.lifehelp_main.weather.entity.WeatherSub;
import com.example.lifehelp_main.weather.tool.NetToData;
import com.example.lifehelp_main.weather.tool.NetToData.OnCompleteListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class Controler {
	private ViewPager viewPager;
	private TextView tvCityName;
	/** 包裹指示器图片的ViewGroup **/
	private LinearLayout indicatorGroup;
	private ListView listView;
	/** 添加城市的TextView **/
	private TextView btnAddCity;
	private Activity context;
	/** ViewPager的Adapter **/
	private WeatherPagerAdapter pagerAdapter;
	/** 数据库辅助类 **/
	private WeatherDbHelper helper;
	/** 指示器图片的数组 **/
	private View[] indicators;
	/** ViewPager中页面的视图集合 **/
	private List<View> views;
	/** 添加的所有城市集合 **/
	private List<String> citys;
	private RotateAnimation anim;
	private WeatherListAdapter listAdapter;

	public Controler(ViewPager viewPager, TextView tvCityName,
			LinearLayout indicatorGroup, ListView listView,
			TextView btnAddCity, Activity context) {
		super();
		this.viewPager = viewPager;
		this.tvCityName = tvCityName;
		this.indicatorGroup = indicatorGroup;
		this.listView = listView;
		this.btnAddCity = btnAddCity;
		this.context = context;
		init();
	}

	/** 初始化设置 **/
	private void init() {
		anim = new RotateAnimation(0, 3600, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(10 * 1000);
		anim.setRepeatCount(-1);
		anim.setFillAfter(true);
		helper = new WeatherDbHelper(context);
		listView.setOnTouchListener(onTouchListener);
		btnAddCity.setOnClickListener(clickListener);
		initViewPager();
		initIndicator();
		ininListView();
	}

	private void ininListView() {
		List<WeatherSub> weathers = new ArrayList<WeatherSub>();
		listAdapter = new WeatherListAdapter(weathers, context);
		listView.setAdapter(listAdapter);
	}

	/** 初始化ViewPager **/
	private void initViewPager() {
		views = new ArrayList<View>();
		citys = helper.getCitys();
		for (int i = 0; i < citys.size(); i++) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.item_pager, null);
			views.add(view);
			ViewHolder holder = new ViewHolder();
			holder.temGroup = (LinearLayout) view
					.findViewById(R.id.item_page_temGroup);
			holder.btnUpdate = (ImageButton) view
					.findViewById(R.id.item_page_updateBtn);
			holder.line = view.findViewById(R.id.item_page_line);
			holder.tvUpdateTime = (TextView) view
					.findViewById(R.id.item_page_publishTimeTv);
			holder.tvWeather = (TextView) view
					.findViewById(R.id.item_page_weatherTv);
			holder.tvWind = (TextView) view.findViewById(R.id.item_page_windTv);
			view.setTag(holder);
		}
		pagerAdapter = new WeatherPagerAdapter(views);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(pageChangeListener);
		viewPager.setOnTouchListener(onTouchListener);
	}

	/** 为ViewPager设置的监听器 **/
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < citys.size(); i++) {
				if (i == arg0) {
					indicators[i]
							.setBackgroundResource(R.drawable.shape_bg_indicator_selected);
				} else {
					indicators[i]
							.setBackgroundResource(R.drawable.shape_bg_indicator_unselected);
				}
			}
			tvCityName.setText(citys.get(arg0));
			updateWeather(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	/** 更新第index页的天气数据 **/
	private void updateWeather(int index) {
		ViewHolder holder = (ViewHolder) views.get(index).getTag();
		invisibleView(holder);
		holder.btnUpdate.startAnimation(anim);
		String cityName = citys.get(index);
		String cityCode = helper.getCityCode(cityName);
		String urlStr = WeatherConstant.WEATHER_URL.replace(
				WeatherConstant.CITY_KEY, cityCode);
		NetToData.getIstance().getWeather(urlStr, new OnCompleteListener() {

			@Override
			public void onSuccess(Message msg) {
				handler.sendMessage(msg);
			}

			@Override
			public void onError() {

			}
		}, handler.obtainMessage(0, index, 0));
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			Weather weather = (Weather) bundle.getSerializable("weather");
			int index = msg.arg1;
			ViewHolder holder = (ViewHolder) views.get(index).getTag();
			visibleView(holder);
			holder.btnUpdate.clearAnimation(); // 清除动画
			holder.tvUpdateTime.setText("发布时间：" + weather.getUpdatetime());
			holder.tvWind.setText(weather.getFengxiang() + "，"
					+ weather.getFengli());
			holder.tvWeather.setText(weather.getWeatherSubs().get(0)
					.getWeatherDay());
			String wendu = weather.getWendu();
			handleTem(wendu, holder.temGroup);

			// 更新ListView的数据
			listAdapter.reflushData(weather.getWeatherSubs());
		}
	};

	private void visibleView(ViewHolder holder) {
		holder.temGroup.setVisibility(View.VISIBLE);
		holder.line.setVisibility(View.VISIBLE);
		holder.tvUpdateTime.setVisibility(View.VISIBLE);
		holder.tvWeather.setVisibility(View.VISIBLE);
		holder.tvWind.setVisibility(View.VISIBLE);
	}

	private void invisibleView(ViewHolder holder) {
		holder.temGroup.setVisibility(View.INVISIBLE);
		holder.line.setVisibility(View.INVISIBLE);
		holder.tvUpdateTime.setVisibility(View.INVISIBLE);
		holder.tvWeather.setVisibility(View.INVISIBLE);
		holder.tvWind.setVisibility(View.INVISIBLE);
	}

	/** 处理温度显示 **/
	private void handleTem(String wendu, LinearLayout temGroup) {
		temGroup.removeAllViews();
		for (int i = 0; i < wendu.length(); i++) {
			char c = wendu.charAt(i);
			int num = Integer.parseInt(c + "");
			ImageView iv = new ImageView(context);
			iv.setImageResource(NUM_IMG_IDS[num]);
			temGroup.addView(iv);// 向容器里添加一个View
		}
		ImageView iv = new ImageView(context);
		iv.setImageResource(NUM_IMG_IDS[NUM_IMG_IDS.length - 1]);
		temGroup.addView(iv);// 向容器里添加一个View
	};

	public static final int[] NUM_IMG_IDS = { R.drawable.widget_4x2_time_zero,
			R.drawable.widget_4x2_time_one, R.drawable.widget_4x2_time_two,
			R.drawable.widget_4x2_time_three, R.drawable.widget_4x2_time_four,
			R.drawable.widget_4x2_time_five, R.drawable.widget_4x2_time_six,
			R.drawable.widget_4x2_time_seven, R.drawable.widget_4x2_time_eight,
			R.drawable.widget_4x2_time_nine, R.drawable.widget_4x2_temp_degree };

	class ViewHolder {
		LinearLayout temGroup;
		TextView tvWeather;
		TextView tvWind;
		TextView tvUpdateTime;
		ImageButton btnUpdate;
		View line;
	}

	/** 初始化指示器圆形图片 **/
	private void initIndicator() {
		indicatorGroup.removeAllViews();
		indicators = new View[citys.size()];
		LayoutParams params = new LayoutParams(15, 15);
		params.rightMargin = 8;
		for (int i = 0; i < citys.size(); i++) {
			indicators[i] = new View(context);
			if (i == 0) {
				indicators[i]
						.setBackgroundResource(R.drawable.shape_bg_indicator_selected);
			} else {
				indicators[i]
						.setBackgroundResource(R.drawable.shape_bg_indicator_unselected);
			}
			indicators[i].setLayoutParams(params);
			indicatorGroup.addView(indicators[i]);
		}
	}

	/** 单击监听器 **/
	private OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, AddCityAty.class);
			context.startActivityForResult(intent, 1);
		}
	};
	/** 代表ListView现在是否处于上升的状态 **/
	private boolean isUp = false;
	private OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			MyFrameLayout container = (MyFrameLayout) listView.getParent();
			if (v == viewPager) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (isUp) {
						container
								.smoothScrollTo(-WeatherConstant.heightContainerViewPager);
						isUp = !isUp;
					}
				}
				return isUp;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {

				if (isUp) {
					container
							.smoothScrollTo(-WeatherConstant.heightContainerViewPager);
				} else {
					container
							.smoothScrollTo(-WeatherConstant.heightContainerViewPager
									+ WeatherConstant.heightListView * 2 / 5);
				}
				isUp = !isUp;
			}
			return true;
		}
	};

	public void reflushAddCity() {
		initViewPager();
		initIndicator();
		// 设置ViewPager的当前页
		viewPager.setCurrentItem(citys.size() - 1);
	}
}
