package com.siberhus.tools.db2jobj;

import java.util.LinkedHashSet;
import java.util.Set;


public class BeanOptions {
	
	public static enum Modifier {
		Default, Private, Protected, Public
	}
	
	private String parentClassName = null;
	
	private Set<String> interfaceNames = null;
	
	private boolean serializable = true;
	
	private boolean serialVersionId = true;
	
	private boolean fqTypeName = false;
	
	private Modifier fieldAccessModifier = Modifier.Private;
	
	private Modifier methodAccessModifier = Modifier.Public;
	
	private boolean generateGettersSetters = true;
	
	private boolean generateToString = true;

	
	public String getParentClassName() {
		return parentClassName;
	}

	public void setParentClassName(String parentClassName) {
		this.parentClassName = parentClassName;
	}

	public void addInterfaceName(String infName){
		if(this.interfaceNames==null){
			 interfaceNames = new LinkedHashSet<String>();
		}
		this.interfaceNames.add(infName);
	}
	
	public Set<String> getInterfaceNames() {
		return interfaceNames;
	}

	public boolean isSerializable() {
		return serializable;
	}

	public void setSerializable(boolean serializable) {
		this.serializable = serializable;
		if(serializable){
			addInterfaceName("java.io.Serializable");
		}
	}

	public boolean isSerialVersionId() {
		return serialVersionId;
	}

	public void setSerialVersionId(boolean serialVersionId) {
		this.serialVersionId = serialVersionId;
	}

	public boolean isFqTypeName() {
		return fqTypeName;
	}

	public void setFqTypeName(boolean fqTypeName) {
		this.fqTypeName = fqTypeName;
	}

	public Modifier getFieldAccessModifier() {
		return fieldAccessModifier;
	}

	public void setFieldAccessModifier(Modifier fieldAccessModifier) {
		this.fieldAccessModifier = fieldAccessModifier;
	}

	public Modifier getMethodAccessModifier() {
		return methodAccessModifier;
	}

	public void setMethodAccessModifier(Modifier methodAccessModifier) {
		this.methodAccessModifier = methodAccessModifier;
	}

	public boolean isGenerateGettersSetters() {
		return generateGettersSetters;
	}

	public void setGenerateGettersSetters(boolean generateGettersSetters) {
		this.generateGettersSetters = generateGettersSetters;
	}

	public boolean isGenerateToString() {
		return generateToString;
	}

	public void setGenerateToString(boolean generateToString) {
		this.generateToString = generateToString;
	}
	
	
}
