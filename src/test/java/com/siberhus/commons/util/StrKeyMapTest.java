package com.siberhus.commons.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.siberhus.commons.util.StrKeyMap.MapValueConvertException;


public class StrKeyMapTest {
	
	@Test
	public void keyCaseMode(){
		StrKeyMap<String> map = new StrKeyMap<String>();
		map.setKeyCaseMode(StrKeyMap.CASE.UPPER);
		map.put("hello", "hello");
		
		assertTrue(map.containsKey("hello"));
		assertTrue(map.containsKey("HELLO"));
		assertTrue(map.containsKey("HeLLo"));
		
		assertTrue(Literal.set("HELLO").equals(map.keySet()));
		assertFalse(Literal.set("hello").equals(map.keySet()));
	}
	
	@Test
	public void keyDefaultTrimMode(){
		StrKeyMap<String> map = new StrKeyMap<String>();
		map.setKeyTrimMode(StrKeyMap.TRIM.DEFAULT);
		map.put(" hello ", "hello");
		map.put("\t", "tab");
		map.put("    ", "blank");
		map.put(null, "null");
		
		assertTrue(map.containsKey("hello"));
		assertTrue(map.containsKey(""));
		assertTrue(map.containsKey("\t"));//key will be trimed
		assertTrue(map.containsKey(null));
		
		assertEquals("blank", map.get("\t"));
		assertEquals("blank", map.get("       "));
		assertEquals("blank", map.get(""));
		assertEquals("null", map.get(null));
		
		assertEquals(3, map.keySet().size());
	}
	
	@Test
	public void keyToEmptyTrimMode(){
		StrKeyMap<String> map = new StrKeyMap<String>();
		map.setKeyTrimMode(StrKeyMap.TRIM.TO_EMPTY);
		map.put("\t", "tab");
		map.put("    ", "blank");
		map.put(null, "null");
		
		assertTrue(map.containsKey(""));
		assertTrue(map.containsKey(null));//null is converted to empty
		
		assertEquals("null", map.get("\t"));
		assertEquals("null", map.get(""));
		assertEquals("null", map.get(null));
		
		assertEquals("",map.keySet().iterator().next());
	}
	
	@Test
	public void keyToNullTrimMode(){
		StrKeyMap<String> map = new StrKeyMap<String>();
		map.setKeyTrimMode(StrKeyMap.TRIM.TO_NULL);
		map.put("\t", "tab");
		map.put("    ", "blank");
		map.put(null, "null");
		
		assertTrue(map.containsKey(""));//empty is converted to null
		assertTrue(map.containsKey(null));
		
		assertEquals("null", map.get("\t"));
		assertEquals("null", map.get(""));
		assertEquals("null", map.get(null));
		
		assertNull(map.keySet().iterator().next());
	}
	
	@Test
	public void valueCaseMode(){
		StrKeyMap<String> map = new StrKeyMap<String>();
		map.setValueCaseMode(StrKeyMap.CASE.UPPER);
		map.put("hello", "hello");
		
		assertTrue(map.containsValue("hello"));
		assertTrue(map.containsValue("HELLO"));
		assertTrue(map.containsValue("HeLLo"));
		
		assertTrue("HELLO".equals(map.values().iterator().next()));
		assertFalse("hello".equals(map.values().iterator().next()));
	}
	
	@Test
	public void valueDefaultTrimMode(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setValueTrimMode(StrKeyMap.TRIM.DEFAULT);
		map.put("number", new BigDecimal(50));
		map.put("name", " siberhus \t ");
		map.put("company", "siberhus");
		map.put("null", null);
		map.put("blank", "   \t ");
		map.put("spouse",new String[]{"\t hussachai","sirimon ",null," "});
		
		assertEquals(new BigDecimal(50), map.get("number"));
		assertEquals("siberhus", map.get("name"));
		assertEquals("siberhus", map.get("company"));
		assertNull(map.get("null"));
		assertEquals("", map.get("blank"));
		assertArrayEquals(new String[]{"hussachai","sirimon",null,""}
			, map.gets(String.class,"spouse"));
	}
	
	@Test
	public void valueToEmptyTrimMode(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setValueTrimMode(StrKeyMap.TRIM.TO_EMPTY);
		map.put("number", new BigDecimal(50));
		map.put("name", " siberhus \t ");
		map.put("company", "siberhus");
		map.put("null", null);
		map.put("blank", "   \t ");
		map.put("spouse",new String[]{"\t hussachai","sirimon ",null," "});
		
		assertEquals(new BigDecimal(50), map.get("number"));
		assertEquals("siberhus", map.get("name"));
		assertEquals("siberhus", map.get("company"));
		assertEquals("", map.get("null"));
		assertEquals("", map.get("blank"));
		assertArrayEquals(new String[]{"hussachai","sirimon","",""}
			, map.gets(String.class,"spouse"));
	}
	
	@Test
	public void valueToNullTrimMode(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setValueTrimMode(StrKeyMap.TRIM.TO_NULL);
		map.put("number", new BigDecimal(50));
		map.put("name", " siberhus \t ");
		map.put("company", "siberhus");
		map.put("null", null);
		map.put("blank", "   \t ");
		map.put("spouse",new String[]{"\t hussachai","sirimon ",null," "});
		
		assertEquals(new BigDecimal(50), map.get("number"));
		assertEquals("siberhus", map.get("name"));
		assertEquals("siberhus", map.get("company"));
		assertNull(map.get("null"));
		assertNull(map.get("blank"));
		assertArrayEquals(new String[]{"hussachai","sirimon",null,null}
			, map.gets(String.class,"spouse"));
	}
	
	@Test
	public void convertScalarType(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setValueTrimMode(StrKeyMap.TRIM.TO_NULL);
		map.setConverterImpl(StrKeyMap.CONVERTER_IMPL.SIBERHUS_CONVERTER);
		map.put("int",101);
		map.put("double",1.11);
		map.put("number","1,234.33");
		map.put("string","hello");
		map.put("class", "java.lang.String");
		map.put("birthdate", "  \t \n");
		
		assertNull(map.get("not exists!"));
		assertEquals("default", map.get(String.class,"not exist!","default"));
		
		assertEquals(101, map.get("int"));
		assertEquals(1.11, map.get("double"));
//		assertEquals(1.11, map.get(Double.class,"double"));
		assertTrue(1.11 == map.get(Double.class,"double"));//unboxing before comparing
		
		assertTrue(1234.33F == map.get(Float.class,"number"));
		assertEquals(new BigDecimal("1234.33"), map.get(BigDecimal.class,"number"));
		
		assertSame(java.lang.String.class, map.get(Class.class,"class"));
		
		assertNull(map.get(BigDecimal.class,"string",null));
		
		assertNull(map.get(Date.class,"birthdate"));
	}
	
	@Test(expected=MapValueConvertException.class)
	public void convertScalarTypeError(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setConverterImpl(StrKeyMap.CONVERTER_IMPL.SIBERHUS_CONVERTER);
		map.put("number","error");
		map.get(BigDecimal.class,"number");
	}
	
	@Test
	public void convertArrayType(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setConverterImpl(StrKeyMap.CONVERTER_IMPL.SIBERHUS_CONVERTER);
		map.put("ints", new int[]{1,2,3});
		map.put("doubles", new String[]{"1","2","3"});
		map.put("floats", new float[]{1.1F,2.2F});
		
		map.put("numberSet", Literal.linkedSet("1,200","500","4.5"));
		map.put("numberList", Literal.list("1","4.5","1,100"));
		map.put("scalar", "50");
		
		assertNull(map.gets(Object.class,"not exists!"));
		
		assertArrayEquals(new Integer[]{1,2,3}, map.gets(Integer.class, "ints"));
		assertArrayEquals(new Double[]{1.0,2.0,3.0}, map.gets(Double.class,"doubles"));
		assertArrayEquals(new Float[]{1.1F,2.2F}, map.gets(Float.class,"floats"));
		
		assertArrayEquals(new Float[]{1.1F,2.2F}, map.gets(Double.class,"floats"));
		
		assertArrayEquals(new Double[]{1200D,500D,4.5D}, map.gets(Double.class,"numberSet"));
		assertArrayEquals(new Double[]{1D,4.5D,1100D}, map.gets(Double.class,"numberList"));
		
		assertArrayEquals(new Integer[]{50}, map.gets(Integer.class, "scalar"));
	}
	
	
	@Test(expected=ClassCastException.class)
	public void convertWrongTypeArray(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setConverterImpl(StrKeyMap.CONVERTER_IMPL.SIBERHUS_CONVERTER);
		map.put("ints", new int[]{1,2,3});
		Double values[] = map.gets(Double.class, "ints");
	}
	
	@Test(expected=MapValueConvertException.class)
	public void getScalarFromArray(){
		StrKeyMap<Object> map = new StrKeyMap<Object>();
		map.setConverterImpl(StrKeyMap.CONVERTER_IMPL.SIBERHUS_CONVERTER);
		map.put("ints", new int[]{1,2,3});
		map.get(Integer.class,"ints");
	}
	
}
