
package com.siberhus.commons.converter;


/**
 * Basic type converter for converting strings to integers.
 *
 * @author Hussachai
 */
public class LongTypeConverter extends NumberTypeConverterSupport implements ITypeConverter<Long> {

	public Long convert(String input)throws ConvertException{
		return convert(input,Long.class); 
	}
	 
    public Long convert(String input,
                        Class<? extends Long> targetType) throws ConvertException {

        Number number = parse(input);
        Long retval = new Long(number.longValue());
        return retval;
    }
}
