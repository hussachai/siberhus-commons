package com.siberhus.commons.validator.regex;

import java.util.regex.Pattern;

public final class Inet4AddressValidator {

	/**
	 * This pattern uses a series of nested subexpressions. 
	 * The first is (((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.), a set of four nested 
	 * subexpressions. (\d{1,2}) matches any one- or two-digit number, or numbers 0 through 99. 
	 * (1\d{2}) matches any three-digit number starting with 1 (1 followed by any 2 digits),
	 * or numbers 100 through 199. (2[0-4]\d) matches numbers 200 through 249. (25[0-5]) matches 
	 * numbers 250 through 255. Each of these subexpressions is enclosed within another 
	 * subexpression with an | between each (so that one of the four subexpressions has to match, 
	 * but not all). After the range of numbers comes \. to match ., and then the entire series 
	 * is enclosed into yet another subexpression and repeated three times using {3}. 
	 * Finally, the range of numbers is repeated (this time without the trailing \.) to match 
	 * the final IP address number. The pattern thus validates the format of the string to be 
	 * matched (that it is four sets of numbers separated by .) and validates that each of 
	 * the numbers has a value between 0 and 255.
	 */
	public static final String IPV4_PATTERN_STR = "(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))";
	
	public static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_PATTERN_STR);
	
	
	public static boolean isValidIPv4(String address){
		if(address==null)return false;
		return IPV4_PATTERN.matcher(address).matches();
	}
	
}
