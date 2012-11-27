package com.siberhus.commons.formatter;

import java.util.Locale;

public abstract class BaseFormatFactory extends FormatFactory{

	private String pattern;
	private Locale locale;
	
	public BaseFormatFactory(){
		
	}
	
	public BaseFormatFactory(String pattern,Locale locale){
		this.pattern = pattern;
		this.locale = locale;
	}
	
	@Override
	public void applyPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		if(locale==null){
			return Locale.getDefault();
		}
		return locale;
	}
	
	public String getPattern() {
		return pattern;
	}

	

	
}
