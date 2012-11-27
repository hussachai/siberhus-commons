
package com.siberhus.commons.converter;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Currency;
import java.util.Set;

/**
 * Provides the basic support for converting Strings to non-floating point numbers (i.e. shorts,
 * integers, and longs).
 *
 * @author Hussachai
 */
public class NumberTypeConverterSupport {
    private Locale locale;
    private NumberFormat[] formats;
    private String currencySymbol;
    
    /** Used by Stripes to tell the converter what locale the incoming text is in. */
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.formats = getNumberFormats();

        // Use the appropriate currency symbol if our locale has a country, otherwise try the dollar sign!
        if (locale.getCountry() != null && !"".equals(locale.getCountry()))
            this.currencySymbol = Currency.getInstance(locale).getSymbol(locale);
        else
            this.currencySymbol = "$";
    }
    
    /** Returns the Locale set on the object using setLocale(). */
    public Locale getLocale() {
        return locale;
    }

    @Deprecated()
    protected NumberFormat getNumberFormat() {
        return NumberFormat.getInstance(this.locale);
    }

    protected NumberFormat[] getNumberFormats() {
        return new NumberFormat[] {
                getNumberFormat() // TODO: inline and remove getNumberFormat() in 1.3
        };
    }
    
    protected Number parse(String input) throws ConvertException {
        input = preprocess(input);

        for (NumberFormat format : this.formats) {
            try { return format.parse(input); }
            catch (ParseException pe) { /* Do nothing. */ }
        }
        
        // If we've gotten here we could not parse the number
        throw new ConvertException("InvalidNumber");
    }
    
    protected String preprocess(String input) {
        // Step 1: trim whitespace
        String output = input.trim();

        // Step 2: remove the currency symbol
        // The casts are to make sure we don't call replace(String regex, String replacement)
        output = output.replace((CharSequence) currencySymbol, (CharSequence) "");
        
        // Step 3: replace parentheses with negation
        if (output.startsWith("(") && output.endsWith(")")) {
            output = "-" + output.substring(1, output.length() - 1);
        }

        return output;
    }
}
