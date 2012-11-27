
package com.siberhus.commons.converter;


/**
 * Basic type converter for converting strings to integers.
 * 
 * @author Hussachai
 */
public class IntegerTypeConverter extends NumberTypeConverterSupport implements
		ITypeConverter<Integer> {
	
	public Integer convert(String input)throws ConvertException{
		return convert(input,Integer.class);
	}
	
	public Integer convert(String input, Class<? extends Integer> targetType)
			throws ConvertException {

		Number number = parse(input);
		Integer retval = null;

		long output = number.longValue();

		if (output < Integer.MIN_VALUE || output > Integer.MAX_VALUE) {
			throw new ConvertException("outOfRange minvalue="
					+ Integer.MIN_VALUE + "maxvalue=" + Integer.MAX_VALUE);
		} else {
			retval = new Integer((int) output);
		}

		return retval;
	}
}
