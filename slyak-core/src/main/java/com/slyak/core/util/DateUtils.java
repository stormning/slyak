/**
 * Project name : slyak-core
 * File name : DateUtils.java
 * Package name : com.slyak.core.util
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	public static Calendar getCalendar(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date == null) {
			calendar.setTime(new Date());
		} else {
			calendar.setTime(date);
		}
		return calendar;
	}

	public static void setToStartTimeOfTheDay(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}

	public static void setToEndTimeOfTheDay(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
	}

	public static Calendar getFirstDayOf(Date date, int field) {
		Calendar c = getCalendar(date);
		setToStartTimeOfTheDay(c);
		int index = c.getActualMinimum(field);
		if (Calendar.DAY_OF_WEEK == field) {
			index++;
		} else if (Calendar.DAY_OF_WEEK_IN_MONTH == field) {
			// TODO
		}
		c.set(field, index);
		return c;
	}

	public static Calendar getFirstDayOfNext(Date date, int field) {
		Calendar c = getFirstDayOf(date, field);
		c.add(field, 1);
		return c;
	}

	public static Calendar getLastDayBegining(Date date, int field) {
		Calendar c = getCalendar(date);
		setToStartTimeOfTheDay(c);
		c.set(field, c.getActualMaximum(field));
		return c;
	}

	public static Calendar getLastDayEnding(Date date, int field) {
		Calendar c = getCalendar(date);
		setToEndTimeOfTheDay(c);
		c.set(field, c.getActualMaximum(field));
		return c;
	}

	public static int get(Date date, int field) {
		return getCalendar(date).get(field);
	}

	/**
	 * today (greater than yesterday) 
	 * 	less than 60 seconds
	 *  less than 60 minutes
	 * 	more than 1 hour
	 * xx day ago (before xx day)
	 * this year other years
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	//TODO i18N
	public static String getDateInfo(Date date){
		Date today = new Date();
		
		String info = null;
		//is this year
		if(DateUtils.truncatedEquals(date, today, Calendar.YEAR)){
			int days = (int) Math.ceil((today.getTime() - date.getTime())/(24*60*60*1000));
			//in 24 hours
			if (days == 0){
				int hours = DateUtils.truncatedCompareTo(today, date, Calendar.HOUR);
				//same hour
				if(hours == 0){
					int minutes = DateUtils.truncatedCompareTo(today, date, Calendar.MINUTE);
					//same minutes
					if(minutes==0){
						info = "刚刚";
					} else{
						info = minutes +"分钟前";
					}
					
				} else {
					info = hours+"小时前";
				}
			} else if (days<=7){
				if(days == 1){
					info = "昨天";
				} else {
					info = days+"天前";
				}
				//one week
			} else {
				info = DateFormatUtils.format(date, "MM-dd mm:ss");
			}
		} else {
			//other years
			info = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return info;
	}
	
	public static void main(String[] args) {
		Date current = new Date();
		System.out.println(getDateInfo(getFirstDayOf(current , Calendar.DAY_OF_MONTH).getTime()));
	}
}
