package com.github.azbh111.jdbclogger;

import com.github.azbh111.jdbclogger.stacktrace.StackTraceManager;
import com.github.azbh111.jdbclogger.stacktrace.model.StackTraceInfo;
import com.github.azbh111.jdbclogger.wrappers.QueryWrapper;
import com.github.azbh111.jdbclogger.wrappers.SqlCallable;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: zyp
 * @since: 2021/9/18 13:11
 */
public class SqlLog {
    private static Logger logger = (str) -> System.out.println(str);

    public static void setLogger(Logger logger) {
        SqlLog.logger = logger;
    }

    public static void sqllog(String str) {
        if (str == null || str.startsWith("/* ping */")) {
            return; // 不打印ping请求
        }
        StackTraceInfo stackTraceInfo = StackTraceManager.getInstance().getStackTraceInfo();
        logger.log(stackTraceInfo.toString() + " " + str);
    }

    public static void log(String str) {
        System.out.println(str);
    }

    public static <T> T wrapExecute(List<QueryWrapper> queryWrappers, SqlCallable<T> runnable) throws SQLException {
        long start = System.currentTimeMillis();
        try {
            return runnable.call();
        } finally {
            long stop = System.currentTimeMillis();
            long duration = stop - start;
            SqlLog.sqllog(String.format("cost %s, %s", duration, QueryWrapper.toString(queryWrappers)));
        }
    }

    public static <T> T wrapExecute(String sql, SqlCallable<T> runnable) throws SQLException {
        long start = System.currentTimeMillis();
        try {
            return runnable.call();
        } finally {
            long stop = System.currentTimeMillis();
            long duration = stop - start;
            SqlLog.sqllog(String.format("cost %s, %s", duration, sql));
        }
    }
}
