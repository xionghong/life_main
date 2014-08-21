package com.example.lifehelp_main.calendar;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.calendar.adapter.CalendarPageAdapter;
import com.example.lifehelp_main.calendar.until.CalendarTool;
import com.example.lifehelp_main.calendar.view.MonthView;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

public class CalendarActivity extends Activity{
	
	private TextView tvMonth, tvDate, tvWeekNum, btnToday;
	private ViewPager viewPager;
	private CalendarPageAdapter pageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constant.init(this);

		setContentView(R.layout.calender_main);
		initCurDate();
		initViews();

	}

	/** 初始化控件 **/
	private void initViews() {

		initTitle();
		initViewPager();
	}

	/** 初始化ViewPager **/
	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		pageAdapter = new CalendarPageAdapter(this, curDay);
		viewPager.setAdapter(pageAdapter);
		// 让ViewPager显示当前日期对应的页
		viewPager.setCurrentItem(pageAdapter.getPositionForDate(curYear,
				curMonth));
		viewPager.setOnPageChangeListener(pageChangeListener);

	}

	OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			//
			Log.d("MainAty", "onPageSelected---" + arg0);
			MonthView monthView = pageAdapter.getViewForPosition(arg0);
			int year = monthView.getYear();
			int month = monthView.getMonth();
			int day = monthView.getDay();
			int weekDay = monthView.getWeekDay();
			int weekNum = CalendarTool.getIstance().getWeekNumOfYear(year,
					month, day);
			reflushTitle(year, month, day, weekDay, weekNum);

			if (curYear == year && month == curMonth) {
				btnToday.setVisibility(View.GONE);
			} else {
				btnToday.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	/** 初始化Title栏 **/
	private void initTitle() {
		tvMonth = (TextView) findViewById(R.id.tvMonth);
		tvDate = (TextView) findViewById(R.id.tvDate);
		tvWeekNum = (TextView) findViewById(R.id.tvWeekNum);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/MIUI_Bold.ttf");
		// 为TextView设置字体
		tvMonth.setTypeface(tf);

		int weekNum = CalendarTool.getIstance().getWeekNumOfYear(curYear,
				curMonth, curDay);
		reflushTitle(curYear, curMonth, curDay, curWeekday, weekNum);
		btnToday = (TextView) findViewById(R.id.btnToday);
		btnToday.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == btnToday) {
				int pageCount = Math.abs(viewPager.getCurrentItem()
						- pageAdapter.getPositionForDate(curYear, curMonth));
				if (pageCount <= 2) {
					viewPager.setCurrentItem(pageAdapter.getPositionForDate(
							curYear, curMonth));
				} else {
					pageAdapter.is = true;
					viewPager.setCurrentItem(
							pageAdapter.getPositionForDate(curYear, curMonth),
							false);
				}
			}
		}
	};

	/*** 更新Title的界面 **/
	private void reflushTitle(int year, int month, int day, int weekday,
			int weekNum) {
		tvMonth.setText(month + "");
		tvDate.setText(year + " " + weekdays[weekday]);
		tvWeekNum.setText(String.format(
				getResources().getString(R.string.week_num), weekNum));
	}

	private static final String[] weekdays = { "周日", "周一", "周二", "周三", "周四",
			"周五", "周六" };

	private int curYear;
	private int curMonth;
	private int curDay;
	private int curWeekday;

	/** 获取当前的年月日信息 **/
	private void initCurDate() {
		Time time = new Time();
		time.setToNow();
		curYear = time.year;
		curMonth = time.month + 1;
		curDay = time.monthDay;
		curWeekday = time.weekDay;
	}

	private DatePickerDialog dialog;

	public void selectTime(View v) {
		if (dialog == null) {
			dialog = new DatePickerDialog(this, callBack, curYear, curMonth,
					curDay);
		} else {
			dialog.updateDate(curYear, curMonth, curDay);
		}
		dialog.show();
	}

	OnDateSetListener callBack = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			int pageCount = Math.abs(viewPager.getCurrentItem()
					- pageAdapter.getPositionForDate(year, monthOfYear + 1));
			if (pageCount <= 2) {
				viewPager.setCurrentItem(pageAdapter.getPositionForDate(year,
						monthOfYear + 1));
			} else {
				pageAdapter.is = true;
				viewPager.setCurrentItem(
						pageAdapter.getPositionForDate(year, monthOfYear + 1),
						false);
			}
		}
	};

}
