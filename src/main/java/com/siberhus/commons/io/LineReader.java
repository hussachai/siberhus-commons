package com.siberhus.commons.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

public class LineReader {
	
	private boolean ignoreComment = true;
	private String commentChars = "#";
	
	private Collection<String> lineCollect = new ArrayList<String>();
	
	public LineReader(){}
	
	public LineReader(File file) throws IOException {
		this(file,"#");
	}
	
	public LineReader(File file, String commentChars)throws IOException{
		this.commentChars = commentChars.trim();
		FileReader reader = null;
		try{
			reader = new FileReader(file);
			load(reader);	
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
	}
	
	public LineReader(String fileName) throws IOException{
		this(fileName,"#");
	}
	public LineReader(String fileName, String commentChars)throws IOException{
		this(new File(fileName),commentChars);
	}
	
	public boolean isIgnoreComment() {
		return ignoreComment;
	}

	public void setIgnoreComment(boolean ignoreComment) {
		this.ignoreComment = ignoreComment;
	}
	
	public String getCommentChars() {
		return commentChars;
	}

	public void setCommentChars(String commentChars) {
		this.commentChars = commentChars;
	}
	
	public synchronized void load(Reader reader)throws IOException{
		BufferedReader bReader = null;
		if(reader instanceof BufferedReader){
			bReader = (BufferedReader)reader;
		}else{
			bReader = new BufferedReader(reader);
		}
		lineCollect.clear();
		String line = null;
		while( (line=bReader.readLine())!=null){
			line = line.trim();
			if(line.length()==1){
				if(line.charAt(0)==65279){
					//ignore BOM
					continue;
				}
			}
			if("".equals(line)){
				continue;
			}
			if(ignoreComment){ 
				if(!line.startsWith(commentChars)){
					lineCollect.add(line);
				}	
			}else{
				lineCollect.add(line);
			}
		}
	}
	
	public synchronized void save(Writer writer) throws IOException{
		BufferedWriter bWriter = null;
		if(writer instanceof BufferedWriter){
			bWriter = (BufferedWriter)writer;
		}else{
			bWriter = new BufferedWriter(writer);
		}
		for(String line : lineCollect){
			bWriter.append(line);
			bWriter.newLine();
		}
	}
	
	public Collection<String> getLines(){
		return lineCollect;
	}
	

	public static void main(String[] args)throws Exception {
		LineReader lineReader = new LineReader(LineReader.class.getResource("test").getFile());
		System.out.println(lineReader.getLines());
	}
}
