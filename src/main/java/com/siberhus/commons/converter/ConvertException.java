package com.siberhus.commons.converter;

public class ConvertException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ConvertException() {
		super();
	}
	
	public ConvertException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public ConvertException(String arg0) {
		super(arg0);
	}

	public ConvertException(Throwable arg0) {
		super(arg0);
	}
	
	
}
