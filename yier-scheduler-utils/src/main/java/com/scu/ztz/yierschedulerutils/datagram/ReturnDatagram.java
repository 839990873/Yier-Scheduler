package com.scu.ztz.yierschedulerutils.datagram;

import java.io.Serializable;

import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;
import com.scu.ztz.yierschedulerutils.utils.GsonTool;

// Inspired By XXL-job
public class ReturnDatagram implements DatagramConverter {
    private static final String type = CommonServiceEnums.RETURN;

    public String getType() {
        return type;
    }

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    public static final Datagram SUCCESS = new ReturnDatagram(SUCCESS_CODE, null).toDatagram();
    public static final Datagram FAIL = new ReturnDatagram(FAIL_CODE, null).toDatagram();

    private int code;
    private String msg;

    public ReturnDatagram() {
    }

    public ReturnDatagram(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static ReturnDatagram wrongType(String currentType) {
        return new ReturnDatagram(FAIL_CODE,"Wrong Datagram Type:"+currentType);
    }

    @Override
    public String toString() {
        return "ReturnDatagram [code=" + code + ", msg=" + msg;
    }

    @Override
    public Datagram toDatagram() {
        String json = GsonTool.toJson(this);
        return new Datagram(type, json);
    }

    public static ReturnDatagram fromJson(String json) {
        return GsonTool.fromJson(json, ReturnDatagram.class);
    }

}