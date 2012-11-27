package com.siberhus.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SimpleDate extends Date {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private Calendar calendar = Calendar.getInstance();
	
	public SimpleDate(String pattern, String value) throws ParseException{
		super(new SimpleDateFormat(pattern).parse(value).getTime());
		calendar.setTime(this);
	}
	
	public SimpleDate() {
		super();
		calendar.setTime(this);
	}

	public SimpleDate(long date) {
		super(date);
		calendar.setTime(this);
	}

	public SimpleDate(Date date) {
		this(date.getTime());
	}
	
	public java.sql.Date toSqlDate(){
		return new java.sql.Date(this.getTime());
	}
	
	public java.sql.Timestamp toSqlTimestamp(){
		return new java.sql.Timestamp(this.getTime());
	}
	
	public java.sql.Time toSqlTime(){
		return new java.sql.Time(this.getTime());
	}
	
	@Override
	public int getDate() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getDayOfMonth() {
		return getDate();
	}

	@Override
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public int getDayOfWeek() {
		return getDay();
	}

	@Override
	public int getHours() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getHourOfDay() {
		return getHours();
	}

	@Override
	public int getMinutes() {
		return calendar.get(Calendar.MINUTE);
	}

	@Override
	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	@Override
	public int getSeconds() {
		return calendar.get(Calendar.SECOND);
	}

	@Override
	public int getTimezoneOffset() {
		return -(calendar.get(Calendar.ZONE_OFFSET) + calendar
				.get(Calendar.DST_OFFSET))
				/ (60 * 1000);
	}

	public int getCalendarYear() {
		return calendar.get(Calendar.YEAR);
	}

	@Override
	public void setDate(int date) {
		calendar.set(Calendar.DAY_OF_MONTH, date);
	}

	public void setDayOfMonth(int date) {
		setDate(date);
	}

	@Override
	public void setHours(int hours) {
		calendar.set(Calendar.HOUR_OF_DAY, hours);
	}

	public void setHourOfDay(int hours) {
		setHours(hours);
	}

	@Override
	public void setMinutes(int minutes) {
		calendar.set(Calendar.MINUTE, minutes);
	}

	@Override
	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, month);
	}

	@Override
	public void setSeconds(int seconds) {
		calendar.set(Calendar.SECOND, seconds);
	}

	public void setCalendarYear(int year) {
		calendar.set(Calendar.YEAR, year);
	}
}
