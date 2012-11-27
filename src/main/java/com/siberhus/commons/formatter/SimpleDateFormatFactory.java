package com.siberhus.commons.formatter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class SimpleDateFormatFactory extends BaseFormatFactory{
	
	

	public SimpleDateFormatFactory() {
		super();
	}

	public SimpleDateFormatFactory(String pattern, Locale locale) {
		super(pattern, locale);
	}

	@Override
	public Format createFormat() {
		return new SimpleDateFormat(getPattern(),getLocale());
	}
	
}
