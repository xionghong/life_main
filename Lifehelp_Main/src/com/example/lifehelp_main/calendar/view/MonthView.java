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
	/** ���������ڵĻ��� **/
	private Paint paint1;
	/** ��ũ�����ڵĻ��� **/
	private Paint paint2;
	/** ��Բ�Ļ��� **/
	private Paint paintCircle;
	/** �����ÿ��Ԫ���ǣ�25-���� **/
	private String[] dayNums = new String[42];
	private RectF mRectF;
	/** ��׼�� **/
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
		/** Ϊ������������ **/
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
		// ���û����ֵ���ֱ����Ļ�׼��
		FontMetricsInt fontMetrics = paint1.getFontMetricsInt();
		mRectF = new RectF(0, 0, Constant.screenWidth, itemHight);
		textBaseLine = (int) (mRectF.top
				+ (mRectF.bottom - mRectF.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top);

	}

	private CalendarTool calendarTool;
	private LunarCalendar calendar;
	/** �Ƿ������� **/
	private boolean isLeapyear;
	/** ������ж����� **/
	private int daysOfMonth;
	/** �ϸ����ж����� **/
	private int daysOfLastMonth;
	/** ����µĵ�һ�������ڼ� 0���������� **/
	private int weekOfFirstDay;

	/** �����ϸ���Ӧ����ʾ������ **/
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
			// �����ϸ��������ô����
			if (i < daysOfLast) {
				// ������
				// day 29 30 31 1 30 1
				// i 0 1 2 3 32 33
				startT = System.currentTimeMillis();
				String lunarDate = calendar.getLunarDate(year, month - 1,
						daysOfLastMonth - daysOfLast + 1 + i, false);
				dayNums[i] = daysOfLastMonth - daysOfLast + 1 + i + "-"
						+ lunarDate;
				continue; // ��������ѭ��
			}
			// ���ñ���
			if (i < daysOfMonth + daysOfLast) {
				startT = System.currentTimeMillis();

				String lunarDate = calendar.getLunarDate(year, month, i
						- daysOfLast + 1, false);
				dayNums[i] = i - daysOfLast + 1 + "-" + lunarDate;
				continue;
			}
			// �����¸���
			startT = System.currentTimeMillis();
			String lunarDate = calendar.getLunarDate(year, month + 1, i
					- daysOfMonth - daysOfLast + 1, false);
			dayNums[i] = i - daysOfMonth - daysOfLast + 1 + "-" + lunarDate;

		}
		System.out.println("����ʱ----" + (System.currentTimeMillis() - start1));
	}

	private int curDay;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < dayNums.length; i++) {
			int line = i / 7; // �к�
			int column = i % 7; // �к�
			// ��ȡ������Ӧ������
			String text1 = dayNums[i].split("-")[0];
			if (i < daysOfLast) {// Ϊ�ϸ��µ��������û�����ɫ
				paint1.setColor(0x44444444);
				paint2.setColor(0x44444444);
			} else if (i < daysOfMonth + daysOfLast) {
				// ��Բ
				if (text1.equals(curDay + "")) {
					// �õ��ܼ�
					weekDay = column;
					// ��ȡ��Բ�İ뾶
					int r = itemHight < itemWidth ? itemHight / 2
							: itemWidth / 2;
					canvas.drawCircle(column * itemWidth + itemWidth / 2, line
							* itemHight + itemHight / 2, r, paintCircle);
					paint1.setColor(0xffffffff);
					paint2.setColor(0xffffffff);
				} else {
					// Ϊ���µ��������û�����ɫ
					paint1.setColor(0xdd000000);
					paint2.setColor(0x66444444);
				}
			} else {
				paint1.setColor(0x44444444);
				paint2.setColor(0x44444444);
			}
			// ���������ֵĿ�
			float measureText1 = paint1.measureText(text1);
			canvas.drawText(text1, column * itemWidth
					+ (itemWidth - measureText1) / 2, textBaseLine + line
					* itemHight, paint1);

			// ��ũ��
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
