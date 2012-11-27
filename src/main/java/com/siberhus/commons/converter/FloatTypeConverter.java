
package com.siberhus.commons.converter;


public class FloatTypeConverter extends NumberTypeConverterSupport implements
		ITypeConverter<Float> {
	/** The minimum value that can assigned to a float or Float. */
	public static final float MIN_VALUE = -Float.MAX_VALUE;

	/** The maximum value that can assigned to a float or Float. */
	public static final float MAX_VALUE = Float.MAX_VALUE;
	
	public Float convert(String input)throws ConvertException{
		return convert(input,Float.class);
	}
	
	public Float convert(String input, Class<? extends Float> targetType)
			throws ConvertException {

		Number number = parse(input);
		Float retval = null;

		double output = number.doubleValue();
		if (output > MAX_VALUE || output < MIN_VALUE) {
			throw new ConvertException("outOfRange minvalue=" + MIN_VALUE
					+ ",maxvalue=" + MAX_VALUE);
		} else {
			retval = new Float(number.floatValue());
		}

		return retval;
	}
}
