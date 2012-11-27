
package com.siberhus.commons.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class BigDecimalTypeConverter extends NumberTypeConverterSupport
                                     implements ITypeConverter<BigDecimal> {

	@Override
    protected NumberFormat[] getNumberFormats() {
        NumberFormat[] formats = super.getNumberFormats();
        for (NumberFormat format : formats) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }

        return formats;
    }
	
	public BigDecimal convert(String input)throws ConvertException{
		return convert(input,BigDecimal.class);
	}
	
    public BigDecimal convert(String input,
                              Class<? extends BigDecimal> targetType) throws ConvertException {

        return (BigDecimal) parse(input);
    }
}
