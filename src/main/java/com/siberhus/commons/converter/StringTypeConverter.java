
package com.siberhus.commons.converter;

import java.util.Locale;

/**
 * A dummy type converter that targets the String type by simply returning the input
 * String without any modifications.
 *
 * @author Hussachai
 */
public class StringTypeConverter implements ITypeConverter<String> {
    /** Does Nothing */
    public void setLocale(Locale locale) { }

    public String convert(String input) {
        return convert(input,String.class);
    }
    
    /** Simple returns the input String un-modified in any way. */
    public String convert(String input, Class<? extends String> targetType) {
        return input;
    }
}
