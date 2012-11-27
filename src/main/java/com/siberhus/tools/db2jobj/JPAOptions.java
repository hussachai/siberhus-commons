package com.siberhus.tools.db2jobj;

import java.util.Date;

import org.hsqldb.Types;

public class JPAOptions extends BeanOptions{

	public static enum Case {
		Lower, Upper
	}
	
	public static enum Annotation {
		Field, Method
	}
	
	public static enum IdGenerationType {
		Auto, Identity, Sequence, Table
	}
	
	private Case tableNameCase = Case.Lower;
	
	private Case columnNameCase = Case.Lower;
	
	private String idAttribName = null;//set this value if you want to override the default.
	
	private Annotation annotation = Annotation.Field;
	
	private IdGenerationType idGenerationType = IdGenerationType.Auto;
	
	private IdGenerator idGenerator;
	
	public String configureFieldName(ColumnInfo columnInfo){
		String columnName = columnInfo.getColumnName();
		if(columnName.toLowerCase().endsWith("_id")){
			columnName = columnName.substring(0, columnName.indexOf("_"));
			return Table2JavaConverter.db2java(columnName);
		}
		return columnName;
	}
	
	public String[] configureAnnotations(ColumnInfo columnInfo){
		String columnName = columnInfo.getColumnName();
		if(columnName.toLowerCase().endsWith("_id")){
			return new String[]{
				"@ManyToOne",
				"@JoinColumn(name=\""+columnName+"\", referencedColumnName=\"id\")"
			};
		}else if(Date.class.isAssignableFrom(columnInfo.getColumnClass())){
			switch(columnInfo.getColumnType()){
			case Types.DATE:return new String[]{"@Temporal(TemporalType.DATE)"};
			case Types.TIMESTAMP:return new String[]{"@Temporal(TemporalType.TIMESTAMP)"};
			case Types.TIME:return new String[]{"@Temporal(TemporalType.TIME)"};
			}
		}
		return new String[0];
	}

	public Case getTableNameCase() {
		return tableNameCase;
	}

	public void setTableNameCase(Case tableNameCase) {
		this.tableNameCase = tableNameCase;
	}

	public Case getColumnNameCase() {
		return columnNameCase;
	}

	public void setColumnNameCase(Case columnNameCase) {
		this.columnNameCase = columnNameCase;
	}
	
	public String getIdAttribName() {
		return idAttribName;
	}

	public void setIdAttribName(String idAttribName) {
		this.idAttribName = idAttribName;
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	public IdGenerationType getIdGenerationType() {
		return idGenerationType;
	}

	public void setIdGenerationType(IdGenerationType idGenerationType) {
		this.idGenerationType = idGenerationType;
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	
	public static void main(String[] args) {
		String parentClassName = "java.lang.String";
		parentClassName = parentClassName.substring(parentClassName.lastIndexOf(".")+1
				, parentClassName.length());
		System.out.println(parentClassName);
	}
}
