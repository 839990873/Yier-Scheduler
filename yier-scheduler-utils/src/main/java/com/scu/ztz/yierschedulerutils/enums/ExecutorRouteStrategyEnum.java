package com.scu.ztz.yierschedulerutils.enums;
// Inspired by XXL-job
public enum ExecutorRouteStrategyEnum {
    ROUND("Go around", null), RANDOM("Randomly", null), CONSISTENT_HASH("CONSISTENT HASH", null),
    LEAST_RECENTLY_USED("LRU", null), BUSYOVER("Busy over", null);

    ExecutorRouteStrategyEnum(String title, Object router) {
        this.title = title;
        this.router = router;
    }

    private String title;
    private Object router;

    public String getTitle() {
        return title;
    }

    public Object getRouter() {
        return router;
    }

    public static ExecutorRouteStrategyEnum match(String name) {
        if (name != null) {
            for (ExecutorRouteStrategyEnum item : ExecutorRouteStrategyEnum.values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return null;
    }
}
