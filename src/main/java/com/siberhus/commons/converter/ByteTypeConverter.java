
package com.siberhus.commons.converter;


public class ByteTypeConverter extends NumberTypeConverterSupport implements ITypeConverter<Byte> {
	
	public Byte convert(String input) throws ConvertException {
		return convert(input,Byte.class);
	}
	
    public Byte convert(String input,
                        Class<? extends Byte> targetType) throws ConvertException {

        Number number = parse(input);
        Byte retval = null;
        
        long output = number.longValue();
            
            if (output < Byte.MIN_VALUE || output > Byte.MAX_VALUE) {
                throw new ConvertException("outOfRange minvalue="
                		+Byte.MIN_VALUE+", maxvalue="+Byte.MAX_VALUE);
            }
            else {
                retval = new Byte((byte) output);
            }

        return retval;
    }
}
