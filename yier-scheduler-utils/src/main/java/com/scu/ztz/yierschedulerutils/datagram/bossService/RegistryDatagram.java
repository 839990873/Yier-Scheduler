package com.scu.ztz.yierschedulerutils.datagram.bossService;

import com.scu.ztz.yierschedulerutils.datagram.BossServiceDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

public class RegistryDatagram extends BossServiceDatagram {

    @Override
    public String getType() {
        return BossServiceEnums.REGISTRY;
    }

    public static RegistryDatagram fromJson(String json) {
        return GsonTool.fromJson(json, RegistryDatagram.class);
    }
}
