
package com.siberhus.commons.converter;

import java.util.Locale;

/**
 * Interface for all type converters in the validation system that provide facilities for
 * converting from String to a specific object type.
 *
 * @author Hussachai
 */
public interface ITypeConverter<T> {

    void setLocale(Locale locale);
    
    T convert(String input, Class<? extends T> targetType) throws ConvertException;
    
    Object convert(String input) throws ConvertException;
    
}
