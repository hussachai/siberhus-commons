package com.siberhus.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class TrimUtilsTest {
	
	private final String NBSP = (char)0xA0+"";
	
	@Test
	public void testTrimScalar(){
		String data = NBSP+" \thello\t "+NBSP;
		Assert.assertEquals(NBSP+" \thello\t "+NBSP,TrimUtils.trim(data));
		Assert.assertEquals("hello",TrimUtils.trimAllSpaces(data));
	}
	
	@Test
	public void testTrimArray(){
		String data[] = new String[]{NBSP+" \thello\t "+NBSP," "};
		Assert.assertArrayEquals(
				new String[]{NBSP+" \thello\t "+NBSP,""}
				,TrimUtils.trim(data));
		Assert.assertArrayEquals(
				new String[]{"hello",""}
				,TrimUtils.trimAllSpaces(data));
		
	}
	
	@Test
	public void testRemoveAllNumbersAtTheLeftSide(){
		String data = "1234hello";
		data = TrimUtils.removeLeft(TrimUtils.NUMBER_CHARS, true, data);
		Assert.assertEquals("hello", data);
	}
	
	@Test
	public void testRemoveAllNonNumbersAtTheLeftSide(){
		String data = "hello1234";
		data = TrimUtils.removeLeft(TrimUtils.NUMBER_CHARS, false, data);
		Assert.assertEquals("1234", data);
	}
	
	@Test
	public void testRemoveAllNumbersAtTheRightSide(){
		String data = "hello1234";
		data = TrimUtils.removeRight(TrimUtils.NUMBER_CHARS, true, data);
		Assert.assertEquals("hello", data);
	}
	
	@Test
	public void testRemoveAllNonNumbersAtTheRightSide(){
		String data = "1234hello";
		data = TrimUtils.removeRight(TrimUtils.NUMBER_CHARS, false, data);
		Assert.assertEquals("1234", data);
	}
	
	
}
