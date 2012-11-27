package com.siberhus.commons.formatter;

import java.text.Format;
import java.util.Locale;

public abstract class FormatFactory {
	
	public abstract void applyPattern(String pattern);
	
	public abstract void setLocale(Locale locale);
	
	public abstract Format createFormat();
	
	@SuppressWarnings("unchecked")
	public static Format createFormat(Class targetClass, String pattern, Locale locale){
		
		FormatFactory formatFactory = null;
		while( (formatFactory = FormatFactoryRegistry
				.formats.get(targetClass))==null ){
			targetClass = targetClass.getSuperclass();
			if(targetClass==null){
				break;
			}
		}
		if(formatFactory!=null){
			if(pattern!=null){
				formatFactory.applyPattern(pattern);
			}
			if(locale!=null){
				formatFactory.setLocale(locale);
			}
			return formatFactory.createFormat();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Format createFormat(Class targetClass, String pattern){
		return createFormat(targetClass, pattern, null);
	}
	
	@SuppressWarnings("unchecked")
	public static Format createFormat(Class targetClass){
		return createFormat(targetClass, null, null);
	}
	
}
