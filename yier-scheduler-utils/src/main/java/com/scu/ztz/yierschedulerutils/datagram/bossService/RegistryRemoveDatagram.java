package com.scu.ztz.yierschedulerutils.datagram.bossService;

import com.scu.ztz.yierschedulerutils.datagram.BossServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class RegistryRemoveDatagram extends BossServiceDatagram {

    String executorAddress;

    @Override
    public String getType() {
        return BossServiceEnums.REGISTRY_REMOVE;
    }

    public static RegistryRemoveDatagram fromJson(String json) {
        return GsonTool.fromJson(json, RegistryRemoveDatagram.class);
    }
}
