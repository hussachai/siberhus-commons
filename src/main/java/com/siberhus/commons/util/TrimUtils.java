package com.siberhus.commons.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author hussachai
 *
 */
public class TrimUtils {
	
	public static String NUMBER_CHARS = "0123456789";
	public static String WHITESPACE_CHARS = "\\t\\n\\x0B\\f\\r";
	
	private static final Pattern PATTERN_LEFT_ALL_SPACES = Pattern.compile("^[ \\t\\n\\x0B\\f\\r\\xA0]+");
	private static final Pattern PATTERN_RIGHT_ALL_SPACES = Pattern.compile("[ \\t\\n\\x0B\\f\\r\\xA0]+$");
	
	public static String trim(String data){
		return StringUtils.trim(data);
	}
	
	public static String trimToEmpty(String data){
		return StringUtils.trimToEmpty(data);
	}
	
	public static String trimToNull(String data){
		return StringUtils.trimToNull(data);
	}
	
	public static String trimAllSpaces(String data){
		if(data==null) return null;
		data = PATTERN_LEFT_ALL_SPACES.matcher(data).replaceFirst("");
		data = PATTERN_RIGHT_ALL_SPACES.matcher(data).replaceFirst("");
		return data;
	}
	
	public static String trimAllSpacesToEmpty(String data){
		data = trim(data);
		if(data!=null){
			return data;
		}
		return "";
	}
	
	public static String trimAllSpacesToNull(String data){
		data = trim(data);
		if(data!=null){
			if(data.length()!=0){
				return data;
			}
		}
		return null;
	}
	
	public static String[] trim(String array[]){
        if(ArrayUtils.isEmpty(array)){
            return new String[0];
        }
        String result[] = new String[array.length];
        for(int i = 0; i < array.length; i++){
            String element = array[i];
            result[i] = trim(element);
        }
        return result;
    }
	
	public static String[] trimToEmpty(String array[]){
        if(ArrayUtils.isEmpty(array)){
            return new String[0];
        }
        String result[] = new String[array.length];
        for(int i = 0; i < array.length; i++){
            String element = array[i];
            result[i] = trimToEmpty(element);
        }
        return result;
    }
	
	public static String[] trimToNull(String array[]){
        if(ArrayUtils.isEmpty(array)){
            return new String[0];
        }
        String result[] = new String[array.length];
        for(int i = 0; i < array.length; i++){
            String element = array[i];
            result[i] = trimToNull(element);
        }
        return result;
    }
	
	public static String[] trimAllSpaces(String array[]){
        if(ArrayUtils.isEmpty(array)){
            return new String[0];
        }
        String result[] = new String[array.length];
        for(int i = 0; i < array.length; i++){
            String element = array[i];
            result[i] = trimAllSpaces(element);
        }
        return result;
    }
	
	public static String[] trimAllSpaceToEmpty(String array[]){
        if(ArrayUtils.isEmpty(array)){
            return new String[0];
        }
        String result[] = new String[array.length];
        for(int i = 0; i < array.length; i++){
            String element = array[i];
            result[i] = trimAllSpacesToEmpty(element);
        }
        return result;
    }
	
	public static String[] trimAllSpacesToNull(String array[]){
        if(ArrayUtils.isEmpty(array)){
            return new String[0];
        }
        String result[] = new String[array.length];
        for(int i = 0; i < array.length; i++){
            String element = array[i];
            result[i] = trim(element);
        }
        return result;
    }
	
	public static String removeLeft(String chars, boolean include, String value) {
		if (value == null){
			return null;
		}
		char valueChars[] = value.toCharArray();
		int firstCharIdx = 0;
		for (int i = 0; i < valueChars.length; i++) {
			if (include ^ chars.contains(String.valueOf(valueChars[i]))) {
				firstCharIdx = i;
				break;
			}
		}
		value = value.substring(firstCharIdx);
		return value;
	}
	
	public static String removeRight(String chars, boolean include, String value) {
		if (value == null){
			return null;
		}
		char valueChars[] = value.toCharArray();
		int lastCharIdx = valueChars.length - 1;
		for (int i = valueChars.length - 1; i > 0; i--) {
			if (include ^ chars.contains(String.valueOf(valueChars[i]))) {
				lastCharIdx = i + 1;
				break;
			}
		}
		value = value.substring(0, lastCharIdx);
		return value;
	}
	
	public static String removeBoth(String chars, boolean include, String value){
		value = removeLeft(chars, include, value);
		value = removeRight(chars, include, value);
		return value;
	}
	
	
}
