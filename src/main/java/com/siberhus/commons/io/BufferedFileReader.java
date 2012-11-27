package com.siberhus.commons.io;

import java.io.*;

import org.apache.commons.io.IOUtils;


public class BufferedFileReader extends BufferedReader{

	private File file;
	
	
	public BufferedFileReader(File file) throws IOException{
		super(new FileReader(file));
		this.file = file;
	}
	
	public BufferedFileReader(String fileName) throws IOException{
		super(new FileReader(fileName));
		this.file = new File(fileName); 
	}
	
	public File getFile() {
		return file;
	}
	
	public void closeQuietly(){
		IOUtils.closeQuietly(this);
	}
	
}
