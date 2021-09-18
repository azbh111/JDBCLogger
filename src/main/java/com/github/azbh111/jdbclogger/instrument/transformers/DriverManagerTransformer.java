package com.github.azbh111.jdbclogger.instrument.transformers;

import com.github.azbh111.jdbclogger.JdbcLoggerConfig;
import com.github.azbh111.jdbclogger.LogHelper;
import com.github.azbh111.jdbclogger.instrument.Agent;
import com.github.azbh111.jdbclogger.wrappers.ConnectionWrapper;
import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;

/**
 * This class is responsible to modify the DriverManager to spawn our own Connection class.
 *
 * @author HK
 */
public class DriverManagerTransformer implements ClassFileTransformer {


    public DriverManagerTransformer() {
    }

    @Override
    public byte[] transform(ClassLoader loader, String rawclassName, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        String className = rawclassName.replaceAll("/", ".");

        if (JdbcLoggerConfig.sholdProxy(className)) {
            try {
                LogHelper.log("Instrumenting " + className);
                ClassPool cp = ClassPool.getDefault();

                cp.importPackage("sun.reflect");

                CtClass curClass = cp.get(className);

                connect(cp, curClass);

                Agent.running();
                return curClass.toBytecode();
            } catch (NotFoundException | CannotCompileException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return classfileBuffer;
    }

    private void connect(ClassPool cp, CtClass curClass) throws NotFoundException, CannotCompileException {
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
