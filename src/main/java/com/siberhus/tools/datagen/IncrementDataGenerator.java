package com.siberhus.tools.datagen;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.siberhus.commons.converter.ITypeConverter;
import com.siberhus.commons.converter.TypeConvertUtils;

public class IncrementDataGenerator<T> extends DataGenerator<T>{
	
	public enum PADDING { LEFT, RIGHT }
	
	private long sequence = 0;
	
	private long increment = 1;
	
	private int paddingSize = 0; //use only if T is String
	
	private char paddingChar = '0'; //use only if T is String
	
	private PADDING padding = null;
	
	
	public void setInitialValue(long value){
		this.sequence = value;
	}
	
	public long getIncrement() {
		return increment;
	}

	public void setIncrement(long increment) {
		this.increment = increment;
	}

	public PADDING getPadding() {
		return padding;
	}

	public void setPadding(PADDING padding) {
		this.padding = padding;
	}

	public char getPaddingChar() {
		return paddingChar;
	}

	public void setPaddingChar(char paddingChar) {
		this.paddingChar = paddingChar;
	}

	public int getPaddingSize() {
		return paddingSize;
	}

	public void setPaddingSize(int paddingSize) {
		this.paddingSize = paddingSize;
	}

	@SuppressWarnings("unchecked")
	public T getValue(Class<? extends T> targetClass) throws Exception {
		
		sequence += increment;
		
		String value = String.valueOf(sequence);
		
		if(padding!=null && targetClass==String.class){
			if(padding==PADDING.LEFT){
				value = StringUtils.leftPad(value, paddingSize, paddingChar);
			}else{
				value = StringUtils.rightPad(value, paddingSize, paddingChar);
			}
		}
		
		ITypeConverter<T> typeConverter = TypeConvertUtils
			.lookupByType(targetClass, getLocale());
		
		return typeConverter.convert(value, targetClass);
	}
	
	public static void main(String[] args)throws Exception{
		IncrementDataGenerator<String> i1 = new IncrementDataGenerator<String>();
		i1.setPadding(IncrementDataGenerator.PADDING.LEFT);
		i1.setInitialValue(3);
		i1.setIncrement(5);
		System.out.println(i1.getValue(String.class));
		
		IncrementDataGenerator<Double> i2 = new IncrementDataGenerator<Double>();
		System.out.println(i2.getValue(Double.class));
		
		IncrementDataGenerator<BigDecimal> i3 = new IncrementDataGenerator<BigDecimal>();
		System.out.println(i3.getValue(BigDecimal.class));
		
	}
	
	
}
