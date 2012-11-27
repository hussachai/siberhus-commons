package com.siberhus.tools.db2jobj;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ColumnInfo {
	
	private String schemaName;
	private String catalogName;
	
	private boolean caseSensitive;
	private boolean autoIncrement;
	private boolean nullable;
	
	private String columnName;
	private int columnType;
	private String columnTypeName;
	private String columnClassName;
	private Class<?> columnClass;
	private int columnDisplaySize;
	private String columnLabel;
	
	private int precision;
	private int scale;
	
	public ColumnInfo(){}
	
	public ColumnInfo(ResultSetMetaData rsmd, int column) throws SQLException{
		this.schemaName = rsmd.getSchemaName(column);
		this.catalogName = rsmd.getCatalogName(column);
		this.caseSensitive = rsmd.isCaseSensitive(column);
		this.autoIncrement = rsmd.isAutoIncrement(column);
		if(rsmd.isNullable(column)==ResultSetMetaData.columnNullable){
			this.nullable = true;
		}else{
			this.nullable = false;
		}
		this.columnName = rsmd.getColumnName(column);
		this.columnType = rsmd.getColumnType(column);
		this.columnTypeName = rsmd.getColumnTypeName(column);
		this.columnClassName = rsmd.getColumnClassName(column);
		this.columnDisplaySize = rsmd.getColumnDisplaySize(column);
		this.columnLabel = rsmd.getColumnLabel(column);
		
		this.precision = rsmd.getPrecision(column);
		this.scale = rsmd.getScale(column);
		
		try {
			this.columnClass = Class.forName(columnClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void printInfo(){
		System.out.println("IsCaseSensitive="+isCaseSensitive());
		System.out.println("AutoIncrement="+isAutoIncrement());
		System.out.println("Nullable="+isNullable());
		System.out.println("Schema="+getSchemaName());
		System.out.println("CatalogName="+getCatalogName());
		System.out.println("Column Name="+getColumnName());
		System.out.println("Column Type="+getColumnType());
		System.out.println("Column TypeName="+getColumnTypeName());
		System.out.println("Column Class="+getColumnClassName());
		System.out.println("Column DisplaySize="+getColumnDisplaySize());
		System.out.println("Column Label="+getColumnLabel());
		System.out.println("Precision="+getPrecision());
		System.out.println("Scale="+getScale());
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public boolean isNullable() {
		return nullable;
	}

	public String getColumnName() {
		return columnName;
	}

	public int getColumnType() {
		return columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public String getColumnClassName() {
		return columnClassName;
	}

	public Class<?> getColumnClass() {
		return columnClass;
	}

	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public int getPrecision() {
		return precision;
	}

	public int getScale() {
		return scale;
	}
	
	
	
}
