package com.siberhus.commons.formatter;

import java.text.ChoiceFormat;
import java.text.Format;
import java.util.Locale;

public class ChoiceFormatFactory extends BaseFormatFactory{

	
	public ChoiceFormatFactory() {
		super();
	}

	public ChoiceFormatFactory(String pattern, Locale locale) {
		super(pattern, locale);
	}
	
	@Override
	public Format createFormat() {
		
		return new ChoiceFormat(getPattern());
	}
	
}
