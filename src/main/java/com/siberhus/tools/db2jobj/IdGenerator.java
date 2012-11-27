package com.siberhus.tools.db2jobj;

public class IdGenerator {
	
	private String catalog = null;
	private String schema = null;
	
	private int initialValue = 1;
	private int allocationSize = 1;
	
	public IdGenerator(){}
	
	public IdGenerator(int initialValue, int allocationSize) {
		this.initialValue = initialValue;
		this.allocationSize = allocationSize;
	}
	
	public String getName(String tableName) {
		if (Character.isLowerCase(tableName.charAt(0))) {
			return tableName+"_gen";
		} else {
			return tableName+"_GEN";
		}
	}
	
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public int getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}

	public int getAllocationSize() {
		return allocationSize;
	}

	public void setAllocationSize(int allocationSize) {
		this.allocationSize = allocationSize;
	}
}
