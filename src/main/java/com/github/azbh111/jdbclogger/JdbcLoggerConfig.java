package com.github.azbh111.jdbclogger;

import com.github.azbh111.jdbclogger.stacktrace.StackTraceManager;
import javassist.ClassPool;
import javassist.CtClass;
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
            LogHelper.log("loadConfig frmo classLoader: " + classLoader.getClass().getName());
            Enumeration<URL> resources = classLoader.getResources("jdbclogger.properties");

            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                InputStream inputStream = url.openStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                inputStream.close();
                String driverClassNames = properties.getProperty("jdbclogger.proxy.driverClass");
                if (driverClassNames != null && driverClassNames.length() > 0) {
                    String[] split = driverClassNames.split(",");
                    for (String s : split) {
                        LogHelper.log("proxy driver: " + s);
                        String className = findConnectMethodDefineClass(s.trim());
                        if (className == null) {
                            throw new RuntimeException("can not find connect method in class: " + className);
                        }
                        proxyDriverClassNames.add(className);
                    }
                }
                String packagePrefixs = properties.getProperty("jdbclogger.project.packagePrefixs");
                if (packagePrefixs != null && packagePrefixs.length() > 0) {
                    String[] split = packagePrefixs.split(",");
                    for (String s : split) {
                        s = s.trim();
                        LogHelper.log("add packagePrefix: " + s);
                        stackTraceManager.getPackagePrefixs().add(s);
                    }
                }
                String packageExecludes = properties.getProperty("jdbclogger.project.packageExecludes");
                if (packageExecludes != null && packageExecludes.length() > 0) {
                    String[] split = packageExecludes.split(",");
                    for (String s : split) {
                        s = s.trim();
                        LogHelper.log("add packageExecludes: " + s);
                        stackTraceManager.getPackageExecludes().add(s);
                    }
                }
            }
        } catch (IOException | NotFoundException e) {
            LogHelper.log("加载配额制失败. " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private static String findConnectMethodDefineClass(String cls) throws NotFoundException {
        ClassPool cp = ClassPool.getDefault();
        CtClass ctClass = cp.get(cls);

        String methodName = "connect";
        CtClass paramClass1 = cp.get(String.class.getCanonicalName());
        CtClass paramClass2 = cp.get(Properties.class.getCanonicalName());
        CtClass[] paramArgs = new CtClass[]{paramClass1, paramClass2};
        try {
            ctClass.getDeclaredMethod(methodName, paramArgs);
        } catch (NotFoundException x) {
            ctClass = ctClass.getSuperclass();
            if (ctClass == null) {
                return null;
            }
        }
        return ctClass.getName();
    }

    public static boolean sholdProxy(String className) {
        return proxyDriverClassNames.contains(className);
    }

}
