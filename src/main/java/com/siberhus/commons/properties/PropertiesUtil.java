package com.siberhus.commons.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.siberhus.commons.util.ResourceUtils;

public class PropertiesUtil {
	
	
	public static Properties create(String propsFilePath) throws IOException{
		File propsFile = null;
		try{
			propsFile = ResourceUtils.getFile(propsFilePath);
			return create(propsFile);
		}catch(IOException e){
			throw e;
		}
	}
	
	public static Properties create(File propsFile) throws IOException{
		
		Properties props = new Properties();
		InputStream in = null;
		try{
			in = new FileInputStream(propsFile);
			props.load(in);
			return props;
		}catch(IOException e){
			throw e;
		}finally{
			if(in!=null){
				try{ in.close(); }catch(Exception e){}
			}
		}
	}
	
	public static void store(Properties props, File propFile){
		
		OutputStream out = null;
		try{
			out = new FileOutputStream(propFile);
			props.store(out,null);
		}catch(IOException e){
			throw new RuntimeException(e);
		}finally{
			if(out!=null){
				try{ out.close(); }catch(Exception e){}
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		PropertiesUtil.create("ss");
	}
}
