package com.siberhus.tools.datagen;

import java.util.Locale;

public abstract class DataGenerator<T> {
	
	
	private Locale locale;
	
	
	public DataGenerator(){
		setLocale(Locale.getDefault());
	}
	
	public DataGenerator(Locale locale){
		setLocale(locale);
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		if(locale==null){
			throw new RuntimeException("Locale can not be null");
		}
		this.locale = locale;
	}
	
	public abstract T getValue(Class<? extends T> targetClass) throws Exception;
	
	
}
