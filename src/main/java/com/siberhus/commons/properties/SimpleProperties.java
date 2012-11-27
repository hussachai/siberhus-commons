package com.siberhus.commons.properties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.regex.Pattern;

import com.siberhus.commons.converter.TypeConvertUtils;
import com.siberhus.commons.io.LineReader;

/**
 * 
 * TODO: this class is needed to escape value
 * 
 * @author hussachai
 *
 */
@SuppressWarnings("unchecked")
public class SimpleProperties extends AbstractSimpleProperties {
	
	private static final long serialVersionUID = 1L;
	
	private char delimiter = ',';
	
	public SimpleProperties(){}
	
	private SimpleProperties(SimpleProperties props){
		super.putAll(props);
		setFile(props.getFile());
		setEncoding(props.getEncoding());
		setInterpolatable(props.isInterpolatable());
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void load(Reader reader) throws IOException{
		super.clear(); //clear old values
		LineReader lr = new LineReader();
		lr.load(reader);
		for(String line : lr.getLines()){
			int eqIdx = line.indexOf('=');
			String key = null;
			String value = null;
			if(eqIdx==-1){
				key = line;
			}else{
				key = line.substring(0,eqIdx);
				value = line.substring(eqIdx+1,line.length());
			}
			key = key.trim();
			if(value!=null){
				value = value.trim();
			}
			super.put(key, value);
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void save(Writer writer,String comments) throws IOException{
		BufferedWriter bWriter = null;
		if(writer instanceof BufferedWriter){
			bWriter = (BufferedWriter)writer;
		}else{
			bWriter = new BufferedWriter(writer);
		}
		if(comments!=null){
			bWriter.append("#"+comments);
		}
		bWriter.newLine();
		for(Map.Entry<Object, Object> entry : super.entrySet()){
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();
			bWriter.append(key);
			bWriter.append("=");
			bWriter.append(value);
			bWriter.newLine();
		}
		bWriter.flush();
	}
	
	public synchronized String setProperty(String key, String value){
		String oldValue = getProperty(key);
		super.put(key, value);
		return oldValue;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized String[] setProperty(String key, String[] values){
		String oldValues[] = getPropertyAsArray(key);
		String valueStr = convertArrayToString(values);
		super.put(key, valueStr);
		return oldValues;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized String[] addPropertyValue(String key, String value){
		String oldValues[] = getPropertyAsArray(key);
		String oldValueStr = getProperty(key);
		String valueStr = null;
		if(value!=null){
			if(oldValueStr!=null){
				valueStr = oldValueStr + getDelimiter() + value;
			}else{
				valueStr = value;
			}
			super.put(key, valueStr);
		}
		return oldValues;
	}
	
	@SuppressWarnings("unchecked")
	public String getProperty(String key){
		String value = (String)super.get(key);
		if(isInterpolatable()){
			value = interpolate(value);
		}
		return value;
	}
	
	public String getProperty(String key, String defaultValue){
		String value = getProperty(key);
		if(value!=null){
			return value;
		}
		return defaultValue;
	}
	
	@SuppressWarnings("unchecked")
	public String[] getPropertyAsArray(String key){
		String values = getProperty(key);
		if(values!=null){
			return splitValue(values);
		}
		return null;
	}
	
	public String[] getPropertyAsArray(String key, String[] defaultValues){
		String values[] = getPropertyAsArray(key);
		if(values!=null){
			return values;
		}
		return defaultValues;
	}
	
	@SuppressWarnings("unchecked")
	public <T>T getProperty(Class<T> type, String key, T defaultValue){
		String value = getProperty(key);
		if(value!=null){
			return (T)TypeConvertUtils.convert(value, type, defaultValue);
		}
		return defaultValue;
	}
	
	public <T>T getProperty(Class<T> type, String key){
		String value = getProperty(key);
		return TypeConvertUtils.convert(value, type);
	}
	
	@SuppressWarnings("unchecked")
	public <T>T[] getPropertyAsArray(Class<T> type, String key){
		String[] values = getPropertyAsArray(key);
		if(values!=null){
			return TypeConvertUtils.convert(values, type);
		}
		return null;
	}
	
	public SimpleProperties duplicate(){
		return new SimpleProperties(this);
	}
	
	public char getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}
	
	private String[] splitValue(Object value){
		if(value!=null){
			String values[] = value.toString().split(
					Pattern.quote(String.valueOf(getDelimiter())));
			for(int i=0;i<values.length;i++){
				values[i] = values[i].trim();
			}
			return values;
		}
		return null;
	}
	
	private String convertArrayToString(String[] values){
		StringBuilder buffer = new StringBuilder();
		for(int i=0;i<values.length-1;i++){
			buffer.append(values[i]).append(getDelimiter());
		}
		buffer.append(values[values.length-1]);
		return buffer.toString();
	}
	
	public static void main(String[] args) throws IOException {
		ISimpleProperties s = new SimpleProperties();
		s.setProperty("version", "java version ${java.version}");
		s.setProperty("lookupVersion","${version}");
		System.out.println(s.getProperty("version"));
		System.out.println(s.getProperty("lookupVersion"));
		ISimpleProperties s2 = s.duplicate();
		s2.setProperty("version", "fuck");
		System.out.println(s2.getProperty("version"));
		System.out.println(s.getProperty("version"));
	}

	
}
