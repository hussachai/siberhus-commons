

package com.siberhus.commons.converter;


import java.util.Locale;

/**
 *
 * @author Hussachai
 */
public interface ITypeConverterFactory {
	
	
    ITypeConverter getTypeConverter(Class forType, Locale locale) throws Exception;

    ITypeConverter getInstance(Class<? extends ITypeConverter> clazz, Locale locale) throws Exception;
    
    void register(Class type, Class<? extends ITypeConverter> converter);
    
    void unregister(Class type);
    
}
	
