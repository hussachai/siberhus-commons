package com.siberhus.commons.validator.regex;

import java.util.regex.Pattern;

public final class UrlValidator {

	/**
	 * https?:// matches http:// or https:// (the ? makes the s optional). [-\w.]+ matches 
	 * the hostname. (:\d+)? matches an optional port (as seen in the second and sixth lines 
	 * in the example). (/([\w/_.]*)?)? matches the path, the outer subexpression matches / 
	 * if one exists, and the inner subexpression matches the path itself. As you can see, 
	 * this pattern cannot handle query strings, and it misreads embedded username:password pairs. 
	 * However, for most URLs it will work adequately (matching hostnames, ports, and paths).
	 */
	public static final String URL_PATTERN_STR = "https?://[-\\w.]+(:\\d+)?(/([\\w/_.]*)?)?";
	
	public static final Pattern URL_PATTERN = Pattern.compile(URL_PATTERN_STR);
	
	/**
	 * This pattern builds on the previous example. https?:// is now followed by (\w*:\w*@)?. 
	 * This new pattern checks for embedded user and password (username and password separated by : 
	 * and followed by @) as seen in the fourth line in the example. In addition, (\?\S+)? 
	 * (after the path) matches the query string, ? followed by additional text, and this, too, 
	 * is made optional with ?.
	 */
	public static final String URL2_PATTERN_STR = "https?://(\\w*:\\w*@)?[-\\w.]+(:\\d+)?(/([\\w/_.]*(\\?\\S+)?)?)?";
	
	public static final Pattern URL2_PATTERN = Pattern.compile(URL2_PATTERN_STR);
	
	
	public static boolean isValidURL(String url){
		if(url==null)return false;
		return URL_PATTERN.matcher(url).matches();
	}
	
	public static boolean isValidURL2(String url){
		if(url==null)return false;
		return URL2_PATTERN.matcher(url).matches();
	}
	
}
