package com.myproject.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;

public class MonthCalendar {

	private DayMonthPair[][] monthCalendar;
	private String yearMonth;

	public MonthCalendar() {
		super();
	}

	public void setMonthCalendar(String yearMonth){
	
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",new Locale("es","ES"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sdf.setLenient(false);
		
		long diffDays;
		long today = (new Date()).getTime();
		long calDay;
		
		try {
			Date date = sdf.parse(yearMonth);
			calendar.setTime(date);
			
			int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 ;
			
			if (monthDays == 28 && dayOfWeek == 1)
				monthCalendar = new DayMonthPair[4][7];
			else
				monthCalendar = new DayMonthPair[5][7];

			for(int i = 0; i < monthCalendar[0].length; i++){
				if(i < dayOfWeek)
					monthCalendar[0][i] = null;
				else{
					calendar.set(Calendar.DATE, i - dayOfWeek + 1);
					calDay = calendar.getTime().getTime();
					diffDays = (calDay - today)/ (1000 * 60 * 60 * 24);
					monthCalendar[0][i] = new DayMonthPair(i - dayOfWeek + 1,
							WordUtils.capitalize(monthFormat.format(calendar.getTime())),(int)diffDays);
				}
			}
			
			for(int i = 1; i <  monthCalendar.length; i++){
				for(int j = 0; j < monthCalendar[i].length; j++){
					if(i*7 - dayOfWeek + 1 + j <= monthDays){
						calendar.set(Calendar.DATE, i*7 - dayOfWeek + 1 + j);
						calDay = calendar.getTime().getTime();
						diffDays = (calDay - today)/ (1000 * 60 * 60 * 24);
						monthCalendar[i][j] =    new DayMonthPair(i*7 - dayOfWeek + 1 + j,
								WordUtils.capitalize(monthFormat.format(calendar.getTime())),(int)diffDays);
					}else
						monthCalendar[i][j] = null;
				}
			}						

		} catch (ParseException e) {
			
			calendar.set(Calendar.DATE, 1);

			int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 ;

			if (monthDays == 28 && dayOfWeek == 1)
				monthCalendar = new DayMonthPair[4][7];
			else
				monthCalendar = new DayMonthPair[5][7];

			for(int i = 0; i < monthCalendar[0].length; i++){
				if(i < dayOfWeek)
					monthCalendar[0][i] = null;
				else{
					calendar.set(Calendar.DATE, i - dayOfWeek + 1);
					calDay = calendar.getTime().getTime();
					diffDays = (calDay - today)/ (1000 * 60 * 60 * 24);
					monthCalendar[0][i] = new DayMonthPair(i - dayOfWeek + 1,
							WordUtils.capitalize(monthFormat.format(calendar.getTime())),(int)diffDays);
				}
			}

			for(int i = 1; i <  monthCalendar.length; i++){
				for(int j = 0; j < monthCalendar[i].length; j++){
					if(i*7 - dayOfWeek + 1 + j <= monthDays){
						calendar.set(Calendar.DATE, i*7 - dayOfWeek + 1 + j);
						calDay = calendar.getTime().getTime();
						diffDays = (calDay - today)/ (1000 * 60 * 60 * 24);
						monthCalendar[i][j] = new DayMonthPair(i*7 - dayOfWeek + 1 + j,
								WordUtils.capitalize(monthFormat.format(calendar.getTime())),(int)diffDays);
					}else
						monthCalendar[i][j] = null;
				}
			}	
		}
	}
	
	public DayMonthPair[][] getMonthCalendar() {
		return monthCalendar;
	}

	public void setMonthCalendar(DayMonthPair[][] monthCalendar) {
		this.monthCalendar = monthCalendar;
	}
	
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		setMonthCalendar(yearMonth);
		this.yearMonth = yearMonth;
	}
}
