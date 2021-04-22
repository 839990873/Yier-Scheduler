package com.scu.ztz.yierschedulerutils.datagram;

import com.scu.ztz.yierschedulerutils.utils.GsonTool;
//Boss -> Executor的Datagram
public abstract class ExecutorServiceDatagram implements DatagramConverter{
    private String type;

    protected ExecutorServiceDatagram() {
        type = getType();
    }

    public abstract String getType();
    
    @Override
    public Datagram toDatagram() {
        String json = GsonTool.toJson(this);
        return new Datagram(type, json);
    }
}