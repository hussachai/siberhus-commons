package com.siberhus.tools.datagen;

import java.util.Date;
import java.util.Locale;

public class DateRandomDataGenerator<T extends Date> extends DataGenerator<T>{
	
	
	public DateRandomDataGenerator() {
		super();
	}

	public DateRandomDataGenerator(Locale locale) {
		super(locale);
	}

	
	@Override
	public T getValue(Class<? extends T> targetClass) throws Exception {
		
		return null;
	}

	
}
