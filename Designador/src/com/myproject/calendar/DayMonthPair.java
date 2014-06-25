package com.myproject.calendar;

public class DayMonthPair {

	private int day;
	private String month;
	private boolean isToday;
	

	public DayMonthPair() {
		super();
	}


	
	public DayMonthPair(int day, String month, boolean isToday) {
		this.day = day;
		this.month = month;
		this.isToday = isToday;
	}
	
	public DayMonthPair(int day) {
		this.day = day;
		this.month = "";
	}

	public DayMonthPair(DayMonthPair dayMonth){
		this.day = dayMonth.getDay();
		this.month = dayMonth.getMonth();
		this.isToday = dayMonth.isToday();
	}

	public int getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public boolean isToday() {
		return isToday;
	}

}
