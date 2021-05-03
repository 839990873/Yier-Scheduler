package com.scu.ztz.yierschedulerutils.datagram;

import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

//Executor -> Boss的Datagram
public abstract class BossServiceDatagram implements DatagramConverter{
    // 发送请求的executor的ID
    private String type;

    protected BossServiceDatagram() {
        type = getType();   
    }

    public abstract String getType();


    @Override
    public Datagram toDatagram() {
        String json = GsonTool.toJson(this);
        return new Datagram(type, json);
    }
}
