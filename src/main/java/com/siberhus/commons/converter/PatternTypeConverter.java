
package com.siberhus.commons.converter;

import java.util.Locale;
import java.util.regex.Pattern;


public class PatternTypeConverter implements ITypeConverter<Object> {
    /** Accepts the Locale provide, but does nothing with it since pattern are Locale-less. */
    public void setLocale(Locale locale) { /** Doesn't matter for email. */}
    
    public Pattern convert(String input)throws ConvertException{
    	return convert(input, Pattern.class);
    }
    
    public Pattern convert(String input,
                          Class<? extends Object> targetType) throws ConvertException {
    	try{
    		return Pattern.compile(input);
    	}catch(Exception e){
    		throw new ConvertException("invalidPattern", e);
    	}
    }
    	
}
