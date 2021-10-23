package com.github.azbh111.jdbclogger;

import java.util.logging.Logger;

/**
 * @author: zyp
 * @since: 2021/10/21 21:37
 */
public class LogHelperTest {
    private static final Logger logger = Logger.getLogger("sql");

    public static void main(String[] args) {
//        自定义日志输出逻辑
        SqlLog.setLogger(str -> logger.info(str));
    }
}