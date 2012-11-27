package com.siberhus.commons.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileExtensionFilter implements FilenameFilter {
	
	private List<String> extensionList;
	
	public FileExtensionFilter(String[] extensions){
		if(extensions!=null){
			extensionList = Arrays.asList(extensions);
		}
	}
	
	@Override
	public boolean accept(File dir, String name) {
		if(dir.isDirectory())return true;
		if(extensionList==null) return true;
		
		int lastDot = name.lastIndexOf(".");
		String extension = name.substring(lastDot, name.length());
		return extensionList.contains(extension);
	}
	
}
