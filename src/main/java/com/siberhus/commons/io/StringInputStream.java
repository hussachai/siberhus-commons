package com.siberhus.commons.io;

import java.io.IOException;
import java.io.InputStream;


public class StringInputStream extends InputStream {

	protected int strOffset;
	protected int charOffset;
	protected int available;
	protected String str;
	
	public StringInputStream(String s) {
		strOffset = 0;
		charOffset = 0;
		str = s;
		available = s.length() << 1;
	}

	public int read() throws IOException {
		if (available == 0)
			return -1;
		available--;
		char c = str.charAt(strOffset);
		if (charOffset == 0) {
			charOffset = 1;
			return (c & 0x0000ff00) >> 8;
		} else {
			charOffset = 0;
			strOffset++;
			return c & 0x000000ff;
		}
	}

	public int available() throws IOException {
		return available;
	}
	
	
}

