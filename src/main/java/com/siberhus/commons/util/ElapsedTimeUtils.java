package com.siberhus.commons.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ElapsedTimeUtils {
	
	public static final short FORMAT_SHORT = 0;
	public static final short FORMAT_MEDIUM = 2;
	public static final short FORMAT_LONG = 3;
	
	private static final long SEC_VAL = 1000;
	private static final long MIN_VAL = SEC_VAL*60;
	private static final long HOUR_VAL = MIN_VAL*60;
	private static final long DAY_VAL = HOUR_VAL*24;
	private static final long MONTH_VAL = DAY_VAL*30;
	private static final long YEAR_VAL = MONTH_VAL*12;
	
	private static final String[] FORMAT_SHORT_STR = new String[]{
		"S","s","m","H","d","M","y"
	};
	private static final String[] FORMAT_MEDIUM_STR = new String[]{
		"ms","sec","min","hr","day","mth","yr"
	};
	private static final String[] FORMAT_LONG_STR = new String[]{
		"millisecond","second","minute","hour","day","month","year"
	};
	
	private static final Map<String, ThreadLocal<Long>> TIME_STORE
		= new ConcurrentHashMap<String, ThreadLocal<Long>>();
	
	public static void start(String name){
		ThreadLocal<Long> tl = TIME_STORE.get(name);
		if(tl==null){
			tl = new ThreadLocal<Long>();
			TIME_STORE.put(name, tl);
		}
		tl.set(System.currentTimeMillis());
	}
	
	public static void reset(String name){
		TIME_STORE.get(name).remove();
	}
	
	public static void remove(String name){
		reset(name);
		TIME_STORE.remove(name);
	}
	
	public static String showElapsedTime(String name){
		return showElapsedTime(name, FORMAT_MEDIUM);
	}
			
	public static String showElapsedTime(String name, short format){
		Long time = TIME_STORE.get(name).get();
		if(time==null){
			return null;
		}
		time = System.currentTimeMillis()-time;
		return formatTime(time, format);
	}
	
	public static String formatTime(Long time, short format){
		String value = "";
		long years=0,months=0,days=0,hours=0,minutes=0,seconds=0; 
		if(time>YEAR_VAL){
			years = time/YEAR_VAL;
			time = time%YEAR_VAL;
			value += decorateYear(years,format);
		}
		if(time>MONTH_VAL){
			months = time/MONTH_VAL;
			time = time%MONTH_VAL;
			value += decorateMonth(months,format);
		}
		if(time>DAY_VAL){
			days = time/DAY_VAL;
			time = time%DAY_VAL;
			value += decorateDay(days,format);
		}
		if(time>HOUR_VAL){
			hours = time/HOUR_VAL;
			time = time%HOUR_VAL;
			value += decorateHour(hours,format);
		}
		if(time>MIN_VAL){
			minutes = time/MIN_VAL;
			time = time%MIN_VAL;
			value += decorateMinute(minutes,format);
		}
		if(time>SEC_VAL){
			seconds = time/SEC_VAL;
			time = time%SEC_VAL;
			value += decorateSecond(seconds,format);
		}
		if(time>=0){
			value += decorateMillisecond(time,format);
		}
		return value;
	}
	
	private static String decorate(long n, short format, int idx){
		String val = "";
		if(format==FORMAT_LONG){
			val = n+FORMAT_LONG_STR[idx];
			if(n>1){
				val = val + "s";
			}
		}else if(format==FORMAT_MEDIUM){
			val = n+FORMAT_MEDIUM_STR[idx];
			if(n>1){
				val = val +"(s)";
			}
		}else if(format==FORMAT_SHORT){
			val = n+FORMAT_SHORT_STR[idx];
		}
		val += " ";
		return val;
	}
	
	private static String decorateMillisecond(long n, short format){
		return decorate(n, format, 0);
	}
	
	private static String decorateSecond(long n, short format){
		return decorate(n, format, 1);
	}
	
	private static String decorateMinute(long n, short format){
		return decorate(n, format, 2);
	}

	private static String decorateHour(long n, short format){
		return decorate(n, format, 3);
	}
	
	private static String decorateDay(long n, short format){
		return decorate(n, format, 4);
	}
	
	private static String decorateMonth(long n, short format){
		return decorate(n, format, 5);
	}
	
	private static String decorateYear(long n, short format){
		return decorate(n, format, 6);
	}
	
	public static void main(String[] args) {
		ElapsedTimeUtils.start("a");
		System.out.println(ElapsedTimeUtils.showElapsedTime("a",FORMAT_SHORT));
	}
}
