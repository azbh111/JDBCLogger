package com.github.azbh111.jdbclogger;

import com.github.azbh111.jdbclogger.stacktrace.StackTraceManager;
import com.github.azbh111.jdbclogger.stacktrace.model.StackTraceInfo;

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
        StackTraceInfo stackTraceInfo = StackTraceManager.getInstance().getStackTraceInfo();
        logger.log(stackTraceInfo.toString() + " " + str);
    }

    public static void log(String str) {
        System.out.println(str);
    }
}
