
package com.siberhus.commons.converter;


/**
 * Basic TypeConverter that will convert from Strings to Numbers of type Double.
 *
 * @author Hussachai
 */
public class DoubleTypeConverter extends NumberTypeConverterSupport implements ITypeConverter<Double> {

	public Double convert(String input) throws ConvertException{
		return convert(input,Double.class);
	}
	
    public Double convert(String input,
                          Class<? extends Double> targetType) throws ConvertException {

        Number number = parse(input);
        Double retval = new Double(number.doubleValue());
        
        return retval;
    }
}
