package com.siberhus.commons.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 
 * @author hussachai
 * 
 */
public class ThreadLocalDataSource implements DataSource {

	private static ThreadLocal<ConnectionWrapper> connections = new ThreadLocal<ConnectionWrapper>();

	private PrintWriter logWriter;
	private String driverClassName;
	private String username;
	private String password;
	private String url;
	private Properties connectionProperties = new Properties();

	public ThreadLocalDataSource() {
	}

	public synchronized void initialize() {
		
		loadDriverClass();
		
		if(getLogWriter()==null){
			setLogWriter(new PrintWriter(System.out));
		}
		
		if (getUsername() != null) {
			getConnectionProperties().setProperty("user", getUsername());
		}
		if (getPassword() != null) {
			getConnectionProperties().setProperty("password", getPassword());
		}
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Properties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(Properties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {

		return getConnection(null, null);
	}
	
	@Override
	public synchronized Connection getConnection(String user, String password)
			throws SQLException {

		ConnectionWrapper connWrapper = connections.get();
		if (connWrapper == null || connWrapper.isClosed()) {
			Connection conn = createConnection(user, password);
			connWrapper = new ConnectionWrapper(conn) {
				@Override
				public void close() throws SQLException {
				}

				@Override
				protected void finalize() throws Throwable {
					log("Connection: " + getWrappedConnection()
							+ " are closing");
					getWrappedConnection().close();
				}
			};
			connections.set(connWrapper);
		}
		return connWrapper;
	}

	@Override
	public PrintWriter getLogWriter() {
		return logWriter;
	}

	@Override
	public int getLoginTimeout() {
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter out) {
		this.logWriter = out;
	}

	@Override
	public void setLoginTimeout(int seconds) {
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	protected void log(String message) {
		System.out.println(message);
	}

	protected synchronized void loadDriverClass() {
		// Load the JDBC driver class
		if (getDriverClassName() != null) {
			try {
				Class.forName(getDriverClassName());
			} catch (Throwable t) {
				String message = "Cannot load JDBC driver class '"
						+ driverClassName + "'";
				log(message);
			}
		}
	}

	protected synchronized Connection createConnection(String user, String password)
			throws SQLException {
		Connection conn = null;
		if (user != null) {
			conn = DriverManager.getConnection(getUrl(), user, password);
		} else if (getConnectionProperties() != null) {
			conn = DriverManager.getConnection(getUrl(),
					getConnectionProperties());
		} else {
			throw new IllegalArgumentException("user cannot be null");
		}
		return conn;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getGlobal();
	}
	
}
