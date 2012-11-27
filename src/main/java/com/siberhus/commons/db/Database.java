package com.siberhus.commons.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author hussachai
 *
 */
public abstract class Database {

	public static final String JDBC_SPY_DRIVER = "net.sf.log4jdbc.DriverSpy";
	
	public static final String JDBC_SPY_URL_PREFIX = "jdbc:log4";
	
	static{
		/* log4jdbc does not load some drivers automatically
		 * jdbc-odbc driver is the one of those.
		 */
		StringBuilder additionalDriver = new StringBuilder();
		additionalDriver.append("sun.jdbc.odbc.JdbcOdbcDriver");
		System.setProperty("log4jdbc.drivers"
				,additionalDriver.toString());
	}
	
	private Database(){}
	
	public abstract String getDriverClass();
	public abstract String getUrl(String host,int port,String dbName);
	public abstract String getUrl(String host,String dbName);
	public abstract String getUrl(String file);
	
	public boolean loadDriver(){
		try{
			Class.forName(getDriverClass());
			return true;
		}catch(Exception e){ return false;}
	}
	
	public String getSpyUrl(String host,int port,String name){
		return JDBC_SPY_URL_PREFIX+getUrl(host, port, name);
	}
	
	public String getSpyUrl(String host,String name){
		return JDBC_SPY_URL_PREFIX+getUrl(host, name);
	}
	
	public String getSpyUrl(String file){
		return JDBC_SPY_URL_PREFIX+getUrl(file);
	}
	
	public Connection getConnection(String host, int port, String dbName
			, String username, String password) throws SQLException{
		return DriverManager.getConnection(getUrl(host, port, dbName),username, password);
	}
	public Connection getConnection(String host, String dbName
			, String username, String password) throws SQLException{
		return DriverManager.getConnection(getUrl(host, dbName),username, password);
	}
	public Connection getConnection(String file, String username, String password) throws SQLException{
		return DriverManager.getConnection(getUrl(file),username, password);
	}
	
	public Connection getSpyConnection(String host, int port, String dbName
			, String username, String password) throws SQLException{
		return DriverManager.getConnection(getSpyUrl(host, port, dbName),username, password);
	}
	
	public Connection getSpyConnection(String host, String dbName
			, String username, String password) throws SQLException{
		return DriverManager.getConnection(getSpyUrl(host, dbName),username, password);
	}
	
	public Connection getSpyConnection(String file, String username, String password) throws SQLException{
		return DriverManager.getConnection(getSpyUrl(file),username, password);
	}
	
	public static final Database JDBC_ODBC = new Database(){
		@Override
		public String getDriverClass(){
			return "sun.jdbc.odbc.JdbcOdbcDriver";
		}
		@Override
		public String getUrl(String host,int port,String dbName){
			throw new UnsupportedOperationException();
		}
		@Override
		public String getUrl(String host, String dbName) {
			return "jdbc:odbc:"+dbName;
		}
		@Override
		public String getUrl(String file) {
			throw new UnsupportedOperationException();
		}
	};
	
	public static final Database JDBC_ODBC_MSACCESS = new Database(){
		@Override
		public String getDriverClass(){
			return "sun.jdbc.odbc.JdbcOdbcDriver";
		}
		@Override
		public String getUrl(String host,int port,String dbName){
			throw new UnsupportedOperationException();
		}
		@Override
		public String getUrl(String host, String dbName) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String getUrl(String file) {
			return "jdbc:odbc:;DRIVER=Microsoft Access Driver (*.mdb);FIL=MS Access;DBQ="+file;
		}
	};
	
	public static final Database JDBC_SPY = new Database(){
		@Override
		public String getDriverClass(){
			return JDBC_SPY_DRIVER;
		}
		@Override
		public String getUrl(String host,int port,String dbName){
			throw new UnsupportedOperationException();
		}
		@Override
		public String getUrl(String host, String dbName) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String getUrl(String file) {
			throw new UnsupportedOperationException();
		}
	};
	
	public static final Database MYSQL5 = new Database(){
		@Override
		public String getDriverClass(){
			return "com.mysql.jdbc.Driver";
		}
		@Override
		public String getUrl(String host,int port,String dbName){
			return "jdbc:mysql://"+host+":"+port+"/"+dbName;
		}
		@Override
		public String getUrl(String host, String dbName) {
			return getUrl(host, 3306 , dbName);
		}
		@Override
		public String getUrl(String file) {
			throw new UnsupportedOperationException();
		}
	};
	
	public static final Database ORACLE9 = new Database(){
		@Override
		public String getDriverClass(){
			return "oracle.jdbc.OracleDriver";
		}
		@Override
		public String getUrl(String host,int port,String dbName){
			return  "jdbc:oracle:thin:@"+host+":"+port+":"+dbName;
		}
		@Override
		public String getUrl(String host, String dbName) {
			return getUrl(host, 1521 , dbName);
		}
		@Override
		public String getUrl(String file) {
			throw new UnsupportedOperationException();
		}
	};
	public static final Database ORACLE10 = ORACLE9;
	public static final Database ORACLE11 = ORACLE9;
	
	
	public static final Database POSTGRESQL8 = new Database(){
		@Override
		public String getDriverClass(){
			return "org.postgresql.Driver";
		}
		@Override
		public String getUrl(String host,int port,String dbName){
			return  "jdbc:postgresql://"+host+":"+port+"/"+dbName;
		}
		@Override
		public String getUrl(String host, String dbName) {
			return getUrl(host, 5432 , dbName);
		}
		@Override
		public String getUrl(String file) {
			throw new UnsupportedOperationException();
		}
	};

	
	
	
}
