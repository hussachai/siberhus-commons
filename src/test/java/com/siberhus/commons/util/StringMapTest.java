package com.siberhus.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringMapTest {
	
	@Test
	public void splitValue(){
		StringMap map = new StringMap();
		map.setKeyCaseMode(StrKeyMap.CASE.UPPER);
		map.setValueCaseMode(StrKeyMap.CASE.LOWER);
		map.setKeyTrimMode(StrKeyMap.TRIM.DEFAULT);
		map.setValueTrimMode(StrKeyMap.TRIM.TO_NULL);
		
		map.put("animals", "Dog, cat , pig, KOALA");
		map.put("numbers", "1\t| 2| 3|4\t| |5");
		
		assertArrayEquals(new String[]{"dog","cat","pig","koala"}
			,map.getSplitValue("animals", ","));
		
		assertArrayEquals(new Integer[]{1,2,3,4,null,5}
			,map.getSplitValue(Integer.class,"numbers", "|"));
	}
}
