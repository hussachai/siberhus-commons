package com.siberhus.commons.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DirectoryFileUtils {

	
	public static File getRequiredDir(File dir){
		checkDirArgument(dir);
		return dir;
	}
	
	public static File getRequiredDir(String dir){
		
		return getRequiredDir(new File(dir));
	}
	
	
	public static File getFile(File dir,String fileName){
		checkDirArgument(dir);
		if(StringUtils.isBlank(fileName)){
			throw new IllegalArgumentException("fileName cannot be blank");
		}
		return new File(dir.getAbsolutePath()+File.separator+fileName);
	}
	
	public static boolean hasFile(File dir,String fileName){
		checkDirArgument(dir);
		if(StringUtils.isBlank(fileName)){
			throw new IllegalArgumentException("fileName cannot be blank");
		}
		return Arrays.asList(dir.list()).contains(fileName);
	}
	
	public static File mkdir(File parentDir, String dirName){
		checkDirArgument(parentDir);
		if(StringUtils.isBlank(dirName)){
			throw new IllegalArgumentException("dirName cannot be blank");
		}
		File dir = new File(parentDir.getAbsolutePath()
				+File.separator+dirName);
		if(dir.exists()){
			return dir;
		}
		if(dir.mkdir()){
			return dir;
		}
		return null;
	}
	
	public static File mkdirs(File parentDir, String dirName){
		checkDirArgument(parentDir);
		if(StringUtils.isBlank(dirName)){
			throw new IllegalArgumentException("dirName cannot be blank");
		}
		File dir = new File(parentDir.getAbsolutePath()
				+File.separator+dirName);
		if(dir.exists()){
			return dir;
		}
		if(dir.mkdirs()){
			return dir;
		}
		return null;
	}
	
	public static List<File> listFiles(File directory,String[] extension,boolean recursive){
		FileExtensionFilter filter = new FileExtensionFilter(extension);
		List<File> fileList = new ArrayList<File>();
		if(!directory.isDirectory()){
			throw new IllegalArgumentException("Parameter 'directory' is not a directory");
		}
		if(recursive){
			recursiveListFiles(fileList,directory, filter);
		}else{
			File files[] = directory.listFiles(filter);
			for(File file : files){
				if(file.isFile()){
					fileList.add(file);
				}
			}
		}
		return fileList;
	}
	
	private static void recursiveListFiles(List<File> fileList,File directory,FilenameFilter filter){
		if(directory.isDirectory()){
			File files[] = directory.listFiles(filter);
			for(File file : files){
				recursiveListFiles(fileList,file,filter);
			}
		}else{
			fileList.add(directory);
		}
	}
	
	private static void checkDirArgument(File dir){
		
		if(dir==null){
			throw new IllegalArgumentException("Dir cannot be null");
		}
		if(!dir.isDirectory()){
			throw new IllegalArgumentException("Dir does not exist or it's not a directory");
		}
	}
	
}
