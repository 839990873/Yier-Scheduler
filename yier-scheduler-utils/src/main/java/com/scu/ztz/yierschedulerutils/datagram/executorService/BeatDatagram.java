package com.scu.ztz.yierschedulerutils.datagram.executorService;

import com.scu.ztz.yierschedulerutils.datagram.ExecutorServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class BeatDatagram extends ExecutorServiceDatagram {

    String ExecutorAddress;
    int activeCount;

    
    @Override
    public String getType() {
        return ExecutorServiceEnums.BEAT;
    }
    
    public static BeatDatagram fromJson(String json) {
        return GsonTool.fromJson(json, BeatDatagram.class);
    }
}
