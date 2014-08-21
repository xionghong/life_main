package com.example.lifehelp_main.calendar.until;

import java.text.BreakIterator;
import java.util.Calendar;

public final class CalendarTool {

	private static final CalendarTool istance = new CalendarTool();

	private CalendarTool() {

	}

	public static final CalendarTool getIstance() {
		return istance;
	}

	private int daysOfMonth = 0; // ĳ�µ�����
	private int dayOfWeek = 0; // ����ĳһ�������ڼ�

	/** �ж��Ƿ�Ϊ���� **/
	public boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 == 0) {
			return true;
		} else if (year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	/** �õ�ĳ���ж������� **/
	public int getDaysOfMonth(boolean isLeapyear, int month) {
		switch (month) {
		case 1:
			daysOfMonth = 31;
			break;
		case 3:
			daysOfMonth = 31;
			break;
		case 5:
			daysOfMonth = 31;
			break;
		case 7:
			daysOfMonth = 31;
			break;
		case 8:
			daysOfMonth = 31;
			break;
		case 10:
			daysOfMonth = 31;
			break;
		case 12:
			daysOfMonth = 31;
			break;
		case 4:
			daysOfMonth = 30;
			break;
		case 6:
			daysOfMonth = 30;
			break;
		case 9:
			daysOfMonth = 30;
			break;
		case 11:
			daysOfMonth = 30;
			break;
		case 2:
			if (isLeapyear) {
				daysOfMonth = 29;
			} else {
				daysOfMonth = 28;
			}

		}
		return daysOfMonth;
	}

	/** ��ȡĳ���е�ĳ�µĵ�һ�������ڼ� **/
	public int getFirstWeekdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}

	// 0 1 2 3 4 5 6 0 1 2 3 4 5 6
	// 1 2 3 4 5 6 7 8 9 10

	/** ��ȡĳ��������ĵڼ��� **/
	public int getWeekNumOfYear(int year, int month, int day) {
		int days = getDaysBeforeDay(year, month, day);
		int today = 0;
		if (days / 7 == 0) {
			today = days / 7;
		} else {
			today = days / 7 + 1;
		}
		return today;
	}

	/** ��ȡĳ��ĳ��ĳ�� ������һ���1��1���ж����� **/
	private int getDaysBeforeDay(int year, int month, int day) {
		boolean isLeapyear = isLeapYear(year);
		int days = 0;
		for (int i = 1; i < month; i++) {
			days += getDaysOfMonth(isLeapyear, month);
		}
		days += day;
		return days;
	}
}
