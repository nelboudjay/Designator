package com.myproject.calendar;

public class DayMonthPair {

	private int day;
	private String month;
	private int today; /* 0 is today, negative is past and positive is future */
	

	public DayMonthPair() {
		super();
	}


	
	public DayMonthPair(int day, String month, int today) {
		this.day = day;
		this.month = month;
		this.today = today;
	}
	
	public DayMonthPair(int day) {
		this.day = day;
		this.month = "";
	}

	public DayMonthPair(DayMonthPair dayMonth){
		this.day = dayMonth.getDay();
		this.month = dayMonth.getMonth();
		this.today = dayMonth.getToday();
	}

	public int getDay() {
		return day;
	}

	public String getMonth() {
		return month;
	}

	public int getToday() {
		return today;
	}

}
