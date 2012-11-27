package com.siberhus.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class FileChecksum {
	
	public static byte[] createChecksum(File file, String algorithm) throws Exception {
		
		if(file.isDirectory()){
			throw new IllegalArgumentException("file cannot be directory");
		}
		InputStream fis = new FileInputStream(file);
		
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance(algorithm);
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		return complete.digest();
	}
	
	public static String getMD5Checksum(File file) throws Exception {
		byte[] b = createChecksum(file, "MD5");
		return convertByeArrayToHex(b);
	}
	
	public static String getSHA1Checksum(File file) throws Exception {
		byte[] b = createChecksum(file, "SHA-1");
		return convertByeArrayToHex(b);
	}
	
	private static String convertByeArrayToHex(byte[] b){
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
	public static void main(String[] args)throws Exception {
		System.out.println(getMD5Checksum(new File("E:\\100RICOH\\R0016460.JPG")));
	}
}
