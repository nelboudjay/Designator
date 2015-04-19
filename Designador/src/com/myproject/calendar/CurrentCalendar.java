package com.myproject.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;


public class CurrentCalendar {

	private DayMonthPair [][] currentCalendar;

	public CurrentCalendar() {
		
		currentCalendar = new DayMonthPair[5][7];
		for(int i = 0; i <  currentCalendar.length; i++){
			for(int j = 0; j < currentCalendar[i].length; j++)
					currentCalendar[i][j] = new DayMonthPair(getDayMonthByIndex(i,j));	
		}
	}
	
	public DayMonthPair [][] getCurrentCalendar(){
		return currentCalendar;
	}

	public DayMonthPair getDayMonthByIndex(int i, int j){
		

		SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",new Locale("es","ES"));
		Calendar calendar = Calendar.getInstance();
		
		int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1;
		
		calendar.add(Calendar.DATE, 1 - dayOfWeek + i*7 + j);
		
		return new DayMonthPair(calendar.get(Calendar.DAY_OF_MONTH),
				WordUtils.capitalize(monthFormat.format(calendar.getTime())),
				(1 - dayOfWeek + i*7 + j));
				
		
	}
	
}
