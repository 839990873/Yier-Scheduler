package com.scu.ztz.yierschedulerutils.datagram.bossService;

import com.scu.ztz.yierschedulerutils.datagram.BossServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;
import java.sql.Timestamp;

public class RegistryDatagram extends BossServiceDatagram {

    private String executorAddress;
    private Timestamp currentTime;
    private String name = "没什么用的name";

    public String getName() {
        return name;
    }

    public String getExecutorAddress() {
        return executorAddress;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void updateCurrentTime() {
        this.currentTime = new Timestamp(System.currentTimeMillis());
    }

    public RegistryDatagram(String executorAddress) {
        this.executorAddress = executorAddress;
        currentTime = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String getType() {
        return BossServiceEnums.REGISTRY;
    }


    public static RegistryDatagram fromJson(String json) {
        return GsonTool.fromJson(json, RegistryDatagram.class);
    }
}
