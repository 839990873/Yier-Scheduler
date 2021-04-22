package com.scu.ztz.yierschedulerutils.enums.service;

public abstract class ServiceEnums {
    public abstract String[] getEnums();

    public String match(String name) {
        if (name != null) {
            for (String s:getEnums()) {
                if (s.equals(name)) {
                    return s;
                }
            }
        }
        return null;
    }
}
