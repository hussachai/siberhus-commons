package com.siberhus.datacleaner;

import java.util.regex.Pattern;

public class SpaceCharCleaner {
	
	private static final Pattern HORIZANTAL_SPACE_PATTERN = Pattern.compile("[ \\t\\xA0]");
	private static final Pattern HORIZANTAL_SPACES_PATTERN = Pattern.compile("[ \\t\\xA0]+");
	
	private static final Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\s");
	private static final Pattern WHITE_SPACES_PATTERN = Pattern.compile("\\s+");
	
	private static final Pattern ALL_SPACE_PATTERN = Pattern.compile("[ \\t\\n\\x0B\\f\\r\\xA0]");
	private static final Pattern ALL_SPACES_PATTERN = Pattern.compile("[ \\t\\n\\x0B\\f\\r\\xA0]+");
	
	
	public static String replaceAllWhitespaces(String data, String replace){
		if(data==null) return null;
		return WHITE_SPACE_PATTERN.matcher(data).replaceAll(replace);
	}
	
	public static String replaceAllMergedWhitespaces(String data, String replace){
		if(data==null) return null;
		return WHITE_SPACES_PATTERN.matcher(data).replaceAll(replace);
	}
	
	public static String replaceAllHSpaces(String data, String replace){
		if(data==null) return null;
		return HORIZANTAL_SPACE_PATTERN.matcher(data).replaceAll(replace);
	}
	
	public static String replaceAllMergedHSpaces(String data, String replace){
		if(data==null) return null;
		return HORIZANTAL_SPACES_PATTERN.matcher(data).replaceAll(replace);
	}
	
	public static String replaceAllSpaces(String data, String replace){
		if(data==null) return null;
		return ALL_SPACE_PATTERN.matcher(data).replaceAll(replace);
	}
	
	public static String replaceAllMergedSpaces(String data, String replace){
		if(data==null) return null;
		return ALL_SPACES_PATTERN.matcher(data).replaceAll(replace);
	}
	
}
