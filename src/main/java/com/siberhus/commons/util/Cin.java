package com.siberhus.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;

import com.siberhus.commons.converter.TypeConvertUtils;

/**
 * 
 * @author hussachai
 * 
 */
public class Cin {
	
	private static final BufferedReader cin;
	
	static{
		cin = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static String readLine(){
		try {
			return StringUtils.trimToNull(cin.readLine());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String readLine(String defaultValue){
		String value = Cin.readLine();
		if(value!=null){
			return value;
		}
		return defaultValue;
	}
	
	public static <T>T readLine(Class<T> targetClass){
		
		return TypeConvertUtils.convert(Cin.readLine(), targetClass);
	}
	
	public static <T>T readLine(Class<T> targetClass, T defaultValue){
		
		T value = null;
		try{
			value = TypeConvertUtils.convert(Cin.readLine(), targetClass);
		}catch(Exception e){}
		if(value!=null){
			return value;
		}else{
			return defaultValue;
		}
	}
	
	
	public static String inputPrompt(String message){
		System.out.print(message+": ");
		return readLine();
	}
	
	public static String inputPrompt(String message, String defaultValue){
		System.out.print(message+": ["+defaultValue+"] ");
		String value = readLine();
		if(value!=null){
			return value;
		}
		return defaultValue;
	}
	
	public static <T>T inputPrompt(String message,Class<T> targetClass){
		T value = null;
		while(true){
			try{
				System.out.print(message+": ");
				value = Cin.readLine(targetClass);
				return value;
			}catch(Throwable e){
				showError(e);
			}
		}
	}
	
	public static <T>T inputPrompt(String message,Class<T> targetClass, T defaultValue){
		
		T value = null;
		while(true){
			try{
				System.out.print(message+": [default] ");
				String valueStr = readLine();
				if(valueStr==null){
					return defaultValue;
				}
				value = TypeConvertUtils.convert(valueStr, targetClass);
				return value;
			}catch(Throwable e){
				showError(e);
			}
		}
	}
	
	private static void showError(Throwable e){
//		if(e instanceof InvocationTargetException){
//			e = ((InvocationTargetException) e).getTargetException();
//		}
		if(StringUtils.isBlank(e.getMessage())){
			System.out.println("ERROR: "+e.toString());
		}else{
			System.out.println("ERROR: "+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(">>"+Cin.inputPrompt("Enter date",Date.class));
	}
}
