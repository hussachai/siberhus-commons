package com.siberhus.commons.util;

import java.util.regex.Pattern;

/*
 * Cut from http://stackoverflow.com/questions/898405/how-to-implement-a-sql-like-like-operator-in-java
 * 
 * Thanks! Alan Moore
 * 
 * The below explanation is belong to Alan Moore not me.
 *  
 * Every SQL reference I can find says the "any single character" wildcard is 
 * the underscore (_), not the question mark (?). That simplifies things a bit, 
 * since the underscore is not a regex metacharacter. However, you still can't 
 * use Pattern.quote() for the reason given by mmyers. I've got another method 
 * here for escaping regexes when I might want to edit them afterward. 
 * With that out of the way, the like() method becomes pretty simple:
 * 
 * If you really want to use ? for the wildcard, your best bet would be to remove it 
 * from the list of metacharacters in the quotemeta() method. Replacing its escaped 
 * form -- replace("\\?", ".") -- wouldn't be safe because there might be backslashes 
 * in the original expression.
 * 
 * And that brings us to the real problems: most SQL flavors seem to support character 
 * classes in the forms [a-z] and [^j-m] or [!j-m], and they all provide a way to escape 
 * wildcard characters. The latter is usually done by means of an ESCAPE keyword, which 
 * lets you define a different escape character every time. As you can imagine, 
 * this complicates things quite a bit. Converting to a regex is probably still the best 
 * option, but parsing the original expression will be much harder--in fact, 
 * the first thing you would have to do is formalize the syntax of the LIKE-like 
 * expressions themselves.
 * 
 * 
 */
public class SqlLikePattern {
	
	public static boolean matches(final String sqlRegex, final String input) {
		String javaRegex = quoteMeta(sqlRegex);
		javaRegex = javaRegex.replace("_", ".").replace("%", ".*?");
		Pattern p = Pattern.compile(javaRegex, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		return p.matcher(input).matches();
	}
	
	public static String quoteMeta(String regex) {
		if (regex == null) {
			throw new IllegalArgumentException("String cannot be null");
		}
		int len = regex.length();
		if (len == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			char c = regex.charAt(i);
			if ("[](){}.*+?$^|#\\".indexOf(c) != -1) {
				sb.append("\\");
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
}
