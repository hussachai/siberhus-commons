package com.siberhus.commons.converter;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;


public class TypeConvertUtils {
	
	private static final DefaultTypeConverterFactory typeConverterFactory = new DefaultTypeConverterFactory();
	
	private static final Map<String, Class<?>> converterAliases = new Hashtable<String, Class<?>>();
	
	private static Locale defaultLocale = Locale.getDefault();
	
	public static void setDefaultLocale(Locale locale){
		TypeConvertUtils.defaultLocale = locale;
	}
	
	public static Collection<Class> getRegisteredTypes(){
		return typeConverterFactory.getRegisteredTypes();
	}
	
	public static Collection<String> getTypeAliasNames(){
		return converterAliases.keySet();
	}
	
	public static void registerTypeAlias(String typeAlias,String targetClass) throws ClassNotFoundException{
		registerTypeAlias(typeAlias, Class.forName(targetClass));
	}
	
	@SuppressWarnings("unchecked")
	public static void registerTypeAlias(String typeAlias,Class targetClass){
		converterAliases.put(typeAlias, targetClass);
	}
	
	@SuppressWarnings("unchecked")
	public static void registerType(Class targetClass, Class converter){
		typeConverterFactory.register(targetClass, converter);
	}
	
	public static void registerType(String targetClass, String converter) throws ClassNotFoundException{
		registerType(Class.forName(targetClass), Class.forName(converter));
	}
	
	@SuppressWarnings("unchecked")
	public static ITypeConverter lookupByTypeAlias(String typeAlias,Locale locale) throws Exception{
		Class targetClass = converterAliases.get(typeAlias);
		return typeConverterFactory.getTypeConverter(targetClass, locale);
	}
	
	@SuppressWarnings("unchecked")
	public static ITypeConverter lookupByTypeAlias(String typeAlias) throws Exception{
		return lookupByTypeAlias(typeAlias,defaultLocale);
	}
	
	@SuppressWarnings("unchecked")
	public static ITypeConverter lookupByType(String targetClass,Locale locale) throws Exception{
		return lookupByType(Class.forName(targetClass), locale);
	}
	
	@SuppressWarnings("unchecked")
	public static ITypeConverter lookupByType(String targetClass) throws Exception{
		return lookupByType(targetClass, defaultLocale);
	}
	
	@SuppressWarnings("unchecked")
	public static ITypeConverter lookupByType(Class targetClass,Locale locale) throws Exception{
		return typeConverterFactory.getTypeConverter(targetClass, locale);
	}
	
	@SuppressWarnings("unchecked")
	public static ITypeConverter lookupByType(Class targetClass)throws Exception{
		return lookupByType(targetClass,defaultLocale);
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T convert(String value,Locale locale,Class<T> targetClass){
		ITypeConverter converter = null;
		try {
			converter = lookupByType(targetClass,locale);
			if(converter==null){
				throw new IllegalArgumentException("Target class: "+targetClass+" is not registered");
			}
		} catch(IllegalArgumentException e){
			throw e;
		} catch (Exception e) {
			throw new ConvertException(e);
		}
		return (T)converter.convert(value,targetClass);
	}
	
	public static <T>T convert(String value,Locale locale,Class<T> targetClass,T defaultValue){
		try{
			return (T)convert(value,locale,targetClass);
		}catch(IllegalArgumentException e){
			throw e;
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static <T>T convert(String value,Class<T> targetClass){
		return (T)convert(value,defaultLocale,targetClass);
	}
	
	public static <T>T convert(String value,Class<T> targetClass,T defaultValue){
		try{
			return (T)convert(value,targetClass);
		}catch(IllegalArgumentException e){
			throw e;
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	//
	@SuppressWarnings("unchecked")
	public static <T>T[] convert(String[] values,Locale locale,Class<T> targetClass){
		if(values==null) return null;
		T[] results = (T[])Array.newInstance(targetClass, values.length);
		for(int i=0;i<values.length;i++){
			results[i]=convert(values[i],locale,targetClass);
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T[] convert(String[] values,Class<T> targetClass){
		
		return convert(values,defaultLocale,targetClass);
	}
	
	public static void main(String[] args)throws Exception {
		String values[] = convert(new String[]{"1"},String.class);
		System.out.println(values instanceof String[]);
		
	}
	
}
