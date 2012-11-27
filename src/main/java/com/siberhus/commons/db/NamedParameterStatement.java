package com.siberhus.commons.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class wraps around a {@link PreparedStatement} and allows the programmer
 * to set parameters by name instead of by index. This eliminates any confusion
 * as to which parameter index represents what. This also means that rearranging
 * the SQL statement or adding a parameter doesn't involve renumbering your
 * indices. Code such as this:
 * 
 * 
 * Connection con=getConnection(); String query="select * from my_table where
 * name=? or address=?"; PreparedStatement p=con.prepareStatement(query);
 * p.setString(1, "bob"); p.setString(2, "123 terrace ct"); ResultSet
 * rs=p.executeQuery();
 * 
 * 
 * can be replaced with:
 * 
 * 
 * Connection con=getConnection(); String query="select * from my_table where
 * name=:name or address=:address"; NamedParameterStatement p=new
 * NamedParameterStatement(con, query); p.setString("name", "bob");
 * p.setString("address", "123 terrace ct"); ResultSet rs=p.executeQuery();
 * 
 * 
 * @author adam_crume
 * @author siberhus
 */
public class NamedParameterStatement implements Statement{
	
	
	/** The statement this object is wrapping. */
	private final PreparedStatement statement;
	
	private final Logger logger = LoggerFactory.getLogger(NamedParameterStatement.class);
	
	/**
	 * Maps parameter names to arrays of ints which are the parameter indices.
	 */
	private final Map indexMap;

	private final List paramList;
	/**
	 * Creates a NamedParameterStatement. Wraps a call to c.{@link Connection#prepareStatement(java.lang.String) 
	 * prepareStatement}.
	 * 
	 * @param connection
	 *            the database connection
	 * @param query
	 *            the parameterized query
	 * @throws SQLException
	 *             if the statement could not be created
	 */
	public NamedParameterStatement(Connection connection, String query)
			throws SQLException {
		indexMap = new HashMap();
		paramList = new ArrayList();
		String parsedQuery = parse(query, indexMap,paramList);
		statement = connection.prepareStatement(parsedQuery);
	}

	public Set<String> getParameterNames(){
		
		return indexMap.keySet();
	}
	
	public List<String> getParameterList(){
		
		return paramList;
	}
	
	/**
	 * Parses a query with named parameters. The parameter-index mappings are
	 * put into the map, and the parsed query is returned. DO NOT CALL FROM
	 * CLIENT CODE. This method is non-private so JUnit code can test it.
	 * 
	 * @param query
	 *            query to parse
	 * @param paramMap
	 *            map to hold parameter-index mappings
	 * @return the parsed query
	 */
	static final String parse(String query, Map paramMap,List paramList) {
		// I was originally using regular expressions, but they didn't work well
		// for ignoring
		// parameter-like strings inside quotes.
		int length = query.length();
		StringBuffer parsedQuery = new StringBuffer(length);
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		int index = 1;

		for (int i = 0; i < length; i++) {
			char c = query.charAt(i);
			if (inSingleQuote) {
				if (c == '\'') {
					inSingleQuote = false;
				}
			} else if (inDoubleQuote) {
				if (c == '"') {
					inDoubleQuote = false;
				}
			} else {
				if (c == '\'') {
					inSingleQuote = true;
				} else if (c == '"') {
					inDoubleQuote = true;
				} else if (c == ':' && i + 1 < length
						&& Character.isJavaIdentifierStart(query.charAt(i + 1))) {
					int j = i + 2;
					while (j < length
							&& Character.isJavaIdentifierPart(query.charAt(j))) {
						j++;
					}
					String name = query.substring(i + 1, j);
					c = '?'; // replace the parameter with a question mark
					i += name.length(); // skip past the end if the parameter

					List indexList = (List) paramMap.get(name);
					if (indexList == null) {
						indexList = new LinkedList();
						paramMap.put(name, indexList);
					}
					paramList.add(name);
					indexList.add(new Integer(index));

					index++;
				}
			}
			parsedQuery.append(c);
		}

		// replace the lists of Integer objects with arrays of ints
		for (Iterator itr = paramMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			List list = (List) entry.getValue();
			int[] indexes = new int[list.size()];
			int i = 0;
			for (Iterator itr2 = list.iterator(); itr2.hasNext();) {
				Integer x = (Integer) itr2.next();
				indexes[i++] = x.intValue();
			}
			entry.setValue(indexes);
		}

		return parsedQuery.toString();
	}

	/**
	 * Returns the indexes for a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @return parameter indexes
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 */
	private int[] getIndexes(String name) {
		int[] indexes = (int[]) indexMap.get(name);
		if (indexes == null) {
			throw new IllegalArgumentException("Parameter not found: " + name);
		}
		return indexes;
	}
	
	
	public void setNull(String name, int sqlType) throws SQLException{
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setNull(indexes[i], sqlType);
			logger.debug("Set value:null to column: {} at indexes[{}]"
					,new Object[]{name,indexes[i]});
		}
	}
	
	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setObject(int, java.lang.Object)
	 */
	public void setObject(String name, Object value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setObject(indexes[i], value);
			logger.debug("Set value: {} to column: {} at indexes[{}]"
					,new Object[]{value,name,indexes[i]});
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setString(int, java.lang.String)
	 */
	public void setString(String name, String value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setString(indexes[i], value);
			logger.debug("Set value: {} to column: {} at indexes[{}]"
					,new Object[]{value,name,indexes[i]});
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, int)
	 */
	public void setInt(String name, int value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setInt(indexes[i], value);
			logger.debug("Set value: {} to column: {} at indexes[{}]"
					,new Object[]{value,name,indexes[i]});
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, int)
	 */
	public void setLong(String name, long value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setLong(indexes[i], value);
			logger.debug("Set value: {} to column: {} at indexes[{}]"
					,new Object[]{value,name,indexes[i]});
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
	 */
	public void setTimestamp(String name, Timestamp value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setTimestamp(indexes[i], value);
			logger.debug("Set value: {} to column: {} at indexes[{}]"
					,new Object[]{value,name,indexes[i]});
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @throws SQLException
	 */
	public void setDate(String name, Date value) throws SQLException {
		int[] indexes = getIndexes(name);
		for(int i=0; i< indexes.length; i++){
			statement.setDate(indexes[i], value);
			logger.debug("Set value: {} to column: {} at indexes[{}]"
					,new Object[]{value,name,indexes[i]});
		}
	}
	
	/**
	 * Returns the underlying statement.
	 * 
	 * @return the statement
	 */
	public PreparedStatement getStatement() {
		return statement;
	}

	/**
	 * Executes the statement.
	 * 
	 * @return true if the first result is a {@link ResultSet}
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#execute()
	 */
	public boolean execute() throws SQLException {
		return statement.execute();
	}

	/**
	 * Executes the statement, which must be a query.
	 * 
	 * @return the query results
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#executeQuery()
	 */
	public ResultSet executeQuery() throws SQLException {
		return statement.executeQuery();
	}

	/**
	 * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
	 * statement; or an SQL statement that returns nothing, such as a DDL
	 * statement.
	 * 
	 * @return number of rows affected
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#executeUpdate()
	 */
	public int executeUpdate() throws SQLException {
		return statement.executeUpdate();
	}

	/**
	 * Closes the statement.
	 * 
	 * @throws SQLException
	 *             if an error occurred
	 * @see Statement#close()
	 */
	public void close() throws SQLException {
		statement.close();
	}

	/**
	 * Adds the current set of parameters as a batch entry.
	 * 
	 * @throws SQLException
	 *             if something went wrong
	 */
	public void addBatch() throws SQLException {
		statement.addBatch();
	}

	/**
	 * Executes all of the batched statements.
	 * 
	 * See {@link Statement#executeBatch()} for details.
	 * 
	 * @return update counts for each statement
	 * @throws SQLException
	 *             if something went wrong
	 */
	@Override
	public int[] executeBatch() throws SQLException {
		return statement.executeBatch();
	}

	/**
	 * Adds the given SQL command to the current list of commmands for this Statement object.
	 */
	@Override
	public void addBatch(String sql) throws SQLException {
		statement.addBatch(sql);
	}

	/**
	 * Cancels this Statement object if both the DBMS and driver support aborting an SQL statement.
	 */
	@Override
	public void cancel() throws SQLException {
		statement.cancel();
	}

	/**
	 * Empties this Statement object's current list of SQL commands
	 */
	@Override
	public void clearBatch() throws SQLException {
		statement.clearBatch();
	}

	/**
	 * Clears all the warnings reported on this Statement object.
	 */
	@Override
	public void clearWarnings() throws SQLException {
		statement.clearWarnings();
	}

	/**
	 * Executes the given SQL statement, which may return multiple results
	 */
	@Override
	public boolean execute(String sql) throws SQLException {
		return statement.execute(sql);
	}
	
	/**
	 * Executes the given SQL statement, which may return multiple results, 
	 * and signals the driver that any auto-generated keys should be made 
	 * available for retrieval.
	 */
	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		return statement.execute(sql, autoGeneratedKeys);
	}
	/**
	 * Executes the given SQL statement, which may return multiple results, 
	 * and signals the driver that the auto-generated keys indicated in the 
	 * given array should be made available for retrieval.
	 */
	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return statement.execute(sql, columnIndexes);
	}
	/**
	 * Executes the given SQL statement, which may return multiple results, 
	 * and signals the driver that the auto-generated keys indicated in the 
	 * given array should be made available for retrieval.
	 */
	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		return statement.execute(sql, columnNames);
	}
	
	/**
	 * Executes the given SQL statement, which returns a single ResultSet object.
	 */
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, 
	 * or DELETE statement or an SQL statement that returns nothing, such as 
	 * an SQL DDL statement.
	 */
	@Override
	public int executeUpdate(String sql) throws SQLException {
		return statement.executeUpdate(sql);
	}
	/**
	 *  Executes the given SQL statement and signals the driver with the given 
	 *  flag about whether the auto-generated keys produced by this Statement 
	 *  object should be made available for retrieval
	 */
	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		return statement.executeUpdate(sql, autoGeneratedKeys);
	}
	
	/**
	 * Executes the given SQL statement and signals the driver that the 
	 * auto-generated keys indicated in the given array should be made 
	 * available for retrieval.
	 */
	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		return statement.executeUpdate(sql, columnIndexes);
	}
	
	/**
	 *  Executes the given SQL statement and signals the driver that 
	 *  the auto-generated keys indicated in the given array should be 
	 *  made available for retrieval.
	 */
	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		return statement.executeUpdate(sql,columnNames);
	}
	
	/**
	 * Retrieves the Connection object that produced this Statement object.
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return statement.getConnection();
	}
	
	/**
	 * Retrieves the direction for fetching rows from database tables that 
	 * is the default for result sets generated from this Statement object.
	 */
	@Override
	public int getFetchDirection() throws SQLException {
		return statement.getFetchDirection();
	}

	/**
	 * Retrieves the number of result set rows that is the default fetch size 
	 * for ResultSet objects generated from this Statement object.
	 */
	@Override
	public int getFetchSize() throws SQLException {
		return statement.getFetchSize();
	}

	/**
	 * Retrieves any auto-generated keys created as a result of executing 
	 * this Statement object. If this Statement object did not generate any keys, 
	 * an empty ResultSet object is returned
	 */
	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return statement.getGeneratedKeys();
	}

	/**
	 * 
	 */
	@Override
	public int getMaxFieldSize() throws SQLException {
		return statement.getMaxFieldSize();
	}

	@Override
	public int getMaxRows() throws SQLException {
		return statement.getMaxRows();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return statement.getMoreResults();
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		return statement.getMoreResults(arg0);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return statement.getQueryTimeout();
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return statement.getResultSet();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return statement.getResultSetConcurrency();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return statement.getResultSetHoldability();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return statement.getResultSetType();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return statement.getUpdateCount();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return statement.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return statement.isClosed();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return statement.isPoolable();
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		statement.setCursorName(arg0);
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		statement.setEscapeProcessing(arg0);
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		statement.setFetchDirection(arg0);
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		statement.setFetchSize(arg0);
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		statement.setMaxFieldSize(arg0);
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		statement.setMaxRows(arg0);
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		statement.setPoolable(arg0);
		
	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		statement.setQueryTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return statement.isWrapperFor(arg0);
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return statement.unwrap(arg0);
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		statement.closeOnCompletion();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return statement.isCloseOnCompletion();
	}
	
}
