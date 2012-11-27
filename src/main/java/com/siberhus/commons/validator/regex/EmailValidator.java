package com.siberhus.commons.validator.regex;

import java.util.regex.Pattern;

public final class EmailValidator {

	/**
	 * (\w+\.)*\w+ matches the name portion of an email address (everything before the @). 
	 * (\w+\.)* matches zero or more instances of text followed by ., and \w+ matches 
	 * required text (this combination matches both ben and ben.forta, for example). 
	 * @ matches @. (\w+\.)+ then matches at least one instance of text followed by ., 
	 * and [A-Za-z]+ matches the top-level domain (com, edu, us, or uk, and so on).
	 * 
	 * The rules governing valid email address formats are extremely complex. This pattern 
	 * will not validate every possible email address. For example, it will allow 
	 * ben..forta@forta.com (which is invalid) and will not allow IP addresses as 
	 * the hostname (which are allowed). Still, it will suffice for most email validation, 
	 * and so it may work for you.
	 */
//	private static final String EMAIL_PATTERN_STR = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+";
//	private static final String EMAIL_PATTERN_STR = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	
	/**
	 * regex to validate email address noteworthy: 
	 * (1) It allows usernames with 1 or 2 alphanum characters, or 3+ chars 
	 * can have -._ in the middle. username may NOT start/end with -._ or any 
	 * other non alphanumeric character. 
	 * (2) It allows heirarchical domain names (e.g. me@really.big.com). 
	 * Similar -._ placement rules there. 
	 * (3) It allows 2-9 character alphabetic-only TLDs (that oughta cover 
	 * museum and adnauseum :&gt;). 
	 * (4) No IP email addresses though -- I wouldn't Want to accept that 
	 * kind of address.
	 * Matches: e@eee.com | eee@e-e.com | eee@ee.eee.museum
	 * Non-Matches: .@eee.com | eee@e-.com | eee@ee.eee.eeeeeeeeee
	 */
	public static final String EMAIL_PATTERN_STR = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";
	
	public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STR);
	
	
	/**
	 * Validates 1 or more email addresses. Email addresses can be delimited with 
	 * either comma or semicolon. White space is allowed after delimiter, 
	 * but not necessary. I needed this to allow my users to specify multiple email 
	 * addresses if they choose to do so.
	 * Matches: lewis@moten.com | lewis@moten.com, me@lewismoten.com | lewis@moten.com;me@lewismoten.com
	 * Non-Matches: lewis@@moten.com
	 */
	public static final String MULTIPLE_EMAIL_PATTERN_STR = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*([,;]\\s*\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*";
	
	public static final Pattern MULTIPLE_EMAIL_PATTERN = Pattern.compile(MULTIPLE_EMAIL_PATTERN_STR);
	
	public static boolean isValidEmail(String email){
		if(email==null) return false;
		return EMAIL_PATTERN.matcher(email).matches();
	}
	
	public static boolean isValidMultipleEmail(String emails){
		if(emails==null) return false;
		return MULTIPLE_EMAIL_PATTERN.matcher(emails).matches();
	}
	
}
