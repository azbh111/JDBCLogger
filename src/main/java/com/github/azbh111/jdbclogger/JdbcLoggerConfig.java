package com.github.azbh111.jdbclogger;

import com.github.azbh111.jdbclogger.stacktrace.StackTraceManager;
import javassist.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author: zyp
 * @since: 2021/9/18 11:34
 */
public class JdbcLoggerConfig {
    private static Set<String> proxyDriverClassNames = new HashSet<>();
    private static StackTraceManager stackTraceManager = StackTraceManager.getInstance();

    static {
//            排除自己
        stackTraceManager.getPackageExecludes().add(JdbcLoggerConfig.class.getPackage().getName() + ".");
    }

    public static void loadConfig(ClassLoader classLoader) {
        try {
            SqlLog.log("loadConfig from classLoader: " + classLoader.getClass().getName());
            Enumeration<URL> resources = classLoader.getResources("jdbclogger.properties");
            while (resources.hasMoreElements()) {
                Properties properties = loadProperties(resources.nextElement());
                readDriverClass(properties);
                readPackagePrefixs(properties);
                readPackageExecludes(properties);
            }
        } catch (IOException | NotFoundException e) {
            SqlLog.log("加载配额制失败. " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private static Properties loadProperties(URL url) throws IOException {
        InputStream inputStream = url.openStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }

    private static void readPackageExecludes(Properties properties) throws NotFoundException {
        String packageExecludes = properties.getProperty("jdbclogger.project.packageExecludes");
        if (packageExecludes == null || packageExecludes.length() == 0) {
            return;
        }
        String[] split = packageExecludes.split(",");
        for (String s : split) {
            s = s.trim();
            SqlLog.log("add packageExecludes: " + s);
            stackTraceManager.getPackageExecludes().add(s);
        }
    }

    private static void readPackagePrefixs(Properties properties) throws NotFoundException {
        String packagePrefixs = properties.getProperty("jdbclogger.project.packagePrefixs");
        if (packagePrefixs == null || packagePrefixs.length() == 0) {
            return;
        }
        String[] split = packagePrefixs.split(",");
        for (String s : split) {
            s = s.trim();
            SqlLog.log("add packagePrefix: " + s);
            stackTraceManager.getPackagePrefixs().add(s);
        }
    }

    private static void readDriverClass(Properties properties) throws NotFoundException {
        String driverClassNames = properties.getProperty("jdbclogger.proxy.driverClass");
        if (driverClassNames == null || driverClassNames.length() == 0) {
            return;
        }
        String[] split = driverClassNames.split(",");
        for (String originClassName : split) {
            SqlLog.log("proxy driver. className=" + originClassName);
            proxyDriverClassNames.add(originClassName.trim());
        }
    }

    public static boolean sholdProxy(String className) {
        return proxyDriverClassNames.contains(className);
    }

}
