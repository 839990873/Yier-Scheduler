package com.scu.ztz.yierschedulerboss.router;

import java.util.Random;

import com.scu.ztz.yierschedulerboss.YierBossConfig;

public class RouteRandom extends Router {

    private static Random localRandom = new Random();
    
    @Override
    public String route() {
        int length = YierBossConfig.getExecutorlist().size();
        if (length == 0)
            return null;
        int idx = localRandom.nextInt(length);
        return YierBossConfig.getExecutorlist().get(idx).getExecutorAddress();
    }
    
}
