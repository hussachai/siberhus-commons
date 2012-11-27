
package com.siberhus.commons.converter;

import java.util.Locale;


public class EnumeratedTypeConverter implements ITypeConverter<Enum> {

	
    public void setLocale(Locale locale) {
        // Do nothing
    }
    
    public Enum convert(String input)throws ConvertException{
    	return convert(input,Enum.class);
    }
    
    @SuppressWarnings("unchecked")
	public Enum convert(String input,
                        Class<? extends Enum> targetType) throws ConvertException {

        try {
        	
            return Enum.valueOf(targetType, input);
            
        }catch (IllegalArgumentException iae) {
            throw new ConvertException("notAnEnumeratedValue");
        }
    }
}
