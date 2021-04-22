package com.scu.ztz.yierschedulerutils.enums.service;

// Boss向worker提供服务的集合
// 单例，被创建一次，保存在全局中。
public class BossServiceEnums extends ServiceEnums {
    public static final String CALLBACK = "callback_boss";
    public static final String REGISTRY = "registry_boss";
    public static final String REGISTRY_REMOVE = "registryRemove_boss";
    private static final String[] bossServiceEnums = new String[] { CALLBACK, REGISTRY, REGISTRY_REMOVE };

    @Override
    public String[] getEnums() {
        return bossServiceEnums;
    }
}
