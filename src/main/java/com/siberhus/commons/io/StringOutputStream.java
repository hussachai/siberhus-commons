package com.siberhus.commons.io;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * Provides an OutputStream to an internal String. Internally converts bytes to
 * a Strings and stores them in an internal StringBuffer.
 */
public class StringOutputStream extends OutputStream implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The internal destination StringBuffer.
	 */
	protected StringBuffer buffer = null;

	/**
	 * Creates new StringOutputStream, makes a new internal StringBuffer.
	 */
	public StringOutputStream() {
		super();
		buffer = new StringBuffer();
	}

	/**
	 * Returns the content of the internal StringBuffer as a String, the result
	 * of all writing to this OutputStream.
	 * 
	 * @return returns the content of the internal StringBuffer
	 */
	public String toString() {
		return buffer.toString();
	}

	/**
	 * Sets the internal StringBuffer to null.
	 */
	public void close() {
		buffer = null;

	}

	/**
	 * Writes and appends byte array to StringOutputStream.
	 * 
	 * @param b
	 *            byte array
	 */
	public void write(byte[] b) {
		buffer.append(toCharArray(b));
     }

	/**
	 * Writes and appends a byte array to StringOutputStream.
	 * 
	 * @param b
	 *            the byte array
	 * @param off
	 *            the byte array starting index
	 * @param len
	 *            the number of bytes from byte array to write to the stream
	 */
	public void write(byte[] b, int off, int len) {
		if ((off < 0) || (len < 0) || (off + len) > b.length) {
			throw new IndexOutOfBoundsException(
					"StringOutputStream.write: Parameters out of bounds.");
		}
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++) {
			bytes[i] = b[off];
			off++;
		}
		buffer.append(toCharArray(bytes));
	}

	/**
	 * Writes and appends a single byte to StringOutputStream.
	 * 
	 * @param b the byte as an int to add
	 */
	public void write(int b) {
		buffer.append((char) b);
	}

	public StringBuffer getBuffer(){
		return buffer;
	}
	
	private static char[] toCharArray(byte[] barr) {
		if (barr == null) {
			return null;
		}
		char[] carr = new char[barr.length];
		for (int i = 0; i < barr.length; i++) {
			carr[i] = (char) barr[i];
		}
		return carr;
	}
	
}
