package com.github.azbh111.jdbclogger.instrument;

import com.github.azbh111.jdbclogger.SqlLog;
import com.github.azbh111.jdbclogger.instrument.transformers.DriverManagerTransformer;

import java.lang.instrument.Instrumentation;

/**
 * This class represents the agent.
 *
 * @author Hooman Kamran
 */
public class Agent {

    public static void premain(String args, Instrumentation inst) {
        Agent.starting();
        inst.addTransformer(new DriverManagerTransformer());
    }

    public static void starting() {
        SqlLog.log("JDBCLogger is starting up!");
    }


}
