
package com.siberhus.commons.converter;

import java.util.Locale;

public class CharacterTypeConverter implements ITypeConverter<Character> {
    /** Does nothing. */
    public void setLocale(Locale locale) { }

    
    public Character convert(String input){
    	return convert(input,Character.class);
    }
    
    public Character convert(String input, Class<? extends Character> targetType) {
        if (input != null && !"".equals(input)) {
            return input.charAt(0);
        }
        else {
            return '\0';
        }
    }
}
