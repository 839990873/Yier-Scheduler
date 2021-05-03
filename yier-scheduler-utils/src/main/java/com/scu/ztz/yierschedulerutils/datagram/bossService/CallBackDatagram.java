package com.scu.ztz.yierschedulerutils.datagram.bossService;

import com.scu.ztz.yierschedulerutils.datagram.BossServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class CallBackDatagram extends BossServiceDatagram {
    String executorAddress;
    int logId;
    String status;
    String message;

    @Override
    public String getType() {
        return BossServiceEnums.CALLBACK;
    }

    public static CallBackDatagram fromJson(String json) {
        return GsonTool.fromJson(json, CallBackDatagram.class);
    }
}
