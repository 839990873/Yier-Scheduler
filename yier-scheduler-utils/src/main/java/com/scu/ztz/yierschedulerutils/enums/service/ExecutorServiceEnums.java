package com.scu.ztz.yierschedulerutils.enums.service;

/**
 * ExecutorServiceEnums
 */
public class ExecutorServiceEnums extends ServiceEnums{

    public static final String BEAT = "beat_executor";
    public static final String RUN = "run_executor";
    public static final String GET_LOG = "getLog_executor";
    // public static final String KILL = "kill_executor";
private static final String[] executorServiceEnums = new String[] { BEAT, RUN, GET_LOG, /*KILL*/ };

    @Override
    public String[] getEnums() {
        return executorServiceEnums;
    }

}

// Workder向Boss提供服务的集合
// public enum ExecutorServiceEnums{
// BEAT("beat_executor"), RUN("run_executor"), GET_LOG("getLog_executor"),
// KILL("kill_executor");

// private String name;

// ExecutorServiceEnums(String name) {
// this.name = name;
// }

// public static ExecutorServiceEnums match(String name) {
// if (name != null) {
// for (ExecutorServiceEnums item : ExecutorServiceEnums.values()) {
// if (item.name().equals(name)) {
// return item;
// }
// }
// }
// return null;
// }
// }
