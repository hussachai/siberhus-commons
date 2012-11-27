package com.siberhus.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.siberhus.commons.lang.ClassUtils;

/*
 * Need to be revised
 */
public class ResourceUtils {

	public static final String CLASSPATH_URL_PREFIX = "classpath:";
	
	public static final String FILE_URL_PREFIX = "file:";
	
	public static InputStream openInputStream(String location) throws FileNotFoundException{
		
		InputStream is = null;
		String path = null;
		if(location.startsWith(CLASSPATH_URL_PREFIX)){
			path = location.substring(CLASSPATH_URL_PREFIX.length());
			is = ClassUtils.getDefaultClassLoader().getResourceAsStream(path);
			String description = "class path resource [" + path + "]";
			if(is==null){
				throw new FileNotFoundException(
						description + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
		}else if(location.startsWith(FILE_URL_PREFIX)){
			path = location.substring(FILE_URL_PREFIX.length());
			is = new FileInputStream(path);
		}else{
			is = new FileInputStream(location);
		}
		
		
		return is;
	}
	
	public static File getFile(String location) throws FileNotFoundException {
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = location.substring(CLASSPATH_URL_PREFIX.length());
			String description = "class path resource [" + path + "]";
			URL url = ClassUtils.getDefaultClassLoader().getResource(path);
			try{
				return new File(url.toURI());
			}catch(Exception e){
				throw new FileNotFoundException(
						description + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
		}else{
			File file = new File(location);
			if(!file.exists()){
				throw new FileNotFoundException(
						location + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
			return file;
		}
	}
	
	public static URL getURL(String location) throws FileNotFoundException {
		URL url = null;
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = location.substring(CLASSPATH_URL_PREFIX.length());
			String description = "class path resource [" + path + "]";
			url = ClassUtils.getDefaultClassLoader().getResource(path);
			try{
				return url;
			}catch(Exception e){
				throw new FileNotFoundException(
						description + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
			}
		}else{
			try {
				url = new URL(location);
			} catch (MalformedURLException e) {
				throw new FileNotFoundException(
						location + " cannot be resolved to absolute file path " +
						"because it does not reside in the file system");
				
			}
			return url;
		}
	}
	
	public static void main(String[] args) throws Exception{
//		System.out.println(ResourcePathUtils.getURL("file:META-INF/MANIFEST.MF"));
		System.out.println(ResourceUtils.getFile("classpath:com/siberhus/datacleaner/SpaceCharCleaner.class"));
	}
}
