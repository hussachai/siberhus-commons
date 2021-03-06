
package com.siberhus.commons.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class CalendarTypeConverter implements ITypeConverter<Calendar> {
	
    private Locale locale;
    private DateFormat[] formats;
    
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.formats = getDateFormats();
    }

    /** The default set of date patterns used to parse dates with SimpleDateFormat. */
    private String[] formatStrings = new String[] {
        "dd/MM/yyyy HH:mm:ss",
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
    
    public Calendar convert(String input) throws ConvertException{
    	return convert(input,Calendar.class);
    }
    
    public Calendar convert(String input,
                        Class<? extends Calendar> targetType) throws ConvertException {
    	
    	Calendar calendar = Calendar.getInstance();
    	Date date = null;
        for (DateFormat format : this.formats) {
            try {
                date = format.parse(input);
                break;
            }
            catch (ParseException pe) { /* Do nothing, we'll get lots of these. */ }
        }
        
        if (date != null) {
        	calendar.setTime(date);
            return calendar;
        }else {
            throw new ConvertException("invalidDate");
        }
    }
    
}
