package com.siberhus.commons.properties;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

public interface ISimpleProperties extends Map<Object,Object> {
	
	/**
	 * 
	 * @return null if load properties via Reader
	 */
	public File getFile();
	
	public void setFile(File file);
	/**
	 * 
	 * @return null if load properties via Reader
	 */
	public String getEncoding();
	
	public void setEncoding(String encoding);
	
	public void load(File file) throws IOException;
	
	public void load(File file,String enc) throws IOException;
	
	public void loadAndWatch(File file,int delay) throws IOException;
	
	public void loadAndWatch(File file,String enc,int delay) throws IOException;
	
	public void load(Reader reader) throws IOException;
	
	public void save(String comments) throws IOException;
	
	public void save(Writer writer,String comments) throws IOException;
	
	public String setProperty(String key, String value);
	
	public String[] addPropertyValue(String key, String value);
	
	public String[] setProperty(String key, String[] values);
	
	public String getProperty(String key);
	
	public String getProperty(String key, String defaultValue);
	
	public String[] getPropertyAsArray(String key);
	
	public String[] getPropertyAsArray(String key, String[] defaultValues);
	
	public <T>T getProperty(Class<T> type, String key, T defaultValue);
	
	public <T>T getProperty(Class<T> type, String key);
	
	public <T>T[] getPropertyAsArray(Class<T> type, String key);
	
	public void setInterpolatable(boolean interpolatable);
	
	public boolean isInterpolatable();
	
	public void setInterpolator(IPropertyInterpolator interpolator);
	
	public IPropertyInterpolator getInterpolator();
	
	public String interpolate(String value);
	
	public ISimpleProperties duplicate();
	
	public Properties toProperties();
	
	public Properties toInterpolatedProperties();
	
}
