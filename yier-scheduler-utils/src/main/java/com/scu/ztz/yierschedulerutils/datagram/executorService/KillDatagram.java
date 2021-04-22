package com.scu.ztz.yierschedulerutils.datagram.executorService;

import com.scu.ztz.yierschedulerutils.datagram.ExecutorServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class KillDatagram extends ExecutorServiceDatagram{

    @Override
    public String getType() {
        return ExecutorServiceEnums.KILL;
    }

    public static KillDatagram fromJson(String json) {
        return GsonTool.fromJson(json, KillDatagram.class);
    }
}
