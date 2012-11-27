
package com.siberhus.commons.converter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;



public class SqlDateTypeConverter implements ITypeConverter<Date> {
	
    private Locale locale;
    private DateFormat[] formats;
    
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.formats = getDateFormats();
    }

    /** The default set of date patterns used to parse dates with SimpleDateFormat. */
    private String[] formatStrings = new String[] {
        "dd/MM/yyyy"
    };
    
    public void setFormatStrings(String[] formatStrings){
    	this.formatStrings = formatStrings;
    }
    
    
    public String[] getFormatStrings() {
        return formatStrings;
    }

    
    protected DateFormat[] getDateFormats() {
        String[] formatStrings = getFormatStrings();
        SimpleDateFormat[] dateFormats = new SimpleDateFormat[formatStrings.length];

        // First create the ones from the format Strings
        for (int i=0; i<formatStrings.length; ++i) {
            dateFormats[i] = new SimpleDateFormat(formatStrings[i], locale);
            dateFormats[i].setLenient(false);
        }
        
        return dateFormats;
    }
    
    public Date convert(String input) throws ConvertException{
    	return convert(input,Date.class);
    }
    
    public Date convert(String input,
                        Class<? extends Date> targetType) throws ConvertException {
    	
        java.util.Date date = null;
        
        for (DateFormat format : this.formats) {
            try {
                date = format.parse(input);
                
                break;
            }
            catch (ParseException pe) { /* Do nothing, we'll get lots of these. */ }
        }
        
        if (date != null) {
            return new Date(date.getTime());
        }
        else {
            throw new ConvertException("invalidDate");
        }
    }
    
}
