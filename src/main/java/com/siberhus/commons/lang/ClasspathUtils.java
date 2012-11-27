package com.siberhus.commons.lang;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Iterator;

import com.siberhus.commons.io.DirectoryFileUtils;

public class ClasspathUtils {
	
	private static final Class<?>[] parameters = new Class<?>[] { URL.class };
	
	public static void addClassesDir(String classesPath) throws IOException{
		addClassesDir(new File(classesPath));
	}
	
	public static void addClassesDir(File classesDir) throws IOException{
		if(!classesDir.exists() || !classesDir.isDirectory()){
			throw new IllegalArgumentException("classes directory: " +classesDir
					+" does not exist or it's not a directory");
		}
		addFile(classesDir,null,false);
	}
	
	public static void addLibDir(String libPath) throws IOException{
		addLibDir(new File(libPath));
	}
	
	public static void addLibDir(File libDir) throws IOException{
		if(!libDir.exists() || !libDir.isDirectory()){
			throw new IllegalArgumentException("lib directory: " +libDir
					+" does not exist or it's not a directory");
		}
		addFile(libDir,new String[]{"jar","zip"},true);
	}
	
	public static void addFile(String filePath,String extensions[],boolean recursive) throws IOException {
		
		addFile(new File(filePath),extensions,recursive);
	}
	
	public static void addFile(File file,String extensions[],boolean recursive) throws IOException {
		if(recursive){
			if(file.isDirectory()){
				Collection<File> fnodes = DirectoryFileUtils.listFiles(file, extensions, true);
				for(Iterator<File> fnodeIt=fnodes.iterator(); fnodeIt.hasNext();){
					File fnode = fnodeIt.next();
					addUrl(fnode.toURI().toURL());
				}
			}else if(file.isFile()){
				addUrl(file.toURI().toURL());
			}
		}else{
			addUrl(file.toURI().toURL());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addUrl(URL u) throws IOException {
//		System.out.println("Add file "+u.getFile()+" to classpath");
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
		Class sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}

	}
	
}
