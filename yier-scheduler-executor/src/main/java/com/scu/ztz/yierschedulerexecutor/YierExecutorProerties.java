package com.scu.ztz.yierschedulerexecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YierExecutorProerties {
    @Value("${yier.boss.ip}")
    private String bossIP;
    
    @Value("${yier.boss.port}")
    private String bossPort;

    public String getBossIP() {
        return bossIP;
    }

    public String getBossPort() {
        return bossPort;
    }
}
