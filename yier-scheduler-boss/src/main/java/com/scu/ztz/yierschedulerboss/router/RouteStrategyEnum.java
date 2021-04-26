package com.scu.ztz.yierschedulerboss.router;
// Inspired by XXL-job
public enum RouteStrategyEnum {
    ROUND("Go around", null), RANDOM("Randomly", new RouteRandom()), CONSISTENT_HASH("CONSISTENT HASH", null),
    LEAST_RECENTLY_USED("LRU", null), BUSYOVER("Busy over", null);

    RouteStrategyEnum(String title, Router router) {
        this.title = title;
        this.router = router;
    }

    private String title;
    private Router router;

    public String getTitle() {
        return title;
    }

    public Router getRouter() {
        return router;
    }

    public static RouteStrategyEnum match(String name) {
        if (name != null) {
            for (RouteStrategyEnum item : RouteStrategyEnum.values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return null;
    }
}
