package com.siberhus.commons.util.english;

import java.util.regex.Pattern;



public class EnglishWordUtil {

	public static final int FIRST_UPPERCASE_ALPHABET_CODE = 65;
	public static final int LAST_UPPERCASE_ALPHABET_CODE = 90;
	
	public static final int FIRST_LOWERCASE_ALPHABET_CODE = 97;
	public static final int LAST_LOWERCASE_ALPHABET_CODE = 122;
	
	
	public static final String PATTERN_ENGLISH_CHARS_STR
		= "["+(char)FIRST_UPPERCASE_ALPHABET_CODE+"-"+(char)LAST_UPPERCASE_ALPHABET_CODE+
		(char)FIRST_LOWERCASE_ALPHABET_CODE+"-"+(char)LAST_LOWERCASE_ALPHABET_CODE+"]";
	public static final String PATTERN_NON_ENGLISH_CHARS_STR 
		= "[^"+(char)FIRST_UPPERCASE_ALPHABET_CODE+"-"+(char)LAST_UPPERCASE_ALPHABET_CODE+
		(char)FIRST_LOWERCASE_ALPHABET_CODE+"-"+(char)LAST_LOWERCASE_ALPHABET_CODE+"]";
	public static final String PATTERN_ENGLISH_CHARS_AND_SPACE_STR
		= "["+(char)FIRST_UPPERCASE_ALPHABET_CODE+"-"+(char)LAST_UPPERCASE_ALPHABET_CODE+
		(char)FIRST_LOWERCASE_ALPHABET_CODE+"-"+(char)LAST_LOWERCASE_ALPHABET_CODE+"\\s]";
	public static final String PATTERN_NON_ENGLISH_CHARS_AND_SPACE_STR 
		= "[^"+(char)FIRST_UPPERCASE_ALPHABET_CODE+"-"+(char)LAST_UPPERCASE_ALPHABET_CODE+
		(char)FIRST_LOWERCASE_ALPHABET_CODE+"-"+(char)LAST_LOWERCASE_ALPHABET_CODE+"\\s]";
	
	public static final Pattern PATTERN_ENGLISH_CHARS = Pattern.compile(PATTERN_ENGLISH_CHARS_STR);
	public static final Pattern PATTERN_NON_ENGLISH_CHARS = Pattern.compile(PATTERN_NON_ENGLISH_CHARS_STR);
	public static final Pattern PATTERN_ENGLISH_AND_SPACE_CHARS = Pattern.compile(PATTERN_ENGLISH_CHARS_AND_SPACE_STR);
	public static final Pattern PATTERN_NON_ENGLISH_AND_SPACE_CHARS = Pattern.compile(PATTERN_NON_ENGLISH_CHARS_AND_SPACE_STR);
	
	public static boolean isAllEnglishAlphabet(String value){
//		if(value==null)return false;
		String tmp = filterOutNonEnglishChars(value);
		if(value.length()==tmp.length()){
			return true;
		}
		return false;
	}
	
	public static boolean maybeValidEnglishName(String value){
		value = value.replaceAll("[\\s\\.0-9\\-]", "");
		return isAllEnglishAlphabet(value);
	}
	
	public static boolean maybeValidEnglishWord(String value){
		return isAllEnglishAlphabet(value);
	}
	
	public static String filterOutNonEnglishChars(String value){
		return filterOutNonEnglishChars(value, true);
	}
	
	public static String filterOutNonEnglishChars(String value, boolean preserveSpace){
		if(value==null)return null;
		if(preserveSpace){
			return PATTERN_NON_ENGLISH_AND_SPACE_CHARS.matcher(value).replaceAll("");
		}else{
			return PATTERN_NON_ENGLISH_CHARS.matcher(value).replaceAll("");
		}
	}
	
	public static String filterOutEnglishChars(String value){

		return filterOutEnglishChars(value,true);
	}
    
	public static String filterOutEnglishChars(String value, boolean preserveSpace){
		if(value==null)return null;
		if(preserveSpace){
			return PATTERN_ENGLISH_CHARS.matcher(value).replaceAll("");
		}else{
			return PATTERN_ENGLISH_AND_SPACE_CHARS.matcher(value).replaceAll("");
		}
	}
	
	public static void main(String[] args) {
		String a = " \tchalee   \t\n ";
		long t1 = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
//			ExtraStringUtils.trimIncludingNonbreakingSpace(a);
			a.trim();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
	
}
