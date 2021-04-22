package com.scu.ztz.yierschedulerutils.enums;

// Inspired by XXL-job
public enum ScheduleTypeEnum {

    NONE("None"),
    CRON("Cron"),
    FIX_RATE("FixRate");

    private String title;

    ScheduleTypeEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static ScheduleTypeEnum match(String name, ScheduleTypeEnum defaultItem){
        for (ScheduleTypeEnum item: ScheduleTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return defaultItem;
    }

}
