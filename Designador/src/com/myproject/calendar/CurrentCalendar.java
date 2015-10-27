package com.myproject.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;


public class CurrentCalendar {

	private DayMonthPair [][] currentCalendar;
	private int [][] monthCalendar;
	
	public CurrentCalendar() {
		System.out.println("wrong");
		currentCalendar = new DayMonthPair[5][7];
		for(int i = 0; i <  currentCalendar.length; i++){
			for(int j = 0; j < currentCalendar[i].length; j++)
					currentCalendar[i][j] = new DayMonthPair(getDayMonthByIndex(i,j));	
		}
		monthCalendar = new int[4][7];
		monthCalendar[0][0] = 1;
	}

	public  CurrentCalendar(String yearMonth){
		System.out.println("right");
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sdf.setLenient(false);
		
		try {
			Date date = sdf.parse(yearMonth);
			calendar.setTime(date);
						
			int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 ;

			if (monthDays == 28 && dayOfWeek == 1)
				monthCalendar = new int[4][7];
			else
				monthCalendar = new int[5][7];

			for(int i = 0; i < monthCalendar[0].length; i++){
				if(i < dayOfWeek)
					monthCalendar[0][i] = 0;
				else
					monthCalendar[0][i] = i - dayOfWeek + 1;
			}

			for(int i = 1; i <  monthCalendar.length; i++){
				for(int j = 0; j < monthCalendar[i].length; j++){
					if(i*7 - dayOfWeek + 1 + j <= monthDays)
						monthCalendar[i][j] = i*7 - dayOfWeek + 1 + j;
					else
						monthCalendar[i][j] = 0;
				}
			}						

		} catch (ParseException e) {
			
			calendar.set(Calendar.DATE, 1);
							
			int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 ;

			if (monthDays == 28 && dayOfWeek == 1)
				monthCalendar = new int[4][7];
			else
				monthCalendar = new int[5][7];

			for(int i = 0; i < monthCalendar[0].length; i++){
				if(i < dayOfWeek)
					monthCalendar[0][i] = 0;
				else
					monthCalendar[0][i] = i - dayOfWeek + 1;
			}

			for(int i = 1; i <  monthCalendar.length; i++){
				for(int j = 0; j < monthCalendar[i].length; j++){
					if(i*7 - dayOfWeek + 1 + j <= monthDays)
						monthCalendar[i][j] = i*7 - dayOfWeek + 1 + j;
					else
						monthCalendar[i][j] = 0;
				}
			}	
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

	public void setMonthCalendar(int[][] monthCalendar) {
		this.monthCalendar = monthCalendar;
	}

	public int[][] getMonthCalendar() {
		return monthCalendar;
	}
	
	
	
}
