package com.github.azbh111.jdbclogger;

/**
 * @author: zyp
 * @since: 2021/9/18 13:11
 */
public class LogHelper {
    private static Logger log = (str) -> System.out.println(str);

    public static void setLogger(Logger log) {
        LogHelper.log = log;
    }

    public static void log(String str) {
        log.log(str);
    }
}
