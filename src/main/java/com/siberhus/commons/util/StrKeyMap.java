package com.siberhus.commons.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.siberhus.commons.converter.TypeConvertUtils;

/**
 * 
 * @author hussachai
 *
 * @param <V>
 */
public class StrKeyMap<V> implements Map<String, V> {
	
	public static enum TRIM { DEFAULT, TO_EMPTY, TO_NULL }
	public static enum CASE { LOWER, UPPER }
	public static enum CONVERTER_IMPL {SIBERHUS_CONVERTER, APACHE_COMMONS}
	
	private Map<String,V> map;
	
	private TRIM keyTrimMode = null;
	private CASE keyCaseMode = null;
	
	private TRIM valueTrimMode = null;
	private CASE valueCaseMode = null;
	
	private CONVERTER_IMPL converterImpl = CONVERTER_IMPL.SIBERHUS_CONVERTER;
	
	private boolean unmodifiable = false;
	
	public StrKeyMap(){
		map = new LinkedHashMap<String, V>();
	}
	
	public void setUnmodifiable(){
		this.unmodifiable = true;
	}
	
	public boolean isUnmodifiable(){
		return unmodifiable;
	}
	
	@SuppressWarnings("unchecked")
	public StrKeyMap(Map<String, V> map){
		this.map  = map;
		for(Map.Entry<String, V> entry : this.map.entrySet()){
			String key = getTransformedKey(entry.getKey());
			V value = (V)getTransformedValue(entry.getValue());
			this.map.put(key, value);
		}
	}
	
	public TRIM getKeyTrimMode() {
		return keyTrimMode;
	}

	public void setKeyTrimMode(TRIM keyTrimMode) {
		this.keyTrimMode = keyTrimMode;
	}
	
	public CASE getKeyCaseMode() {
		return keyCaseMode;
	}
	
	public void setKeyCaseMode(CASE keyCaseMode) {
		this.keyCaseMode = keyCaseMode;
	}
	
	public TRIM getValueTrimMode() {
		return valueTrimMode;
	}
	
	public void setValueTrimMode(TRIM valueTrimMode) {
		this.valueTrimMode = valueTrimMode;
	}
	
	public CASE getValueCaseMode() {
		return valueCaseMode;
	}

	public void setValueCaseMode(CASE valueCaseMode) {
		this.valueCaseMode = valueCaseMode;
	}
	
	public CONVERTER_IMPL getConverterImpl() {
		return converterImpl;
	}
	
	public void setConverterImpl(CONVERTER_IMPL converterImpl) {
		this.converterImpl = converterImpl;
	}
	
	
	public <D>D[] gets(Class<D> clazz, Object mapKey) {
		
		Object mapValue = get(mapKey);
		D[] values = null;
		try{
			values = convertValues(clazz, mapValue);
		}catch(Exception e){
			throw new MapValueConvertException(e)
				.setKey(mapKey).setValue(mapValue);
		}
		return values;
	}
	
	public <D>D[] gets(Class<D> clazz, Object mapKey, D[] defaultValues) {
		
		try{
			D values[] = gets(clazz,mapKey);
			if(values!=null){
				return values;
			}
		}catch(Exception e){}
		
		return defaultValues;
	}
	
	public <D>D get(Class<D> clazz, Object mapKey) {
		
		Object mapValue = get(mapKey);
		D value = null;
		try{
			value = convertValue(clazz, mapValue);
		}catch(Exception e){
			throw new MapValueConvertException(e)
				.setKey(mapKey).setValue(mapValue);
		}
		return value;
	}
	
	public <D>D get(Class<D> clazz, Object mapKey, D defaultValue){
		try{
			D value = get(clazz, mapKey);
			if(value!=null){
				return value;
			}
		}catch(Exception e){}
		
		return defaultValue;
	}
	
	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object mapKey) {
		mapKey = getTransformedKey(mapKey);
		return map.containsKey(mapKey);
	}
	
	@Override
	public boolean containsValue(Object mapValue) {
		mapValue = getTransformedValue((V)mapValue);
		return map.containsValue(mapValue);
	}
	
	@Override
	public Set<Map.Entry<String, V>> entrySet() {
		return map.entrySet();
	}
	
	@Override
	public V get(Object mapKey) {
		mapKey = getTransformedKey(mapKey);
		return map.get(mapKey);
	}
	
	
	public String getString(String mapKey){
		Object valueObj = get(mapKey);
		if(valueObj!=null){
			return valueObj.toString();
		}
		return null;
	}
	
	public String getString(String mapKey, String defaultValue){
		String value = getString(mapKey);
		if(value!=null){
			return value; 
		}
		return defaultValue;
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	@Override
	public Set<String> keySet() {
		return map.keySet();
	}
	
	@Override
	public V put(String mapKey, V mapValue) {
		if(isUnmodifiable()){
			throw new UnsupportedOperationException("Cannot alter unmodifiable map");
		}
		mapKey = getTransformedKey(mapKey);
		mapValue = (V)getTransformedValue(mapValue);
		return map.put(mapKey, mapValue);
	}
	
	@Override
	public void putAll(Map<? extends String,? extends V> m) {
		if(isUnmodifiable()){
			throw new UnsupportedOperationException("Cannot alter unmodifiable map");
		}
		for(Map.Entry<? extends String, ? extends V> entry : m.entrySet()){
			String key = entry.getKey();
			V value = entry.getValue();
			key = getTransformedKey(key);
			value = (V)getTransformedValue(value);
			this.map.put(key, value);
		}
	}

	@Override
	public V remove(Object mapKey) {
		if(isUnmodifiable()){
			throw new UnsupportedOperationException("Cannot alter unmodifiable map");
		}
		return map.remove(mapKey);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}
	
	@Override
	public String toString(){
		return map.toString();
	}
	
	public String getTransformedKey(Object keyObj){
		
		String key = (String)keyObj;
		if(getKeyTrimMode()!=null){
			if(getKeyTrimMode()==TRIM.DEFAULT){
				key = StringUtils.trim(key);
			}else if(getKeyTrimMode()==TRIM.TO_NULL){
				key = StringUtils.trimToNull(key);
			}else if(getKeyTrimMode()==TRIM.TO_EMPTY){
				key = StringUtils.trimToEmpty(key);
			}
		}
		if(getKeyCaseMode()!=null){
			if(getKeyCaseMode()==CASE.LOWER){
				key = StringUtils.lowerCase(key);
			}else if(getKeyCaseMode()==CASE.UPPER){
				key = StringUtils.upperCase(key);
			}
		}
		return key;
	}
	
	public Object getTransformedValue(Object valueObj){
		
		if( valueObj==null || valueObj instanceof String){
			String value = (String)valueObj;
			return getTransformedStringValue(value);
		}else{
			return valueObj;
		}
		
	}
	
	public String getTransformedStringValue(String value){
		
		if(getValueTrimMode()!=null){
			if(getValueTrimMode()==TRIM.DEFAULT){
				value = StringUtils.trim(value);
			}else if(getValueTrimMode()==TRIM.TO_NULL){
				value = StringUtils.trimToNull(value);
			}else if(getValueTrimMode()==TRIM.TO_EMPTY){
				value = StringUtils.trimToEmpty(value);
			}
		}
		if(getValueCaseMode()!=null){
			if(getValueCaseMode()==CASE.LOWER){
				value = StringUtils.lowerCase(value);
			}else if(getValueCaseMode()==CASE.UPPER){
				value = StringUtils.upperCase(value);
			}
		}
		return value;
	}
	
	public <D>D convertValue(Class<D> clazz, Object mapValue){
		
		if(mapValue==null) return null;
		
		Class valueClass = mapValue.getClass();
		
		if(valueClass==clazz){
			return (D)mapValue;
		}
		else if(clazz.isAssignableFrom(valueClass)){
			return (D)mapValue;
		}
		
		if(valueClass.isArray() || mapValue instanceof Collection 
				|| mapValue instanceof Map){
			throw new UnsupportedOperationException("value cannot be array, collection or map");
		}
		
		String valueStr = ObjectUtils.toString(mapValue);
		
		//if value is not array or collection
		if(getConverterImpl()==CONVERTER_IMPL.SIBERHUS_CONVERTER){
			return TypeConvertUtils.convert(valueStr, clazz);
		}else if(getConverterImpl()==CONVERTER_IMPL.APACHE_COMMONS){
			return (D)ConvertUtils.convert(valueStr, clazz);
		}else{
			throw new UnsupportedOperationException("Converter implementation is not defined!");
		}
	}
	
	public <D>D[] convertValues(Class<D> clazz, Object mapValue){
		if(mapValue==null) return null;
		Class valueClass = mapValue.getClass();
		
		Object valueObjs[] = null;
		int arraySize = 0;
		
		if(valueClass.isArray()){
			if(mapValue instanceof int[]){
	    		return (D[])ArrayUtils.toObject((int[])mapValue);
	    	}else if(mapValue instanceof double[]){
	    		return (D[])ArrayUtils.toObject((double[])mapValue);
	    	}else if(mapValue instanceof long[]){
	    		return (D[])ArrayUtils.toObject((long[])mapValue);
	    	}else if(mapValue instanceof boolean[]){
	    		return (D[])ArrayUtils.toObject((boolean[])mapValue);
	    	}else if(mapValue instanceof float[]){
	    		return (D[])ArrayUtils.toObject((float[])mapValue);
	    	}else if(mapValue instanceof short[]){
	    		return (D[])ArrayUtils.toObject((short[])mapValue);
	    	}else if(mapValue instanceof char[]){
	    		return (D[])ArrayUtils.toObject((char[])mapValue);
	    	}else if(mapValue instanceof byte[]){
	    		return (D[])ArrayUtils.toObject((byte[])mapValue);
	    	}
			
			valueObjs = (Object[])mapValue;
			arraySize = valueObjs.length;
			
		}else if(mapValue instanceof Collection){
			
			Collection valueCollect = ((Collection)mapValue);
			valueObjs = valueCollect.toArray();
			arraySize = valueCollect.size();
			
		}else if(mapValue instanceof Map){
			throw new UnsupportedOperationException("Cannot convert Map type value");
		}else{
//			throw new UnsupportedOperationException("method gets(Class clazz,Object key) support array or " +
//					"collection value only! please use get(Class clazz,Object key) instead.");
			valueObjs = new Object[]{mapValue};
			arraySize = 1;
		}
		
		if(valueObjs!=null){
			D values[] = (D[])Array.newInstance(clazz, arraySize);
			for(int i=0;i<valueObjs.length;i++){
				Object value = getTransformedValue(valueObjs[i]);
				values[i] = convertValue(clazz, value);
			}
			return values;
		}
		
		return null;
	}
	
	public static class MapValueConvertException extends RuntimeException{

		private static final long serialVersionUID = 1L;
		
		private Object key;
		private Object value;
		
		public MapValueConvertException() {
			super();
		}
		
		public MapValueConvertException(String message, Throwable cause) {
			super(message, cause);
		}

		public MapValueConvertException(String message) {
			super(message);
		}

		public MapValueConvertException(Throwable cause) {
			super(cause);
		}

		public Object getKey() {
			return key;
		}

		public MapValueConvertException setKey(Object key) {
			this.key = key;
			return this;
		}

		public Object getValue() {
			return value;
		}
		
		public MapValueConvertException setValue(Object value) {
			this.value = value;
			return this;
		}
		
		@Override
		public String toString(){
			return "MapValueConvertException["+key+","+value+"]: "+getMessage();
		}
	}
	
}
