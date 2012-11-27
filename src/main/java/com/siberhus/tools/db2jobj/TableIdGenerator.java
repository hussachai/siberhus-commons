package com.siberhus.tools.db2jobj;

public class TableIdGenerator extends IdGenerator {

	private String table = "";
	private String pkColumnName = "";
	private String valueColumnName = "";
	
	public String getPkColumnValue(String tableName) {
		if (Character.isLowerCase(tableName.charAt(0))) {
			return tableName+"_id";
		} else {
			return tableName+"_ID";
		}
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPkColumnName() {
		return pkColumnName;
	}

	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}

	public String getValueColumnName() {
		return valueColumnName;
	}

	public void setValueColumnName(String valueColumnName) {
		this.valueColumnName = valueColumnName;
	}
	
	
}
