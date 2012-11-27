package com.siberhus.tools.db2jobj;

public class SequenceIdGenerator extends IdGenerator{
	
	public String getSequenceName(String tableName) {
		if (Character.isLowerCase(tableName.charAt(0))) {
			return "seq_" + tableName;
		} else {
			return "SEQ_" + tableName;
		}
	}
	

}