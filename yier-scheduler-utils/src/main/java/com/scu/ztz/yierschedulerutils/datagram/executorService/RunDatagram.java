package com.scu.ztz.yierschedulerutils.datagram.executorService;

import com.scu.ztz.yierschedulerutils.datagram.ExecutorServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class RunDatagram extends ExecutorServiceDatagram {
    private String exeAdd;
    private int jobId; //任务id
    private String executorParam; //执行的参数
    private String glueSource; //源代码
    private String glueProerties; //源代码有关的配置
    private String jobName; // 任务的名字
    private int logId;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public String getExeAdd() {
        return exeAdd;
    }

    public void setExeAdd(String exeAdd) {
        this.exeAdd = exeAdd;
    }
    
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setGlueProerties(String glueProerties) {
        this.glueProerties = glueProerties;
    }

    public String getGlueProerties() {
        return glueProerties;
    }
    
    public void setGlueSourceCode(String glueSource) {
        this.glueSource = glueSource;
    }

    public String getGlueSource() {
        return glueSource;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    @Override
    public String getType() {
        return ExecutorServiceEnums.RUN;
    }

    public static RunDatagram fromJson(String json) {
        return GsonTool.fromJson(json, RunDatagram.class);
    }
}
