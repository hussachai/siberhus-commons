package com.siberhus.commons.io;

import java.io.*;

import org.apache.commons.io.IOUtils;

public class BufferedFileWriter extends BufferedWriter{

	private File file;
	
	public BufferedFileWriter(File file) throws IOException{
		super(new FileWriter(file));
		this.file = file;
	}
	
	public BufferedFileWriter(String fileName,boolean append) throws IOException{
		super(new FileWriter(fileName,append));
		this.file = new File(fileName);
	}
	
	public BufferedFileWriter(String fileName) throws IOException{
		super(new FileWriter(fileName));
		this.file = new File(fileName); 
	}
	
	public File getFile() {
		return file;
	}
	
	public void closeQuietly(){
		IOUtils.closeQuietly(this);
	}
}
