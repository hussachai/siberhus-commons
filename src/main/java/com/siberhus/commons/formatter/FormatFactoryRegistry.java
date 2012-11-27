package com.siberhus.commons.formatter;

import java.util.HashMap;
import java.util.Map;

public class FormatFactoryRegistry {
	
	
	@SuppressWarnings("unchecked")
	static final Map<Class,FormatFactory> formats = new HashMap<Class,FormatFactory>();
	
	static{
		formats.put(java.util.Date.class,new SimpleDateFormatFactory());
		formats.put(java.sql.Date.class,new SimpleDateFormatFactory());
		formats.put(java.sql.Timestamp.class,new SimpleDateFormatFactory());
		
	}
	
	@SuppressWarnings("unchecked")
	public static void register(Class clazz, FormatFactory factory){
		formats.put(clazz, factory);
	}
	
	@SuppressWarnings("unchecked")
	public static void unregister(Class clazz){
		formats.remove(clazz);
	}
	
	
	
	
}