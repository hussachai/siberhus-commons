package com.siberhus.commons.converter;

import java.text.NumberFormat;
import java.math.BigDecimal;

/**
 * 
 * @author Hussachai
 */
public class PercentageTypeConverter extends NumberTypeConverterSupport
		implements ITypeConverter<Number> {

	/** Returns a single percentage instance of NumberFormat. */
	@Override
	protected NumberFormat[] getNumberFormats() {
		return new NumberFormat[] { NumberFormat
				.getPercentInstance(getLocale()) };
	}
	
	public Number convert(String input) throws ConvertException {
		return convert(input,Number.class);
	}
	
	public Number convert(String input, Class<? extends Number> targetType)
			throws ConvertException {

		Number number = null;

		try {
			/*
			 * Since NumberFormat's percentage instance is insistent that the %
			 * sign must be present (how dumb), if there are errors, let's take
			 * a second shot at parsing
			 */
			number = parse(input);
		} catch (ConvertException e) {
			number = parse(input + "%");
			if (targetType.equals(Float.class) || targetType.equals(Float.TYPE)) {
				number = new Float(number.floatValue());
			} else if (targetType.equals(Double.class)
					|| targetType.equals(Double.TYPE)) {
				number = new Double(number.doubleValue());
			} else if (targetType.equals(BigDecimal.class)) {
				number = new BigDecimal(number.doubleValue());
			} else {
				throw new IllegalArgumentException(
						"PercentageTypeConverter only converts to float, double and BigDecimal. "
								+ "This is because the input number is always converted to a decimal value. "
								+ "E.g. 99% -> 0.99. Type specified was: "
								+ targetType);
			}
		}

		return number;
	}

}
