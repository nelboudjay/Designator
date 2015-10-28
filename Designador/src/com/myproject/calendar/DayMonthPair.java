package com.myproject.calendar;

public class DayMonthPair {

	private int day;
	private int month;
	private String monthName;
	private int today; /* 0 is today, negative is past and positive is future */
	

	public DayMonthPair() {
		super();
	}


	
	public DayMonthPair(int day, int month, String monthName, int today) {
		this.day = day;
		this.month = month;
		this.monthName = monthName;
		this.today = today;
	}
	
	public DayMonthPair(int day) {
		this.day = day;
		this.monthName = "";
	}

	public DayMonthPair(DayMonthPair dayMonth){
		this.day = dayMonth.getDay();
		this.month = dayMonth.getMonth();
		this.monthName = dayMonth.getMonthName();
		this.today = dayMonth.getToday();
	}

	public int getDay() {
		return day;
	}

	public String getMonthName() {
		return monthName;
	}

	public int getMonth(){
		return month;
	}
	public int getToday() {
		return today;
	}

}
