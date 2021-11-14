package test;

import com.github.azbh111.jdbclogger.wrappers.ConnectionWrapper;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 * @author: zyp
 * @since: 2021/11/14 16:36
 */
public class DataSourceWrapper implements DataSource {
    private DataSource dataSource;

    public DataSourceWrapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws java.sql.SQLException {
        return new ConnectionWrapper(this.dataSource.getConnection());
    }

    public Connection getConnection(String username, String password) throws java.sql.SQLException {
        return new ConnectionWrapper(this.dataSource.getConnection(username, password));
    }

    public PrintWriter getLogWriter() throws java.sql.SQLException {
        return this.dataSource.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws java.sql.SQLException {
        this.dataSource.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws java.sql.SQLException {
        this.dataSource.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws java.sql.SQLException {
        return this.dataSource.getLoginTimeout();
    }

    public Logger getParentLogger() throws java.sql.SQLFeatureNotSupportedException {
        return this.dataSource.getParentLogger();
    }

    public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
        return this.dataSource.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
        return this.dataSource.isWrapperFor(iface);
    }
}
