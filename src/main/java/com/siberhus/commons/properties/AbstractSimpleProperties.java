package com.siberhus.commons.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;

import com.siberhus.commons.io.FileWatchdog;

public abstract class AbstractSimpleProperties extends LinkedHashMap<Object,Object> 
	implements ISimpleProperties{
	
	private File file;
	
	private String encoding = Charset.defaultCharset().name();
	
	private boolean interpolatable = true;
	
	private IPropertyInterpolator interpolator = new DefaultPropertyInterpolator(this);
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}
	
	public synchronized void load(File file) throws IOException{
		load(file,getEncoding());
	}
	
	public synchronized void load(File file,String encoding) throws IOException{
		this.file = file;
		this.encoding = encoding;
		InputStreamReader reader = null;
		try{
			reader = new InputStreamReader(new FileInputStream(file),encoding);
			load(reader);
		}finally{
			try{
				if(reader!=null) reader.close();
			}catch(Exception e){}
		}
	}
	
	public synchronized void loadAndWatch(File file,int delay) throws IOException {
		loadAndWatch(file,getEncoding(),delay);
	}
	
	public synchronized void loadAndWatch(File file,String encoding,int delay) throws IOException {
		this.file = file;
		this.encoding = encoding;
		PropertyWatchdog pdog = new PropertyWatchdog(file);
        pdog.setDelay(delay);
        pdog.setEncoding(encoding);
        pdog.start();
	}
	
	
	public synchronized void save(String comments) throws IOException {
		OutputStreamWriter writer = null;
		try{
			writer = new OutputStreamWriter(
				new FileOutputStream(getFile()),getEncoding());
			save(writer,comments);
		}finally{
			if(writer!=null) writer.close();
		}
	}
	
	public Properties toProperties(){
		Properties properties = new Properties();
		for(Object keyObj : keySet()){
			String key = (String)keyObj;
			Object valueObj = get(key);
			if(valueObj instanceof String){
				properties.setProperty(key, (String)valueObj);
			}else if(valueObj instanceof String[]){
				String values[] = (String[])valueObj;
				if(!ArrayUtils.isEmpty(values)){
					properties.setProperty(key, values[0]);
				}
			}
		}
		return properties;
	}
	
	public Properties toInterpolatedProperties(){
		Properties properties = new Properties();
		for(Object keyObj : keySet()){
			String key = (String)keyObj;
			String value = getProperty(key);
			properties.setProperty(key, value);
		}
		return properties;
	}
	
	public boolean isInterpolatable() {
		return interpolatable;
	}

	public void setInterpolatable(boolean interpolatable) {
		this.interpolatable = interpolatable;
	}
	
	public IPropertyInterpolator getInterpolator() {
		return interpolator;
	}

	public void setInterpolator(IPropertyInterpolator interpolator) {
		this.interpolator = interpolator;
	}
	
	public final String interpolate(String value){
		return getInterpolator().interpolate(value);
	}
	
	class PropertyWatchdog extends FileWatchdog{
		
		protected PropertyWatchdog(File file) {
			super(file);
		}
		
		@Override
		protected void doOnChange() {
			try {
				load(getFile(), getEncoding());
			} catch (Exception e) {
				throw new RuntimeException(e);//stop watchdog
			}
		}
	}
	
	
}
