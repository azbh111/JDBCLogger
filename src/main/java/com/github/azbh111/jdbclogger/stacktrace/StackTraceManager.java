package com.github.azbh111.jdbclogger.stacktrace;


import com.github.azbh111.jdbclogger.stacktrace.model.StackTraceInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pyz
 * @date 2019/1/16 11:47 AM
 */
public class StackTraceManager {
    /**
     * 前缀匹配项目包
     */
    private final Set<String> packagePrefixs = new HashSet<>();
    /**
     * 前缀排除包
     */
    private final Set<String> packageExecludes = new HashSet<>();
    /**
     * 缓存项目包,加快判断速度
     */
    private final Map<String, LoggerStackTraceElement> projectStackTraceCache = new ConcurrentHashMap<>();

    private static final StackTraceManager instance = new StackTraceManager();

    public Set<String> getPackagePrefixs() {
        return packagePrefixs;
    }

    public Set<String> getPackageExecludes() {
        return packageExecludes;
    }

    public static StackTraceManager getInstance() {
        return instance;
    }

    {
//        quartz
        packagePrefixs.add("org.quartz.");
//      排除jdk包和连接池包
        packageExecludes.add("java.");
        packageExecludes.add("javax.");
        packageExecludes.add("sun.");
        packageExecludes.add("com.alibaba.druid.");
    }

    public StackTraceInfo getStackTraceInfo() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        int length = stackTraces.length;
        LoggerStackTraceElement preStackTrace = null;
        LoggerStackTraceElement currentStackTrace = null;
        StackTraceElement stackTrace;
        StackTraceInfo info = new StackTraceInfo();
        for (int i = 0; i < length; i++) {
            stackTrace = stackTraces[i];
            preStackTrace = currentStackTrace;
            currentStackTrace = toLoggerStackTrace(stackTrace);

            if (!currentStackTrace.isProxy() && currentStackTrace.isProjectClass()) {
                if (preStackTrace != null) {
                    info.setInvokeMethodName(stackTraces[i - 1].getMethodName());
                }
                info.setMethodName(stackTraces[i].getMethodName());
                info.setFileName(currentStackTrace.getFileName());
                info.setLineNumer(stackTrace.getLineNumber());
                return info;
            }
        }
        return info;
    }

    private LoggerStackTraceElement toLoggerStackTrace(StackTraceElement element) {
        LoggerStackTraceElement loggerStackTraceElement = projectStackTraceCache.get(element.getClassName());
        if (loggerStackTraceElement == null) {
            loggerStackTraceElement = parse(element);
            projectStackTraceCache.put(element.getClassName(), loggerStackTraceElement);
        }
        return loggerStackTraceElement;
    }

    private boolean isProjectStackTrace(StackTraceElement element) {
        if ("<generated>".equals(element.getFileName())) {
            return false;
        }
        String className = element.getClassName();
        for (String prefix : packagePrefixs) {
            A:
            if (className.startsWith(prefix)) {
                for (String execlude : packageExecludes) {
                    if (className.startsWith(execlude)) {
                        break A;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private LoggerStackTraceElement parse(StackTraceElement element) {
        LoggerStackTraceElement ele = new LoggerStackTraceElement();
        ele.setProjectClass(element.getClassName().contains("$$") || element.getClassName().startsWith("com.sun.proxy.$Proxy"));
        ele.setFileName(trimJavaSuffix(element.getFileName()));
        ele.setProjectClass(isProjectStackTrace(element));
        return ele;
    }

    private String trimJavaSuffix(String fileName) {
        if (fileName == null) {
            return "";
        }
        return fileName.endsWith(".java") ? fileName.substring(0, fileName.length() - 5) : fileName;
    }

    private static class LoggerStackTraceElement {
        /**
         * 是否是代理类
         */
        private boolean proxy;
        /**
         * 是否是项目类
         */
        private boolean projectClass;
        private String fileName;

        public boolean isProxy() {
            return proxy;
        }

        public void setProxy(boolean proxy) {
            this.proxy = proxy;
        }

        public boolean isProjectClass() {
            return projectClass;
        }

        public void setProjectClass(boolean projectClass) {
            this.projectClass = projectClass;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}
