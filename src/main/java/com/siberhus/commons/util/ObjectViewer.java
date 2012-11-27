package com.siberhus.commons.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class ObjectViewer<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
//	private final Logger logger = LoggerFactory.getLogger(ObjectViewer.class);
	
	
	public static enum GROUPING_CHARS {
		BRACKETS,SQUARE_BRACKETS,CURLY_BRACKETS,LTGT
	}
	
	private T object = null;
	private String propertyName = null;
	private boolean convertNullToEmptyString = false;
	
	private String elementPrefix = "";
	private String elementSuffix = "";
	private String listValueDelimeter = ",";
	private boolean listGrouping = true;//if this value is set to true uniqueList will be false
	private GROUPING_CHARS listGroupingChars = GROUPING_CHARS.BRACKETS;
	private boolean autoSortList = false;
	private boolean uniqueList = false;//if this value is set to true listGrouping will be false
	
	private transient Method method = null;
	
	public ObjectViewer(){}
	
	public ObjectViewer(T object){
		this.object = object;
	}
	
	public ObjectViewer(T object,String propertyName){
		this(object);
		this.propertyName = propertyName;
	}
	
	public T getObject() {
		return object;
	}

	public ObjectViewer<T> setObject(T object) {
		this.object = object;
		return this;
	}
	
	public String getElementPrefix() {
		return elementPrefix;
	}

	public ObjectViewer<T> setElementPrefix(String elementPrefix) {
		this.elementPrefix = elementPrefix;
		return this;
	}

	public String getElementSuffix() {
		return elementSuffix;
	}

	public ObjectViewer<T> setElementSuffix(String elementSuffix) {
		this.elementSuffix = elementSuffix;
		return this;
	}

	public String getListValueDelimeter() {
		return listValueDelimeter;
	}
	
	public ObjectViewer<T> setListValueDelimeter(String listValueDelimeter) {
		if(listValueDelimeter==null){
			throw new IllegalArgumentException("delimeter cannot be null");
		}else{
			this.listValueDelimeter = listValueDelimeter;
		}
		return this;
	}
	
	public boolean isListGrouping() {
		return listGrouping;
	}

	public ObjectViewer<T> setListGrouping(boolean listGrouping) {
		this.listGrouping = listGrouping;
		if(listGrouping){
			this.uniqueList = false;
		}
		return this;
	}
	
	public GROUPING_CHARS getListGroupingChars() {
		return listGroupingChars;
	}

	public ObjectViewer<T> setListGroupingChars(GROUPING_CHARS listGroupingChars) {
		this.listGroupingChars = listGroupingChars;
		return this;
	}

	public boolean isAutoSortList() {
		return autoSortList;
	}

	public ObjectViewer<T> setAutoSortList(boolean autoSortList) {
		this.autoSortList = autoSortList;
		return this;
	}
	
	public boolean isUniqueList() {
		return uniqueList;
	}

	public ObjectViewer<T> setUniqueList(boolean uniqueList) {
		this.uniqueList = uniqueList;
		if(uniqueList){
			this.listGrouping = false;
		}
		return this;
	}
	
	public boolean isConvertNullToEmptyString() {
		return convertNullToEmptyString;
	}
	
	public ObjectViewer<T> setConvertNullToEmptyString(boolean convertNullToEmptyString) {
		this.convertNullToEmptyString = convertNullToEmptyString;
		return this;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String toString(){
		String value = null;
		if(object!=null){
			if(propertyName==null){
				value = convertObjectToString(object);
			}else{
				try{
					method = getObjectMethod();
					Object result = method.invoke(object);
					value = (result==null)?"":result.toString();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if(isConvertNullToEmptyString()){
			if(value==null){
				value = "";
			}
		}
		return value;
	}
	
	private Method getObjectMethod(){
		if(method!=null){
			return method;
		}
		try{
			method = object.getClass().getMethod("get"+
					StringUtils.capitalize(propertyName));
			if(!method.isAccessible()){
				method.setAccessible(true);
			}
			return method;
		}catch(Exception e){
			throw new IllegalArgumentException("Property: "+
					propertyName+" not found!",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private String convertObjectToString(Object values){
		if(values==null){
			return null;
		}
		if(values instanceof Collection){
			Collection collection = (Collection)values;
			if(collection.size()>0){
				return convertObjectArrayToString(collection.toArray());
			}else{
				return null;
			}
		}else if(values.getClass().isArray()){
			Object array[] = (Object[])values;
			if(array.length>0){
				return convertObjectArrayToString(array);
			}else{
				return null;
			}
		}
		return values.toString();
	}
	
	private String convertObjectArrayToString(Object values[]){
		StringBuilder buffer = new StringBuilder();
		Map<Object,Integer> objCounterMap = null;
		Set<Object> objSet = null;
		if(isUniqueList()){
			if(isAutoSortList()){
				objSet = new TreeSet<Object>();
			}else{
				objSet = new LinkedHashSet<Object>();
			}
			for(Object value : values){
				objSet.add(value);
			}
			values = objSet.toArray();
		}else{
			if(isAutoSortList()){
				objCounterMap = new TreeMap<Object, Integer>();
			}else{
				objCounterMap = new LinkedHashMap<Object, Integer>();
			}
		}
		if(isListGrouping()){
			for(Object value : values){
				Integer counter = objCounterMap.get(value);
				if(counter==null){
					counter = 1;
				}else{
					counter++;
				}
				objCounterMap.put(value, counter);
			}
			for(Map.Entry<Object, Integer> entry : objCounterMap.entrySet()){
				Object value = entry.getKey();
				Integer counter = entry.getValue();
				buffer.append(getElementPrefix())
				.append(value)
				.append(getElementSuffix());
				if(counter!=1){
					if(getListGroupingChars()!=null){
						switch(getListGroupingChars()){
						case BRACKETS: buffer.append("(").append(counter).append(")");break;
						case CURLY_BRACKETS: buffer.append("{").append(counter).append("}");break;
						case SQUARE_BRACKETS: buffer.append("[").append(counter).append("]");break;
						case LTGT: buffer.append("<").append(counter).append(">");break;
						}
					}else{
						buffer.append("(").append(counter).append(")");
					}
				}
				buffer.append(getListValueDelimeter());
			}
		}else{
			for(Object value : values){
				buffer.append(getElementPrefix())
				.append(value)
				.append(getElementSuffix())
				.append(getListValueDelimeter());
			}
		}
		
		if(buffer.length()==0){
			return values.toString();
		}else{
			buffer.delete(buffer.length()-getListValueDelimeter().length(), buffer.length());
			return buffer.toString();
		}
	}
	
}
