package com.siberhus.commons.util;

import java.io.Serializable;

public class KeyValue<K,V> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private K key;
	private V value;
	
	public KeyValue(){}
	
	public KeyValue(K key, V value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return value!=null? value.toString(): "";
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	
}
