package com.siberhus.commons.properties;

import java.util.Map;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;

public class DefaultStrLookup extends StrLookup{
	
	private static final DefaultStrLookup SYSTEM_ENV_LOOKUP;
    /**
     * Lookup that uses System environment.
     */
    private static Map<String, String> sysEnvMap;
    
    private DefaultStrLookup(){}
    
    static {
        try {
        	sysEnvMap = System.getenv();
        } catch (SecurityException ex) {}
        SYSTEM_ENV_LOOKUP = new DefaultStrLookup();
    }
    
	@Override
	public String lookup(String key) {
		if (sysEnvMap == null) {
            return null;
        }
        String value = sysEnvMap.get(key);
        if (value == null) {
            return null;
        }
        return value;
	}
	
	public static StrLookup systemEnvLookup(){
		return SYSTEM_ENV_LOOKUP;
	}
	
	public static void main(String[] args) {
		StrLookup l = DefaultStrLookup.systemPropertiesLookup();
		StrSubstitutor s = new StrSubstitutor(l);
		System.out.println(s.replace("hello ${java.version}"));
	}
}
