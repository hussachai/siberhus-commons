package com.siberhus.commons.xml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.siberhus.commons.converter.TypeConvertUtils;

public class DataElement {
	
	private Element domElement;
	private String name;
	private Map<String, String> attribMap = new LinkedHashMap<String, String>();
	private String value;
	
	public DataElement(){}

	public Element getDomElement() {
		return domElement;
	}
	
	public void setDomElement(Element domElement) {
		this.domElement = domElement;
		String value = null;
		setName(domElement.getTagName());
		
		NamedNodeMap attributes = domElement.getAttributes();
		
		Node valueAttribNode = attributes.getNamedItem("value");
		if (valueAttribNode!=null) {
			value = valueAttribNode.getNodeValue();
			value = StringUtils.trim(value);
		} else {
			value = domElement.getTextContent();
			value = StringUtils.trimToNull(value);
		}
		
		setValue(value);
		
		if(attributes.getLength()!=0){
			for(int i=0;i<attributes.getLength();i++){
				Node namedNode = attributes.item(i);
				getAttribMap().put(namedNode.getNodeName()
						,namedNode.getNodeValue());
			}
		}
	}
	
	public boolean exists(){
		if(domElement!=null){
			return true;
		}
		return false;
	}
	
	public DataElement getParentElement(){
		if(!exists()){
			return null;
		}
		
		DataElement dataElement = new DataElement();
		Element parentElement = (Element)domElement.getParentNode();
		dataElement.setDomElement(parentElement);
		return dataElement;
	}
	
	public List<DataElement> getChildElements(){
		if(!exists()){
			return null;
		}
		
		NodeList nodeList = domElement.getChildNodes();
		if(nodeList==null) return null;
		List<DataElement> dataElementList = new ArrayList<DataElement>();
		for(int i=0;i<nodeList.getLength();i++){
			Node childNode = nodeList.item(i);
			if(childNode instanceof Element){
				DataElement dataElement = new DataElement();
				dataElement.setDomElement((Element)childNode);
				dataElementList.add(dataElement);
			}
		}
		return dataElementList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getAttribMap() {
		return attribMap;
	}
	
	public void setAttribMap(Map<String, String> attribMap) {
		this.attribMap = attribMap;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getValue(String defaultValue){
		if(value==null){
			return defaultValue;
		}
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public <T>T getValue(Class<T> clazz){
		return TypeConvertUtils.convert(getValue(),clazz);
	}
	
	public <T>T getValue(Class<T> clazz, T defaultValue){
		if(!exists()){
			return defaultValue;
		}
		return TypeConvertUtils.convert(getValue(), clazz, defaultValue);
	}
	
	public boolean hasAttribute(String name){
		return getAttribMap().containsKey(name);
	}
	
	public String getAttribute(String name){
		return getAttribMap().get(name);
	}
	
	public String getAttribute(String name, String defaultValue){
		String value = getAttribute(name);
		if(value==null){
			return defaultValue;
		}
		return value;
	}
	
	public <T>T getAttribute(Class<T> clazz, String name){
		String value = getAttribute(name);
		return TypeConvertUtils.convert(value, clazz);
	}
	
	public <T>T getAttribute(Class<T> clazz, String name,T defaultValue){
		if(!exists()){
			return defaultValue;
		}
		String value = getAttribute(name);
		return TypeConvertUtils.convert(value, clazz,defaultValue);
	}
	
}
