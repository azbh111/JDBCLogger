package com.github.azbh111.jdbclogger.wrappers;

import com.github.azbh111.jdbclogger.LogHelper;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class PreparedStatementWrapper extends StatementWrapper implements PreparedStatement {

    public PreparedStatement pstmt;

    public PreparedStatementWrapper(ConnectionWrapper con, PreparedStatement prepareStatement, String sql) {
        super(con, prepareStatement, sql);
        this.pstmt = prepareStatement;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        long start = System.currentTimeMillis();

        ResultSet result = this.pstmt.executeQuery();

        long stop = System.currentTimeMillis();
        long duration = stop - start;

        LogHelper.sqllog(String.format("cost %s, %s", duration, queries));
        
        return result;
    }

    @Override
    public int executeUpdate() throws SQLException {

        long start = System.currentTimeMillis();

        Integer result = this.pstmt.executeUpdate();

        long stop = System.currentTimeMillis();
        long duration = stop - start;
        LogHelper.sqllog(String.format("cost %s, %s", duration, queries));

        return result;
    }

    @Override
    public boolean execute() throws SQLException {

        long start = System.currentTimeMillis();

        Boolean result = this.pstmt.execute();

        long stop = System.currentTimeMillis();
        long duration = stop - start;
        LogHelper.sqllog(String.format("cost %s, %s", duration, queries));

        return result;
    }

    // Batch
    @Override
    public void addBatch() throws SQLException {
        QueryWrapper query = new QueryWrapper(getSQL().query);
        queries.add(query);
        index++;
        pstmt.addBatch();
    }

    @Override
    public void clearParameters() throws SQLException {
        queries = new ArrayList<QueryWrapper>();
        pstmt.clearParameters();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        getSQL().setNull(parameterIndex, sqlType);
        pstmt.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        getSQL().setBoolean(parameterIndex, x);
        pstmt.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        getSQL().setByte(parameterIndex, x);
        pstmt.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        getSQL().setShort(parameterIndex, x);
        pstmt.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        getSQL().setInt(parameterIndex, x);
        pstmt.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        getSQL().setLong(parameterIndex, x);
        pstmt.setLong(parameterIndex, x);

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        getSQL().setFloat(parameterIndex, x);
        pstmt.setFloat(parameterIndex, x);

    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        getSQL().setDouble(parameterIndex, x);
        pstmt.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        getSQL().setBigDecimal(parameterIndex, x);
        pstmt.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        getSQL().setString(parameterIndex, x);
        pstmt.setString(parameterIndex, x);

    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        getSQL().setBytes(parameterIndex, x);
        pstmt.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        getSQL().setDate(parameterIndex, x);
        pstmt.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        getSQL().setTime(parameterIndex, x);
        pstmt.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        getSQL().setTimestamp(parameterIndex, x);
        pstmt.setTimestamp(parameterIndex, x);

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getSQL().setAsciiStream(parameterIndex, x, length);
        pstmt.setAsciiStream(parameterIndex, x, length);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getSQL().setUnicodeStream(parameterIndex, x, length);
        pstmt.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getSQL().setBinaryStream(parameterIndex, x, length);
        pstmt.setBinaryStream(parameterIndex, x, length);

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        getSQL().setObject(parameterIndex, x, targetSqlType);
        pstmt.setObject(parameterIndex, x, targetSqlType);

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        getSQL().setObject(parameterIndex, x);
        pstmt.setObject(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        getSQL().setCharacterStream(parameterIndex, reader, length);
        pstmt.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        getSQL().setRef(parameterIndex, x);
        pstmt.setRef(parameterIndex, x);

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        getSQL().setBlob(parameterIndex, x);
        pstmt.setBlob(parameterIndex, x);

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        getSQL().setClob(parameterIndex, x);
        pstmt.setClob(parameterIndex, x);

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        getSQL().setArray(parameterIndex, x);
        pstmt.setArray(parameterIndex, x);

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return pstmt.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        getSQL().setDate(parameterIndex, x, cal);
        pstmt.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        getSQL().setTime(parameterIndex, x, cal);
        pstmt.setTime(parameterIndex, x, cal);

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        getSQL().setTimestamp(parameterIndex, x, cal);
        pstmt.setTimestamp(parameterIndex, x, cal);

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        getSQL().setNull(parameterIndex, sqlType, typeName);
        pstmt.setNull(parameterIndex, sqlType, typeName);

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        getSQL().setURL(parameterIndex, x);
        pstmt.setURL(parameterIndex, x);

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return pstmt.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        getSQL().setRowId(parameterIndex, x);
        pstmt.setRowId(parameterIndex, x);

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        getSQL().setNString(parameterIndex, value);
        pstmt.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        getSQL().setNCharacterStream(parameterIndex, value, length);
        pstmt.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        getSQL().setNClob(parameterIndex, value);
        pstmt.setNClob(parameterIndex, value);

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getSQL().setClob(parameterIndex, reader, length);
        pstmt.setClob(parameterIndex, reader, length);

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        getSQL().setBlob(parameterIndex, inputStream, length);
        pstmt.setBlob(parameterIndex, inputStream, length);

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getSQL().setNClob(parameterIndex, reader, length);
        pstmt.setNClob(parameterIndex, reader, length);

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        getSQL().setSQLXML(parameterIndex, xmlObject);
        pstmt.setSQLXML(parameterIndex, xmlObject);

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        pstmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength);

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        pstmt.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        pstmt.setBinaryStream(parameterIndex, x, length);

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        pstmt.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        pstmt.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        pstmt.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        pstmt.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        pstmt.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        pstmt.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        pstmt.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        pstmt.setNClob(parameterIndex, reader);
    }

    @Override
    public void close() throws SQLException {
        pstmt.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return pstmt.isClosed();
    }

}
