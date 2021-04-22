package com.scu.ztz.yierschedulerutils.datagram.executorService;

import com.scu.ztz.yierschedulerutils.datagram.ExecutorServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class RunDatagram extends ExecutorServiceDatagram{

    @Override
    public String getType() {
        return ExecutorServiceEnums.RUN;
    }

    public static RunDatagram fromJson(String json) {
        return GsonTool.fromJson(json, RunDatagram.class);
    }
}
