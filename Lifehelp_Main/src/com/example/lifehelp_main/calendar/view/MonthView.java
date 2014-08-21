package com.example.lifehelp_main.calendar.view;



import com.example.lifehelp_main.calendar.Constant;
import com.example.lifehelp_main.calendar.until.CalendarTool;
import com.example.lifehelp_main.calendar.until.LunarCalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class MonthView extends View {
	private int itemWidth = Constant.itemWidth;
	private int itemHight = Constant.monthViewItemHight;
	/** 画阳历日期的画笔 **/
	private Paint paint1;
	/** 画农历日期的画笔 **/
	private Paint paint2;
	/** 画圆的画笔 **/
	private Paint paintCircle;
	/** 里面的每个元素是：25-初五 **/
	private String[] dayNums = new String[42];
	private RectF mRectF;
	/** 基准线 **/
	private int textBaseLine;

	public MonthView(Context context) {
		super(context);
		init(context);
	}

	public MonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		paint1 = new Paint();
		paint1.setTextSize(35);
		/** 为画笔设置字体 **/
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/MIUI_Normal.ttf");
		paint1.setTypeface(tf);

		paint2 = new Paint();
		paint2.setTextSize(15);
		paint2.setColor(0x44444444);

		paintCircle = new Paint();
		paintCircle.setAntiAlias(true);
		paintCircle.setColor(0xffd0382e);

		calendarTool = CalendarTool.getIstance();
		calendar = LunarCalendar.getIstance();
		// 设置画文字的竖直方向的基准线
		FontMetricsInt fontMetrics = paint1.getFontMetricsInt();
		mRectF = new RectF(0, 0, Constant.screenWidth, itemHight);
		textBaseLine = (int) (mRectF.top
				+ (mRectF.bottom - mRectF.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top);

	}

	private CalendarTool calendarTool;
	private LunarCalendar calendar;
	/** 是否是闰年 **/
	private boolean isLeapyear;
	/** 这个月有多少天 **/
	private int daysOfMonth;
	/** 上个月有多少天 **/
	private int daysOfLastMonth;
	/** 这个月的第一天是星期几 0代表星期日 **/
	private int weekOfFirstDay;

	/** 代表上个月应该显示的天数 **/
	private int daysOfLast;
	long startT, start1;

	public void setDayNums(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		start1 = System.currentTimeMillis();
		curDay = day;
		isLeapyear = calendarTool.isLeapYear(year);
		daysOfMonth = calendarTool.getDaysOfMonth(isLeapyear, month);
		daysOfLastMonth = calendarTool.getDaysOfMonth(isLeapyear, month - 1);
		weekOfFirstDay = calendarTool.getFirstWeekdayOfMonth(year, month);
		daysOfLast = weekOfFirstDay == 0 ? 7 : weekOfFirstDay;
		for (int i = 0; i < dayNums.length; i++) {
			// 设置上个月最后那么几天
			if (i < daysOfLast) {
				// 星期三
				// day 29 30 31 1 30 1
				// i 0 1 2 3 32 33
				startT = System.currentTimeMillis();
				String lunarDate = calendar.getLunarDate(year, month - 1,
						daysOfLastMonth - daysOfLast + 1 + i, false);
				dayNums[i] = daysOfLastMonth - daysOfLast + 1 + i + "-"
						+ lunarDate;
				continue; // 结束本次循环
			}
			// 设置本月
			if (i < daysOfMonth + daysOfLast) {
				startT = System.currentTimeMillis();

				String lunarDate = calendar.getLunarDate(year, month, i
						- daysOfLast + 1, false);
				dayNums[i] = i - daysOfLast + 1 + "-" + lunarDate;
				continue;
			}
			// 设置下个月
			startT = System.currentTimeMillis();
			String lunarDate = calendar.getLunarDate(year, month + 1, i
					- daysOfMonth - daysOfLast + 1, false);
			dayNums[i] = i - daysOfMonth - daysOfLast + 1 + "-" + lunarDate;

		}
		System.out.println("总用时----" + (System.currentTimeMillis() - start1));
	}

	private int curDay;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < dayNums.length; i++) {
			int line = i / 7; // 行号
			int column = i % 7; // 列号
			// 获取阳历对应的日期
			String text1 = dayNums[i].split("-")[0];
			if (i < daysOfLast) {// 为上个月的文字设置画笔颜色
				paint1.setColor(0x44444444);
				paint2.setColor(0x44444444);
			} else if (i < daysOfMonth + daysOfLast) {
				// 画圆
				if (text1.equals(curDay + "")) {
					// 得到周几
					weekDay = column;
					// 获取到圆的半径
					int r = itemHight < itemWidth ? itemHight / 2
							: itemWidth / 2;
					canvas.drawCircle(column * itemWidth + itemWidth / 2, line
							* itemHight + itemHight / 2, r, paintCircle);
					paint1.setColor(0xffffffff);
					paint2.setColor(0xffffffff);
				} else {
					// 为本月的文字设置画笔颜色
					paint1.setColor(0xdd000000);
					paint2.setColor(0x66444444);
				}
			} else {
				paint1.setColor(0x44444444);
				paint2.setColor(0x44444444);
			}
			// 测量出文字的宽
			float measureText1 = paint1.measureText(text1);
			canvas.drawText(text1, column * itemWidth
					+ (itemWidth - measureText1) / 2, textBaseLine + line
					* itemHight, paint1);

			// 画农历
			String text2 = dayNums[i].split("-")[1];
			float measureText2 = paint2.measureText(text2);
			canvas.drawText(text2, column * itemWidth
					+ (itemWidth - measureText2) / 2, textBaseLine + line
					* itemHight + 17, paint2);

		}
	}

	private int year;
	private int month;
	private int day;
	private int weekDay;

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getWeekDay() {
		return weekDay;
	}
}
