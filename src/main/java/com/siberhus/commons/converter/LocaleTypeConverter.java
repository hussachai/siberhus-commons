
package com.siberhus.commons.converter;

import java.util.Locale;


public class LocaleTypeConverter implements ITypeConverter<Locale> {

	
    public void setLocale(Locale locale) {
        // Do nothing
    }
    
    public Locale convert(String input)throws ConvertException{
    	return convert(input, Locale.class);
    }
    
    @SuppressWarnings("unchecked")
	public Locale convert(String input,
                        Class targetType) throws ConvertException {
    	if(input==null){
    		return Locale.getDefault();
    	}
    	Locale locale = null;
    	int sepPos = input.indexOf("_");
    	if(sepPos!=-1){
    		String language = input.substring(0, sepPos);
    		String country = input.substring(sepPos+1);
    		locale = new Locale(language,country);
    	}else{
    		locale = new Locale(input);
    	}
    	
    	return locale;
    }
    
    public static void main(String[] args) {
    	Locale l = new LocaleTypeConverter().convert("th_TH");
		System.out.println(l.getCountry());
		System.out.println(l.getLanguage());
	}
}
