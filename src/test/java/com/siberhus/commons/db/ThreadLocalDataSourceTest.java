package com.siberhus.commons.db;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class ThreadLocalDataSourceTest extends TestCase{
	
	private ThreadLocalDataSource ds;
	
	public void setUp() throws Exception{
		ds = new ThreadLocalDataSource();
		ds.setDriverClassName("org.hsqldb.jdbcDriver");
		ds.setUrl("jdbc:hsqldb:mem:test");
		ds.setUsername("sa");
		ds.initialize();
		
		
	}
	
	public void testGetConnection() throws SQLException{
		Connection conn = ds.getConnection();
		assertNotNull(conn);
		assertFalse(conn.isClosed());
		conn.close();
		assertFalse(conn.isClosed());
		conn = ds.getConnection();
		assertFalse(conn.isClosed());
	}
	/**
	 * @throws Exception
	 */
	public void sample() throws Exception {
		setUp();
		final QueryRunner qr = new QueryRunner(ds);
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()
							+":"+ds.getConnection());
					Thread.sleep( (int)(Math.random()*10));
					//count user table
					qr.query("select count(*) from information_schema.system_tables " +
							"where table_type='TABLE'", new ScalarHandler());
					System.out.println(Thread.currentThread().getName()
							+":"+ds.getConnection());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		for(int i=0;i<10;i++){
			new Thread(runnable).start();
		}
		try{
			Thread.sleep(2000L);
			System.gc();
		}catch(Exception e){}
	}
	
	public static void main(String[] args) throws Exception{
		
		new ThreadLocalDataSourceTest().sample();
	}

}
