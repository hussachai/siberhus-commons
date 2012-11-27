package com.siberhus.tools.datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import com.siberhus.commons.converter.ITypeConverter;
import com.siberhus.commons.converter.TypeConvertUtils;

public class ListDataGenerator<T> extends DataGenerator<T>{
	
	private List<String> dataList = new ArrayList<String>();
	
	private boolean randomOrder = false;
	
	private int cursor = 0;
	
	
	public ListDataGenerator(){
		super();
	}
	
	public ListDataGenerator(Locale locale){
		super(locale);
	}
	
	public boolean isRandomOrder() {
		return randomOrder;
	}

	public void setRandomOrder(boolean randomOrder) {
		this.randomOrder = randomOrder;
	}

	public void clear(){
		dataList.clear();
	}
	
	public void load(List<String> data){
		dataList.addAll(data);
	}
	
	public void load(String[] data){
		if(data!=null){
			for(String datum : data){
				dataList.add(datum);
			}
		}
	}
	
	public void loadFromFile(String dataFile) throws IOException{
		loadFromFile(new File(dataFile));
	}
	
	public void loadFromFile(File dataFile) throws IOException{
		BufferedReader in = null;
		try{
			in = new BufferedReader(new FileReader(dataFile));
			String line = null;
			while( (line=in.readLine())!=null){
				dataList.add(line);
			}
		}finally{
			IOUtils.closeQuietly(in);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T getValue(Class<? extends T> targetClass) throws Exception{
		
		if(dataList==null || dataList.size()==0){
			throw new Exception("Data List is empty! you must load data " +
					"before calling this method");
		}
		
		ITypeConverter<T> typeConverter = TypeConvertUtils
			.lookupByType(targetClass, getLocale());
		
		String value = null;
		if(!isRandomOrder()){
			cursor++;
			if(cursor>dataList.size()){
				cursor = 0;//reset
			}
			value = dataList.get(cursor);
		}else{
			value = dataList.get( (int)(Math.random()*dataList.size()) );
		}
		
		return typeConverter.convert(value, targetClass);
	}
	
	public static void main(String[] args)throws Exception {
		
//		Connection conn = LocalResource.createSqlConnection();
		ListDataGenerator dataList = new ListDataGenerator();
		dataList.loadFromFile("resources/datagen/en/family-names.txt");
		dataList.setRandomOrder(true);
		for(int i=0;i<50;i++){
			System.out.println(dataList.getValue(String.class));
		}
	}
}
