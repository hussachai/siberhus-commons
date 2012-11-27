package com.siberhus.commons.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author hussachai
 *
 */
public class StringMap extends StrKeyMap<String>{
	
	public StringMap(){}
	
	public StringMap(Map<String,String> map){
		super(map);
	}
	
	public StringMap(Properties props){
		super(new HashMap<String, String>());
		for(Map.Entry<Object, Object> entry : props.entrySet()){
			String name = (String)entry.getKey();
			String value = (String)entry.getValue();
			super.put(name, value);
		}
	}
	
	public <D>D[] getSplitValue(Class<D> clazz,String mapKey, char separatorChar) {
		String mapValue = super.get(mapKey);
		String strValues[] = StringUtils.split(mapValue, separatorChar);
		
		return transformAndConvertStringValues(clazz, strValues);
	}
	
	public <D>D[] getSplitValue(Class<D> clazz,String mapKey, String separatorChars) {
		String mapValue = super.get(mapKey);
		String strValues[] = StringUtils.split(mapValue, separatorChars);
		
		return transformAndConvertStringValues(clazz, strValues);
	}
	
	public String[] getSplitValue(String mapKey, char separatorChar) {
		String mapValue = super.get(mapKey);
		String values[] = StringUtils.split(mapValue, separatorChar);
		
		return getTransformedStringValues(values);
	}
	
	public String[] getSplitValue(String mapKey, String separatorChars) {
		String mapValue = super.get(mapKey);
		String values[] = StringUtils.split(mapValue, separatorChars);
		
		return getTransformedStringValues(values);
	}
	
	@Override
	public String put(String mapKey, String mapValue) {
		if(isUnmodifiable()){
			throw new UnsupportedOperationException("Cannot alter unmodifiable map");
		}
		mapKey = getTransformedKey(mapKey);
		mapValue = getTransformedStringValue(mapValue);
		return super.put(mapKey, mapValue);
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		if(isUnmodifiable()){
			throw new UnsupportedOperationException("Cannot alter unmodifiable map");
		}
		for(Map.Entry<? extends String, ? extends String> entry : m.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			key = getTransformedKey(key);
			value = getTransformedStringValue(value);
			super.put(key, value);
		}
	}
	
	public String[] getTransformedStringValues(String values[]){
		if(values==null) return null;
		for(int i=0;i<values.length;i++){
			values[i] = getTransformedStringValue(values[i]);
		}
		return values;
	}
	
	public <D>D[] transformAndConvertStringValues(Class<D> clazz, String[] strValues){
		
		if(strValues==null) return null;
		D values[] = (D[])Array.newInstance(clazz, strValues.length);
		for(int i=0;i<values.length;i++){
			Object value = getTransformedStringValue(strValues[i]);
			values[i] = convertValue(clazz, value);
		}
		return values;
	}
	
}
