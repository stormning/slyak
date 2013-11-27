/**
 * Project name : slyak-web
 * File name : DateUtils.java
 * Package name : com.slyak.util
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Calendar getCalendar(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date == null){
			calendar.setTime(new Date());
		}else{
			calendar.setTime(date);
		}
		return calendar;
	}
	
	public static void setToStartTimeOfTheDay(Calendar c){
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}
	
	public static void setToEndTimeOfTheDay(Calendar c){
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
	}
	
	public static Calendar getFirstDayOf(Date date,int field){
		Calendar c = getCalendar(date);
		setToStartTimeOfTheDay(c);
		int index = c.getActualMinimum(field);
		if(Calendar.DAY_OF_WEEK == field){
			index++;
		}else if(Calendar.DAY_OF_WEEK_IN_MONTH == field){
			//TODO 
		}
		c.set(field,index);
		return c;
	}
	
	public static Calendar getFirstDayOfNext(Date date,int field){
		Calendar c = getFirstDayOf(date,field);
		c.add(field, 1);
		return c;
	}
	
	public static Calendar getLastDayBegining(Date date,int field){
		Calendar c = getCalendar(date);
		setToStartTimeOfTheDay(c);
		c.set(field,c.getActualMaximum(field));
		return c;
	}
	
	public static Calendar getLastDayEnding(Date date,int field){
		Calendar c = getCalendar(date);
		setToEndTimeOfTheDay(c);
		c.set(field,c.getActualMaximum(field));
		return c;
	}
	
	public static int get(Date date,int field){
		return getCalendar(date).get(field);
	}
	
}
