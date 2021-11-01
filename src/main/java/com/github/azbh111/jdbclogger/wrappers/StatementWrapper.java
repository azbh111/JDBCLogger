package com.github.azbh111.jdbclogger.wrappers;


import com.github.azbh111.jdbclogger.SqlLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a wrapper on the Statement class.
 *
 * @author Hooman Kamran
 */
public class StatementWrapper implements Statement {

    public Statement stmt;
    public ConnectionWrapper con;
    public List<QueryWrapper> queries = new ArrayList<QueryWrapper>();

    public Integer index = 0;

    public StatementWrapper(ConnectionWrapper con, Statement createStatement) {
        this.stmt = createStatement;
        this.con = con;
    }

    public StatementWrapper(ConnectionWrapper con, Statement createStatement, String sql) {
        this.stmt = createStatement;
        this.con = con;
        QueryWrapper query = new QueryWrapper(sql);
        this.queries.add(index, query);
    }

    public QueryWrapper getSQL() {
        return queries.get(index);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.stmt.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return stmt.isWrapperFor(iface);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.executeQuery(sql));

    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.executeUpdate(sql));
    }

    @Override
    public void close() throws SQLException {
        stmt.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return stmt.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        stmt.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return stmt.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        stmt.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        stmt.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return stmt.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        stmt.setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        stmt.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return stmt.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        stmt.clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        stmt.setCursorName(name);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.execute(sql));
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return stmt.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return stmt.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return stmt.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        stmt.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return stmt.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        stmt.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return stmt.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return stmt.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return stmt.getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        QueryWrapper query = new QueryWrapper(sql);
        queries.add(query);
        stmt.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        queries = new ArrayList<QueryWrapper>();
        stmt.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return SqlLog.wrapExecute(queries, () -> this.stmt.executeBatch());
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.con;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return stmt.getMoreResults(current);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return stmt.getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.executeUpdate(sql, autoGeneratedKeys));
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.executeUpdate(sql, columnIndexes));
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.executeUpdate(sql, columnNames));
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.execute(sql, autoGeneratedKeys));
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.execute(sql, columnIndexes));
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return SqlLog.wrapExecute(sql, () -> this.stmt.execute(sql, columnNames));
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return stmt.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return stmt.isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        stmt.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return stmt.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        stmt.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return stmt.isCloseOnCompletion();
    }

}
