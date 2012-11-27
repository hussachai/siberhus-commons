package com.siberhus.commons.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.DecimalFormat;


public class BigIntegerTypeConverter extends NumberTypeConverterSupport
		implements ITypeConverter<BigInteger> {


	protected NumberFormat[] getNumberFormats() {
		NumberFormat[] formats = super.getNumberFormats();
		for (NumberFormat format : formats) {
			((DecimalFormat) format).setParseBigDecimal(true);
		}

		return formats;
	}
	
	public BigInteger convert(String input)throws ConvertException {
		return convert(input,BigInteger.class);
	}
	
	public BigInteger convert(String input,
			Class<? extends BigInteger> targetType) throws ConvertException {
		
		BigDecimal decimal = (BigDecimal) parse(input);
		return decimal.toBigInteger();
	}
}
