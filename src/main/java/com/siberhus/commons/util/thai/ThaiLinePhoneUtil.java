package com.siberhus.commons.util.thai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.siberhus.commons.io.LineReader;

public class ThaiLinePhoneUtil {
	
	private static final Map<String,String[]> regionPhonePrefixMap;
	
	static{
		BufferedReader reader = null;
		Map<String,String[]> regionPhonePrefixMapTmp = new HashMap<String, String[]>();
		try{
			
			reader = new BufferedReader(new InputStreamReader(ThaiLinePhoneUtil.class
					.getResourceAsStream("region_phone_prefix.txt"),"UTF-8"));
			LineReader lineReader = new LineReader();
			lineReader.load(reader);
			for(String line : lineReader.getLines()){
				line =  line.trim();
				if(line.length()==0|| !line.contains("="))continue;
				String parts[] = line.split("=");
				regionPhonePrefixMapTmp.put(parts[0], parts[1].split("|"));
			}
		}catch(Exception e){}finally{
			if(reader!=null){try{reader.close();}catch(Exception e){}}
		}
		regionPhonePrefixMap = Collections.unmodifiableMap(regionPhonePrefixMapTmp);
	}
	
	
	public static Map<String,String[]> getRegionPhonePrefixMap(){
		return regionPhonePrefixMap;
	}
	
	public static String[] getProvincesByPhonePrefix(String prefix){
		return regionPhonePrefixMap.get(prefix);
	}
	
	public static boolean isValidPhonePrefix(String prefix){
		return regionPhonePrefixMap.containsKey(prefix);
	}
	
	public static boolean isValidPhone(String phn){
		if(phn.length()<3){
			//just prevent array index out of bound error
			return false;
		}
		if(regionPhonePrefixMap.containsKey(phn.substring(0,2))){
			return true;
		}else if(regionPhonePrefixMap.containsKey(phn.substring(0,3))){
			return true;
		}else{
			return false;
		}
	}
}
