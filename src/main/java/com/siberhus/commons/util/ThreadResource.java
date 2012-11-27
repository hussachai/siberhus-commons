package com.siberhus.commons.util;


import java.util.HashMap;
import java.util.Map;

public class ThreadResource {
	
	private static final Map<Object,ThreadLocal<Object>> resourceMap 
		= new HashMap<Object,ThreadLocal<Object>>();
	
	public static <T>T get(Class<T> clazz){
		ThreadLocal<Object> threadLocal = resourceMap.get(clazz);
		if(threadLocal!=null){
			return (T)resourceMap.get(clazz).get();
		}
		return null;
	}
	
	public static void put(Class<?> clazz,Object value){
		ThreadLocal<Object> threadLocal = resourceMap.get(clazz);
		if(threadLocal==null){
			threadLocal = new ThreadLocal<Object>();
			resourceMap.put(clazz, threadLocal);
		}
		threadLocal.set(value);
	}
	
	public static void remove(Class<?> clazz){
		ThreadLocal<Object> threadLocal = resourceMap.get(clazz);
		if(threadLocal!=null){
			threadLocal.remove();
		}
	}
	
}
