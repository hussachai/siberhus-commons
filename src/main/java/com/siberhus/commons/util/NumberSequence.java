package com.siberhus.commons.util;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * This class do the same work like database sequence 
 * but it has some more feature.
 * 
 * Warning: This class is not thread safe
 * 
 * @author Hussachai
 *
 */
public class NumberSequence {
	
	private int paddingSize = 4;
	
	private Long historySequence = new Long(0);
	private boolean rememberHistory = false;
	private Long historySize = new Long(30);
	private Map<Long, Long> historyMap = new HashMap<Long,Long>();
	
	private Long lastValue = null;
	private Long increment = new Long(1);
	private Long sequence = new Long(1);
	private Long maxValue = Long.MAX_VALUE;
	private Long minValue = new Long(1);
	
	public NumberSequence(){}
	
	public NumberSequence(int paddingSize){
		this.paddingSize = paddingSize;
	}
	
	
	public NumberSequence setIncrement(Long increment){
		this.increment = increment;
		return this;
	}
	
	public Long getIncrement(){
		return increment;
	}
	
	public int getPaddingSize() {
		return paddingSize;
	}

	public void setPaddingSize(int paddingSize) {
		this.paddingSize = paddingSize;
	}
	
	public Long getLastValue() {
		return lastValue;
	}

	public void setLastValue(Long lastValue) {
		checkConstraint(lastValue);
		this.lastValue = lastValue;
		this.sequence = lastValue;
	}

	public Long getMaxValue() {
		return maxValue;
	}
	
	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	

	public Long getHistorySize() {
		return historySize;
	}

	public void setHistorySize(Long historySize) {
		this.historySize = historySize;
	}
	
	public boolean isRememberHistory() {
		return rememberHistory;
	}

	public void setRememberHistory(boolean rememberHistory) {
		this.rememberHistory = rememberHistory;
	}
	
	public long nextValue(){
		incrementValue();
		return sequence.longValue();
	}
	
	public String nextValueString(){
		incrementValue();
		return getPaddingValue(sequence);
	}
	
	public Long getValue(Long number){
		return historyMap.get(number);
	}
	
	public String getValueString(Long number){
		Long value = historyMap.get(number);
		if(value!=null){
			return getPaddingValue(value);
		}
		return null;
	}
	
	public void reset(){
		this.sequence = minValue;
		historySequence = new Long(0);
		historyMap.clear();
	}
	
	private String getPaddingValue(Long value){
		
		return StringUtils.leftPad(value.toString(), paddingSize,'0');
	}
	
	private void incrementValue(){
		sequence += increment;
		lastValue = sequence;
		if(rememberHistory){
			if(historyMap.size()>=historySize){
				historyMap.clear();
//				historySequence = new Long(0);
			}else{
				historySequence++;
				historyMap.put(historySequence, lastValue);
			}
		}
	}
	
	private void checkConstraint(Long value){
		if(value<minValue || value>maxValue){
			throw new RuntimeException(MessageFormat
					.format("value({0}) is less than minValue{1} or" +
							" is greater than maxValue{2}"
					,value,minValue,maxValue));
		}
	}
	
	public static void main(String[] args) {
		NumberSequence ns = new NumberSequence(4);
		ns.setRememberHistory(true);
		System.out.println(ns.nextValueString());
		System.out.println(ns.nextValueString());
		System.out.println(ns.getValueString(1L));
		System.out.println(ns.getValueString(2L));
	}
}
