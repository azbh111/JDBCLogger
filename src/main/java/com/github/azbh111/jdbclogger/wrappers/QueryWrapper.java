package com.github.azbh111.jdbclogger.wrappers;

import com.github.azbh111.jdbclogger.SqlLog;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

/**
 * This class represents a SQL that is going to be executed by a JDBC Statement.
 *
 * @author Hooman Kamran
 */
public class QueryWrapper {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("'yyyy-MM-dd HH:mm:ss'");
    public String vars[];
    public String query;

    public QueryWrapper(String[] vars, String query) {
        this.vars = vars;
        this.query = query;
    }

    public QueryWrapper(String query) {
        int countVars = countMatches(query, "?");
        vars = new String[countVars];
        this.query = query.replaceAll("\\?", "%s");
        this.query = this.query.toUpperCase();
    }

    public QueryWrapper copyNew() {
        String[] vars = new String[this.vars.length];
        return new QueryWrapper(vars, query);
    }

    public void clearParameters() {
        vars = new String[vars.length];
    }

    public void setString(int i, String x) {
        vars[i - 1] = toExecutableString(x);
    }

    public void setNull(int i, int type) {
        vars[i - 1] = "null";
    }

    @Override
    public String toString() {
        try {
            return String.format(query, this.vars).replaceAll("\n", " ");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            SqlLog.log("convert QueryWrapper to String fail.\n" + sw.toString());
            return query + " " + vars.length;
        }
    }

    public void setBoolean(int i, boolean x) {
        vars[i - 1] = x ? "1" : "0";
    }

    public void setByte(int i, byte x) {
        vars[i - 1] = String.valueOf(x);
    }

    public void setShort(int i, short x) {
        vars[i - 1] = String.valueOf(x);
    }

    public void setInt(int i, int x) {
        vars[i - 1] = String.valueOf(x);
    }

    public void setLong(int i, long x) {
        vars[i - 1] = String.valueOf(x);
    }

    public void setFloat(int i, float x) {
        vars[i - 1] = String.valueOf(x);
    }

    public void setDouble(int i, double x) {
        vars[i - 1] = String.valueOf(x);
    }

    public void setBigDecimal(int i, BigDecimal x) {
        vars[i - 1] = toExecutableString(x);
    }

    public void setBytes(int i, byte[] x) {
        vars[i - 1] = "[bytes]";
    }

    public void setDate(int i, Date x) {
        vars[i - 1] = x == null ? "null" : formatter.format(x);
    }

    public void setTime(int i, Time x) {
        vars[i - 1] = x == null ? "null" : formatter.format(x);
    }


    public void setAsciiStream(int i, InputStream x, long length) {
        vars[i - 1] = "[input stream]";
    }

    public void setAsciiStream(int i, InputStream x) {
        vars[i - 1] = "[input stream]";
    }

    public void setTimestamp(int i, Timestamp x) {
        vars[i - 1] = x == null ? "null" : formatter.format(x);
    }

    public void setUnicodeStream(int i, InputStream x, int length) {
        vars[i - 1] = "[inputStream]";
    }

    public void setBinaryStream(int i, InputStream x, long length) {
        vars[i - 1] = "[inputStream]";
    }

    public void setBinaryStream(int i, InputStream x) {
        vars[i - 1] = "[inputStream]";
    }

    public void setObject(int i, Object x, int targetSqlType) {
        vars[i - 1] = toExecutableString(x);
    }

    public void setObject(int i, Object x) {
        vars[i - 1] = toExecutableString(x);
    }

    public void setCharacterStream(int i, Reader reader, long length) {
        vars[i - 1] = "[reader]";
    }

    public void setCharacterStream(int i, Reader reader) {
        vars[i - 1] = "[reader]";
    }

    public void setRef(int i, Ref x) {
        vars[i - 1] = "[ref]";
    }

    public void setBlob(int i, Blob x) {
        vars[i - 1] = "[Blob]";
    }

    public void setBlob(int i, InputStream x) {
        vars[i - 1] = "[Blob]";
    }

    public void setClob(int i, Reader x) {
        vars[i - 1] = "[clob]";
    }

    public void setClob(int i, Clob x) {
        vars[i - 1] = "[clob]";
    }

    public void setArray(int i, Array x) {
        vars[i - 1] = "[array]";
    }

    public void setDate(int i, Date x, Calendar cal) {
        vars[i - 1] = x == null ? "null" : formatter.format(x);
    }

    public void setTime(int i, Time x, Calendar cal) {
        vars[i - 1] = x == null ? "null" : formatter.format(x);
    }

    public void setTimestamp(int i, Timestamp x, Calendar cal) {
        vars[i - 1] = x == null ? "null" : formatter.format(x);
    }

    public void setNull(int i, int sqlType, String typeName) {
        vars[i - 1] = "null";
    }

    public void setURL(int i, URL x) {
        vars[i - 1] = toExecutableString(x);
    }

    public void setRowId(int i, RowId x) {
        vars[i - 1] = "[rowId]";
    }

    public void setNString(int i, String x) {
        vars[i - 1] = toExecutableString(x);
    }

    public void setNCharacterStream(int i, Reader value, long length) {
        vars[i - 1] = "[characterStream]";
    }

    public void setNCharacterStream(int i, Reader value) {
        vars[i - 1] = "[characterStream]";
    }

    public void setNClob(int i, NClob value) {
        vars[i - 1] = "[nclob]";
    }

    public void setNClob(int i, Reader value) {
        vars[i - 1] = "[nclob]";
    }

    public void setClob(int i, Reader reader, long length) {
        vars[i - 1] = "[clob]";
    }

    public void setBlob(int i, InputStream inputStream, long length) {
        vars[i - 1] = "[blob]";
    }

    public void setNClob(int i, Reader reader, long length) {
        vars[i - 1] = "[nclob]";
    }

    public void setSQLXML(int i, SQLXML xmlObject) {
        vars[i - 1] = "[sqlxml]";
    }

    private int countMatches(String str, String sub) {
        if (str != null && str.length() > 0) {
            int count = 0;
            for (int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String toString(List<QueryWrapper> queryWrappers) {
        if (queryWrappers.isEmpty()) {
            return "";
        }
        StringJoiner sj = new StringJoiner(";");
        for (QueryWrapper queryWrapper : queryWrappers) {
            sj.add(queryWrapper.toString());
        }
        return sj.toString();
    }

    private String toExecutableString(Object x) {
        if (x == null) {
            return "null";
        }
        return "'" + x + "'";
    }
}
