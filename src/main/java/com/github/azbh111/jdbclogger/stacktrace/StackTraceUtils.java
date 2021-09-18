package com.github.azbh111.jdbclogger.stacktrace;

import sun.reflect.Reflection;

/**
 * @author pyz
 * @date 2019/4/11 10:06 AM
 */
public class StackTraceUtils {

    /**
     * 在A方法中调用B方法
     * 在B方法中调用StackTraceUtils.getCallerClass() 返回A方法所属的类
     */
    public static Class getCallerClass() {
        return getCallerClass(1);
    }

    /**
     * 在A方法中调用B方法
     * 在B方法中调用StackTraceUtils.getCallerClass() 返回B方法所属的类
     */
    public static Class getCurrentClass() {
        return getCallerClass(0);
    }

    /**
     * 在A方法中调用B方法
     * 在B方法中调用StackTraceUtils.getCallerClass(n)
     * n=1 返回A方法所属的类
     * n=0 返回B方法所属的类
     *
     * @param n
     * @return
     */
    public static Class getCallerClass(int n) {
        return Reflection.getCallerClass(3 + n);
    }

}
