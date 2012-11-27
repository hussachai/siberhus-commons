package com.siberhus.commons.formatter;

import java.text.Format;
import java.text.MessageFormat;
import java.util.Locale;

public class MessageFormatFactory extends BaseFormatFactory{

	
	public MessageFormatFactory() {
		super();
	}

	public MessageFormatFactory(String pattern, Locale locale) {
		super(pattern, locale);
	}

	@Override
	public Format createFormat() {
		
		return new MessageFormat(getPattern(),getLocale());
		
	}

}
