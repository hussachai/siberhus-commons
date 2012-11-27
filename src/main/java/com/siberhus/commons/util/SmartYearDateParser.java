package com.siberhus.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartYearDateParser {
	
	private Logger logger = LoggerFactory.getLogger(SmartYearDateParser.class);
	
	private static final int MIN_YEAR = 0;
	
	private static final int MAX_YEAR = 100;
	
	private static final Locale CALDIDATE_LOCALES[] = new Locale[]{Locale.US,new Locale("th","TH")};
	
	private static final Locale REFERENCE_LOCALE = Locale.US;
	
	private int minYear = MIN_YEAR;
	
	private int maxYear = MAX_YEAR;
	
	private Locale candidateLocales[] = CALDIDATE_LOCALES;
	
	private Locale referenceLocale = REFERENCE_LOCALE;
	
	
	public Locale[] getLocales() {
		return candidateLocales;
	}

	public void setLocales(Locale[] locales) {
		this.candidateLocales = locales;
	}
	
	public int getMinYear() {
		return minYear;
	}

	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}

	public int getMaxYear() {
		return maxYear;
	}

	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}

	public Locale[] getCandidateLocales() {
		return candidateLocales;
	}

	public void setCandidateLocales(Locale[] candidateLocales) {
		this.candidateLocales = candidateLocales;
	}

	public Locale getReferenceLocale() {
		return referenceLocale;
	}

	public void setReferenceLocale(Locale referenceLocale) {
		this.referenceLocale = referenceLocale;
	}
	
	public Date parseQuietly(String dateString,String pattern){
		
		if(StringUtils.isBlank(dateString)){
			return null;
		}
		try{
			return parse(dateString,pattern);
		}catch(Exception e){}
		return null;
	}
	
	public Date parse(String dateString,String pattern)throws ParseException{
		Calendar refCurrentCal = Calendar.getInstance(referenceLocale);
		logger.debug("Reference Current Year: {}",refCurrentCal.get(Calendar.YEAR));
		for(Locale locale : candidateLocales){
			logger.debug("Current Locale: {}",locale);
			SimpleDateFormat dateFmt = new SimpleDateFormat(pattern,locale);
			Date date = dateFmt.parse(dateString);
			Calendar dateCal = Calendar.getInstance(referenceLocale);		
			dateCal.setTime(date);
			
			logger.debug("Input Year: {}",dateCal.get(Calendar.YEAR));
			int diffYear = refCurrentCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
			logger.debug("Min Year: {} , Max Year: {}",minYear,maxYear);
			logger.debug("Diff Year: {}",diffYear);
			if(diffYear>=minYear && diffYear <= maxYear){
				return date;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("Default locale: "+Locale.getDefault());
		SmartYearDateParser dateParser = new SmartYearDateParser();
		Date r = dateParser.parseQuietly("29/12/1982", "dd/MM/yyyy");
		System.out.println(r);
		System.out.println(new SimpleDateFormat("dd/MM/yyyy",Locale.US).format(r));
		System.out.println(new SimpleDateFormat("dd/MM/yyyy",new Locale("th","TH")).format(r));
	}
	
}
