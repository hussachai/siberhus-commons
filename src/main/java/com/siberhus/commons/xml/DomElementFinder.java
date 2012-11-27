package com.siberhus.commons.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.siberhus.commons.converter.TypeConvertUtils;
import com.siberhus.commons.io.StringInputStream;

public class DomElementFinder  {
	
	private XPath xpath;
	private DocumentBuilderFactory domBuilderFactory;
	private DocumentBuilder domBuilder;
	private Document domDocument;
	private DataElement rootElement;
	
	private DomElementFinder() {}
	
	public static DomElementFinder newInstance(String xmlData) throws SAXException,
		IOException, ParserConfigurationException {
		
		InputStream inputStream = new StringInputStream(xmlData);
		return newInstance(inputStream);
		
	}
	
	public static DomElementFinder newInstance(File xmlFile) throws SAXException,
			IOException, ParserConfigurationException {

		InputStream inputStream = null;
		try{
			inputStream = new FileInputStream(xmlFile);
			return newInstance(inputStream);
		}finally{
			if(inputStream!=null){
				inputStream.close();
			}
		}
	}
	
	public static DomElementFinder newInstance(InputStream inputStream) throws SAXException,
		IOException, ParserConfigurationException {
	
		DomElementFinder def = new DomElementFinder();

		// Create a DocumentBuilderFactory
		def.domBuilderFactory = DocumentBuilderFactory.newInstance();
//		def.domBuilderFactory.setValidating(true);
		
		// Create a DocumentBuilder
		def.domBuilder = def.domBuilderFactory.newDocumentBuilder();
		
		def.xpath = XPathFactory.newInstance().newXPath();

		// Parse an XML document
		def.domDocument = def.domBuilder.parse(inputStream);
		
		// Retrieve Root Element
		DataElement dataElement = new DataElement();
		dataElement.setDomElement(def.domDocument.getDocumentElement());
		def.rootElement = dataElement;
		
		return def;
		
	}
	
	public DataElement getRootElement() {
		return rootElement;
	}

	public DataElement getElement(String xPathExp){
		return getElement(getRootElement(),xPathExp);
	}
	
	public DataElement getElement(DataElement startingCtx, String xPathExp){
		DataElement dataElement = findElement(startingCtx,xPathExp);
		if(dataElement==null){
			dataElement = new DataElement();
		}
		return dataElement;
	}
	
	public DataElement findElement(String xPathExp){
		return findElement(getRootElement(), xPathExp);
	}
	
	public DataElement findElement(DataElement startingCtx, String xPathExp){
		Element element = null;
		try {
			element = (Element) xpath.evaluate(xPathExp, startingCtx.getDomElement(),
					XPathConstants.NODE);
			
			if(element==null){
				return null;
			}
			if(element.getNodeType() != Node.ELEMENT_NODE){
				
			}
			DataElement dataElement = new DataElement();
			dataElement.setDomElement(element);
			return dataElement;
		} catch (XPathExpressionException xpee) {
			throw new RuntimeException(xpee);
		}
	}
	
	public List<DataElement> getElements(String xPathExp){
		return getElements(getRootElement(), xPathExp);
	}
	
	public List<DataElement> getElements(DataElement startingCtx,String xPathExp){
		List<DataElement> dataElementList = findElements(startingCtx,xPathExp);
		if(dataElementList==null){
			return new ArrayList<DataElement>();
		}
		return dataElementList;
	}
	
	public List<DataElement> findElements(String xPathExp){
		return findElements(getRootElement(), xPathExp);
	}
	
	public List<DataElement> findElements(DataElement startingCtx,String xPathExp){
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(xPathExp, startingCtx.getDomElement(),
					XPathConstants.NODESET);
			if(nodeList==null){
				return null;
			}
			List<DataElement> dataElementList = new ArrayList<DataElement>();
			for(int i=0;i<nodeList.getLength();i++){
				Node node = nodeList.item(i);
				if(node instanceof Element){
					DataElement dataElement = new DataElement();
					dataElement.setDomElement((Element)nodeList.item(i));
					dataElementList.add(dataElement);
				}
			}
			return dataElementList;
		} catch (XPathExpressionException xpee) {
			throw new RuntimeException(xpee);
		}
	}
	
	//=========== Very Convenient Method ======================//
	// <log vendor="" config="">
	// 		<vendor></vendor>
	//		<config></config>
	// </log>
	public String getAttributeOrElementValue(String xPathExp, String name){
		return getAttributeOrElementValue(getRootElement(), xPathExp, name);
	}
	
	public String getAttributeOrElementValue(DataElement startingCtx
			, String xPathExp, String name){
		return getAttributeOrElementValue(startingCtx, xPathExp, name, null);
	}
	
	public String getAttributeOrElementValue(DataElement startingCtx, String xPathExp
			, String name, String defaultValue){
		String value = null;
		if(startingCtx==null){
			startingCtx = getRootElement();
		}
		DataElement parentElem = findElement(startingCtx, xPathExp);
		if(parentElem!=null){
			value = parentElem.getAttribute(name, defaultValue);
			if(value!=null){
				return value;
			}
			DataElement childElem = findElement(parentElem, name);
			if(childElem!=null){
				return childElem.getValue(defaultValue);
			}
		}
		return null;
	}
	
	public <T>T getAttributeOrElementValue(Class<T> clazz
			, String xPathExp, String name){
		
		return getAttributeOrElementValue(clazz, getRootElement(), xPathExp, name);
	}
	
	public <T>T getAttributeOrElementValue(Class<T> clazz
			, String xPathExp, String name, T defaultValue){
		
		return getAttributeOrElementValue(clazz, getRootElement(), xPathExp, name, defaultValue);
	}
	
	public <T>T getAttributeOrElementValue(Class<T> clazz, DataElement startingCtx
			, String xPathExp, String name){
		
		return getAttributeOrElementValue(clazz, startingCtx, xPathExp, name, null);
	}
	
	public <T>T getAttributeOrElementValue(Class<T> clazz, DataElement startingCtx, String xPathExp
			, String name, T defaultValue){
		
		String valueStr = getAttributeOrElementValue(startingCtx, xPathExp, name);
		if(valueStr!=null){
			return TypeConvertUtils.convert(valueStr, clazz, defaultValue);
		}
		return defaultValue;
	}
	
}
