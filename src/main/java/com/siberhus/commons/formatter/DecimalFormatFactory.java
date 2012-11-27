package com.siberhus.commons.formatter;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Locale;

public class DecimalFormatFactory extends BaseFormatFactory{

	
	public DecimalFormatFactory() {
		super();
	}

	public DecimalFormatFactory(String pattern, Locale locale) {
		super(pattern, locale);
	}
	
	@Override
	public Format createFormat() {
		return new DecimalFormat(getPattern());
	}
	
}
