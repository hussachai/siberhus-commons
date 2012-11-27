package com.siberhus.tools.db2jobj;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.sql.DataSource;


public class TableNamesLister {

	
	private DataSource dataSource;
	
	public TableNamesLister(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	
	
	public void listAllTableNames(String scheme)throws Exception{
		
		Connection conn = dataSource.getConnection();
		
		DatabaseMetaData dbmd = conn.getMetaData();
		// Specify the type of object; in this case we want tables
        String[] types = {"TABLE"};
        ResultSet resultSet = dbmd.getTables(null, null, "%", types);
    
        ResultSetMetaData rsmd2 = resultSet.getMetaData();
        for(int i=1;i<=rsmd2.getColumnCount();i++){
        	System.out.println(rsmd2.getColumnName(i));
        }
        System.out.println("==========================");
        
        // Get the table names
        while (resultSet.next()) {
            // Get the table name
            String tableName2 = resultSet.getString("TABLE_NAME");
            System.out.println(tableName2+","
            		+resultSet.getString("TABLE_TYPE")
            		+","+resultSet.getString("TABLE_SCHEM"));
            
            // Get the table's catalog and schema names (if any)
            String tableCatalog = resultSet.getString(1);
            String tableSchema = resultSet.getString(2);
        }
	}
	
	
	public static void main(String[] args)throws Exception {
//		TableNamesLister tnl = new TableNamesLister(LocalResource.getSharedDataSource());
//		tnl.listAllTableNames("SCG");
	}
}
