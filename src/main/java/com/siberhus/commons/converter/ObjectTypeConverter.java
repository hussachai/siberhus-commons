
package com.siberhus.commons.converter;

import java.util.Locale;

/**
 * A dummy type converter that targets the Object type by simply returning the input
 * String without any modifications.
 *
 * @author Hussachai
 */
public class ObjectTypeConverter implements ITypeConverter<Object> {
    /** Does Nothing */
    public void setLocale(Locale locale) { }
    
    public Object convert(String input){
    	return convert(input,Object.class);
    }
    
    /** Simple returns the input String un-modified in any way. */
    public Object convert(String input, Class<? extends Object> targetType) {
        return input;
    }
}
