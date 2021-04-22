package com.scu.ztz.yierschedulerutils.enums;
// Inspired by XXL-job
public enum MisfireStrategyEnum  {
    DO_NOTHING("Do nothing when misfire"),
    FIRE_ONCE_NOW("Fire now when misfire");

    private String title;

    MisfireStrategyEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static MisfireStrategyEnum match(String name, MisfireStrategyEnum defaultItem){
        for (MisfireStrategyEnum item: MisfireStrategyEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return defaultItem;
    }

}