package com.github.azbh111.jdbclogger.stacktrace.model;

/**
 * @author pyz
 * @date 2019/6/13 8:03 PM
 */
public class StackTraceInfo {
    /**
     * 所在的类
     */
    private String fileName = "?";
    private int lineNumer = -1;
    /**
     * 类中的方法
     */
    private String methodName = "?";
    /**
     * 调用的方法
     */
    private String invokeMethodName = "?";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumer() {
        return lineNumer;
    }

    public void setLineNumer(int lineNumer) {
        this.lineNumer = lineNumer;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInvokeMethodName() {
        return invokeMethodName;
    }

    public void setInvokeMethodName(String invokeMethodName) {
        this.invokeMethodName = invokeMethodName;
    }

    @Override
    public String toString() {
        return fileName + ":" + methodName + ":" + invokeMethodName + ":" + lineNumer;
    }
}
