package com.scu.ztz.yierschedulerboss.router;

import java.util.concurrent.atomic.AtomicInteger;

import com.scu.ztz.yierschedulerboss.YierBossConfig;

public class GoRound extends Router {
    private static volatile AtomicInteger i= new AtomicInteger(0);

    @Override
    public String route() {
        int length = YierBossConfig.getExecutorlist().size();
        if (length == 0)
            return null;
        int idx = i.incrementAndGet() % length;
        return YierBossConfig.getExecutorlist().get(idx).getExecutorAddress();
    }

}
