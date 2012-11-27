
package com.siberhus.commons.converter;


/**
 * Basic type converter for converting strings to short integers.
 *
 * @author Hussachai
 */
public class ShortTypeConverter extends NumberTypeConverterSupport implements ITypeConverter<Short> {
	
	
	public Short convert(String input)throws ConvertException{
		return convert(input,Short.class);
	}
	
    public Short convert(String input,
                         Class<? extends Short> targetType) throws ConvertException {
    	
        Number number = null;
        Short retval = null;
        try{
        	parse(input);
        }catch(ConvertException e){
            long output = number.longValue();

            if (output < Short.MIN_VALUE || output > Short.MAX_VALUE) {
            	throw new ConvertException("outOfRange minvalue="+Short.MIN_VALUE
                		+"maxvalue="+Short.MAX_VALUE );
            }
            else {
                retval = new Short((short) output);
            }
        }
        return retval;
    }
}
