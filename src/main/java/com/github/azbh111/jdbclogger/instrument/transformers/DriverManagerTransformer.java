package com.github.azbh111.jdbclogger.instrument.transformers;

import com.github.azbh111.jdbclogger.JdbcLoggerConfig;
import com.github.azbh111.jdbclogger.LogHelper;
import com.github.azbh111.jdbclogger.wrappers.ConnectionWrapper;
import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;
import java.util.WeakHashMap;

/**
 * This class is responsible to modify the DriverManager to spawn our own Connection class.
 *
 * @author HK
 */
public class DriverManagerTransformer implements ClassFileTransformer {
    private WeakHashMap cache = new WeakHashMap();
    private Object NULL = new Object();

    public DriverManagerTransformer() {
        importPackage();
    }

    @Override
    public byte[] transform(ClassLoader loader, String rawclassName, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        String className = rawclassName.replaceAll("/", ".");
        loadConfigFromClassPath(); // 如果有新的ClassLoder, 就尝试加载配置
        if (JdbcLoggerConfig.sholdProxy(className)) {
            return doTransform(className, classfileBuffer);
        }
        return classfileBuffer;
    }

    private void importPackage() {
        ClassPool cp = ClassPool.getDefault();
        cp.importPackage("sun.reflect");
    }

    private byte[] doTransform(String className, byte[] classfileBuffer) {
        try {
            LogHelper.log("Instrumenting " + className);
            ClassPool cp = ClassPool.getDefault();

            CtClass curClass = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

            proxyConnectMethod(cp, curClass);
            return curClass.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadConfigFromClassPath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            return;
        }
        if (!cache.containsKey(classLoader)) {
            cache.put(classLoader, NULL);
            if (!"sun.misc.Launcher$AppClassLoader".equals(classLoader.getClass().getName())) {
                ClassPool.getDefault().appendClassPath(new LoaderClassPath(classLoader));
            }
            JdbcLoggerConfig.loadConfig(classLoader);
        }
    }

    private void proxyConnectMethod(ClassPool cp, CtClass curClass) throws NotFoundException, CannotCompileException {
        String methodName = "connect";
        CtClass paramClass1 = cp.get(String.class.getCanonicalName());
        CtClass paramClass2 = cp.get(Properties.class.getCanonicalName());
        CtClass[] paramArgs = new CtClass[]{paramClass1, paramClass2};

        CtMethod originMethod = curClass.getDeclaredMethod(methodName, paramArgs);
        // 创建新的方法，复制原来的方法
        CtMethod mnew = CtNewMethod.copy(originMethod, methodName, curClass, null);

//		原方法改名
        originMethod.setName(methodName + "$Impl");

        String wrapperClass = ConnectionWrapper.class.getCanonicalName();
        mnew.setBody(String.format("{return new %s(%s$Impl($1, $2));}", wrapperClass, methodName));

        curClass.addMethod(mnew);
    }

}
