package com.github.azbh111.jdbclogger.wrappers;

import java.sql.SQLException;

/**
 * @author: zyp
 * @since: 2021/11/1 16:00
 */
public interface SqlCallable<T> {
    T call() throws SQLException;
}
