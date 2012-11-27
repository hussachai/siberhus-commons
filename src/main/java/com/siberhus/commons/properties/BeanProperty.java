package com.siberhus.commons.properties;

import java.lang.reflect.Method;

public class BeanProperty {
	
	private String name;
	private Class type;
	private Method method;
	
	public BeanProperty(){}
	
	public BeanProperty(String name, Class type, Method method) {
		this.name = name;
		this.type = type;
		this.method = method;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
}