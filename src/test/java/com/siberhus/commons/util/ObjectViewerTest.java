package com.siberhus.commons.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectViewerTest {
	
	List<Integer> listData;
	
	@Before
	public void generateTestListData(){
		listData = new ArrayList<Integer>();
		listData.add(1);
		listData.add(5);
		listData.add(5);
		listData.add(-1);
		listData.add(10);
		listData.add(10);
		listData.add(11);
		listData.add(10);
	}
	
	@Test
	public void testUniqueList(){
		ObjectViewer ov = new ObjectViewer(listData);
		ov.setUniqueList(true);
		Assert.assertEquals("1,5,-1,10,11", ov.toString());
	}
	
	@Test
	public void testListGrouping(){
		ObjectViewer ov = new ObjectViewer(listData);
		ov.setListGrouping(true);
		ov.setListGroupingChars(ObjectViewer.GROUPING_CHARS.CURLY_BRACKETS);
		ov.setListValueDelimeter(",");
		Assert.assertEquals("1,5{2},-1,10{3},11", ov.toString());
	}
	
	@Test
	public void testAutoSortList(){
		ObjectViewer ov = new ObjectViewer(listData);
		ov.setListGrouping(true);
		ov.setListGroupingChars(ObjectViewer.GROUPING_CHARS.CURLY_BRACKETS);
		ov.setListValueDelimeter(",");
		ov.setAutoSortList(true);
		Assert.assertEquals("-1,1,5{2},10{3},11", ov.toString());
	}
	
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			new ObjectViewer().toString();
//			new String().toString();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		
	}
}
