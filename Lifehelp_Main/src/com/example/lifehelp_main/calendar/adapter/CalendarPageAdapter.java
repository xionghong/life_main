package com.example.lifehelp_main.calendar.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.lifehelp_main.calendar.view.MonthView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CalendarPageAdapter extends PagerAdapter {

	public static final String TAG = "Adapter";
	public static final int EARLY_YEAR = 1901;
	public static final int EARLY_MONTH = 1;
	public static final int END_YEAR = 2099;
	public static final int END_MONTH = 12;

	private List<MonthView> monthViews;
	private int curDay;

	public CalendarPageAdapter(Context context, int curDay) {
		super();
		this.curDay = curDay;
		monthViews = new ArrayList<MonthView>();
		for (int i = 0; i < 5; i++) {
			monthViews.add(new MonthView(context));
		}
	}

	@Override
	public int getCount() {
		return (END_YEAR - EARLY_YEAR) * 12 + END_MONTH - EARLY_MONTH + 1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (is1) {
			count--;
			if (count == 0) {
				count = 3;
				is1 = false;
			}
			return;
		}
		MonthView monthView = monthViews.get(position % monthViews.size());
		Log.d(TAG, "destroyItem----" + position % monthViews.size());
		container.removeView(monthView);
	}

	// 月份 1 2 3 4 5 6 7 8 9 10 11 12 1 2 3 4
	// position 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15

	// 年 1970 1970 1971 1971
	// position 0 1 12 13
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		MonthView monthView = monthViews.get(position % monthViews.size());
		Log.d(TAG, "instantiateItem----" + position % monthViews.size());
		monthView.setDayNums(position / 12 + 1901, position % 12 + 1, curDay);
		if (is) {
			container.removeAllViews();
			is = false;
			is1 = true;
		}
		container.addView(monthView);
		return monthView;
	}

	private int count = 3;
	public boolean is = false;
	private boolean is1 = false;

	/** 获取某个position对应的view **/
	public MonthView getViewForPosition(int position) {
		return monthViews.get(position % monthViews.size());
	}

	/** 获取某个年月对应的position **/
	public int getPositionForDate(int year, int month) {
		return (year - EARLY_YEAR) * 12 + month - EARLY_MONTH;
	}
}
