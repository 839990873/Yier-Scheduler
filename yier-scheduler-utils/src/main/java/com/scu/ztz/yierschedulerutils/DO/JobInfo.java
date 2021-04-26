package com.scu.ztz.yierschedulerutils.DO;

import java.util.Date;

public class JobInfo {
    private int id; // 主键ID
    private String jobDescription;
    private String email; // 报警邮件
    private String scheduleType; // 调度类型
    private String schedulePlan; // 调度配置，值含义取决于调度类型
    private String executorHandler; // 执行器，任务Handler名称
    private String executorParam; // 执行器，任务参数
    private int executorTimeout; // 任务执行超时时间，单位秒
    private int executorFailRetryCount; // 失败重试次数
    private String glueType; // GLUE类型,对应com.scu.ztz.yierschedulerutils.enums;
    private String glueSource; // GLUE源代码
    private String glueProerties; // GLUE源代码
    private int triggerable; // 调度状态：0-停止，1-运行
    private long triggerLastTime; // 上次调度时间
    private long triggerNextTime; // 下次调度时间
    private String jobName; // 任务的名字
    private String routeStrategy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getSchedulePlan() {
        return schedulePlan;
    }

    public void setSchedulePlan(String schedulePlan) {
        this.schedulePlan = schedulePlan;
    }


    public String getExecutorHandler() {
        return executorHandler;
    }

    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public int getExecutorTimeout() {
        return executorTimeout;
    }

    public void setExecutorTimeout(int executorTimeout) {
        this.executorTimeout = executorTimeout;
    }

    public int getExecutorFailRetryCount() {
        return executorFailRetryCount;
    }

    public void setExecutorFailRetryCount(int executorFailRetryCount) {
        this.executorFailRetryCount = executorFailRetryCount;
    }

    public String getGlueType() {
        return glueType;
    }

    public void setGlueType(String glueType) {
        this.glueType = glueType;
    }

    public String getGlueSource() {
        return glueSource;
    }

    public void setGlueSource(String glueSource) {
        this.glueSource = glueSource;
    }

    public String getGlueProerties() {
        return glueProerties;
    }

    public int getTriggerable() {
        return triggerable;
    }

    public void setTriggerable(int triggerable) {
        this.triggerable = triggerable;
    }

    public void setGlueProerties(String glueProerties) {
        this.glueProerties = glueProerties;
    }

    public long getTriggerLastTime() {
        return triggerLastTime;
    }

    public void setTriggerLastTime(long triggerLastTime) {
        this.triggerLastTime = triggerLastTime;
    }

    public long getTriggerNextTime() {
        return triggerNextTime;
    }

    public void setTriggerNextTime(long triggerNextTime) {
        this.triggerNextTime = triggerNextTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getRouteStrategy() {
        return routeStrategy;
    }

    public void setRouteStrategy(String routeStrategy) {
        this.routeStrategy = routeStrategy;
    }
}
