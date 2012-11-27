
package com.siberhus.commons.converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

public class BooleanTypeConverter implements ITypeConverter<Boolean> {
    
	private static final Collection<String> truths = new HashSet<String>();

    static {
        truths.add("true");
        truths.add("t");
        truths.add("yes");
        truths.add("y");
        truths.add("on");
    }


    public void setLocale(Locale locale) {
        // Do nothing
    }

    public Boolean convert(String input) {
    	return convert(input,Boolean.class);
    }
    
    public Boolean convert(String input,
                           Class<? extends Boolean> targetType) {

        boolean retval = false;

        for (String truth : truths) {
            retval = retval || truth.equalsIgnoreCase(input);
        }

        if (retval == false) {
            try {
                long number = Long.parseLong(input);
                retval =  (number > 0);
            }
            catch (NumberFormatException nfe) {/* Ingore the exception */ }
        }

        return retval;
    }
}
