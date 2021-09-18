package com.github.azbh111.jdbclogger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author: zyp
 * @since: 2021/9/18 11:34
 */
public class JdbcLoggerConfig {
    private static Set<String> proxyDriverClassNames = new HashSet<>();

    static {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            InputStream resourceAsStream = contextClassLoader.getResourceAsStream("jdbclogger.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            resourceAsStream.close();
            String driverClassNames = properties.getProperty("jdbclogger.proxy.driverClass");
            String[] split = driverClassNames.split(",");
            for (String s : split) {
                String className = findConnectMethodDefineClass(s.trim());
                if (className == null) {
                    throw new RuntimeException("can not find connect method in class: " + className);
                }
                LogHelper.log("find connect method in class: " + className);
                proxyDriverClassNames.add(className);
            }
        } catch (IOException | NotFoundException e) {
            throw new RuntimeException(e);
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
