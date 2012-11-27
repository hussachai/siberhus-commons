package com.siberhus.commons.util;

import java.util.HashSet;
import java.util.Set;

public class EscapeUtils {
	
	public static String escape(char escapeChar,char[] escapedChars,String value){
		if(value==null)return null;
		StringBuilder buffer = new StringBuilder();
		Set<Character> escapedCharSet = new HashSet<Character>();
		for(Character escapedChar : escapedChars){
			escapedCharSet.add(escapedChar);
		}
		char valueChars[] = value.toCharArray();
		for(int i=0;i<valueChars.length;i++){
			Character valueChar = valueChars[i];
			
			if(escapedCharSet.contains(valueChar)){
				buffer.append(escapeChar);
			}
		}
		return buffer.toString();
	}
}
