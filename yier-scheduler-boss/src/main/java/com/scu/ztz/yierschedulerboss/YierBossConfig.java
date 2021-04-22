package com.scu.ztz.yierschedulerboss;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerboss.business.BossServiceProvider;
import com.scu.ztz.yierschedulerboss.business.ExecutorServiceRequester;
import com.scu.ztz.yierschedulerutils.DAO.ExecutorDao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

// 存储所有全局参数和全局常量
@Component
public class YierBossConfig implements InitializingBean, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(YierBossConfig.class);

    @Autowired
    private ExecutorDao exexutorDao;

    // Service消息类型
    public static final BossServiceEnums bossServiceEnums = new BossServiceEnums();
    public static final CommonServiceEnums commonServiceEnums = new CommonServiceEnums();
    public static final ExecutorServiceEnums executorService = new ExecutorServiceEnums();

    // 保存executor和对应的channel
    private static final ConcurrentHashMap<String, Channel> executorMap = new ConcurrentHashMap<>();
    private static final CopyOnWriteArrayList<String> executorList = new CopyOnWriteArrayList<>();

    public static boolean addExecutror(String address, Channel c) {
        if (executorMap.putIfAbsent(address, c) != null) {
            logger.warn("Duplicate Executor Address");
            return false;
        }
        executorList.add(address);
        return true;
    }

    public static void rmvExecutror(String address, Channel c) {
        executorMap.remove(address);
        executorList.remove(address);
    }

    // ServiceProvider和ServiceRequester,和返回消息的队列
    private static final BossServiceProvider boosServiceProvider = new BossServiceProvider();
    private static final ExecutorServiceRequester executorServiceRequester = new ExecutorServiceRequester();
    private static final int MAX_QUEUE_SIZE = 128;
    private static final ArrayBlockingQueue<ReturnDatagram> returnDatagramQueue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE,true);

    public static BossServiceProvider getBoosServiceProvider() {
        return boosServiceProvider;
    }

    public static ExecutorServiceRequester getExecutorServiceRequester() {
        return executorServiceRequester;
    }

    public static ArrayBlockingQueue<ReturnDatagram> getReturndatagramqueue() {
        return returnDatagramQueue;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // ExecutorList = exexutorDao.getAllExecutor();
    }

    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub

    }

}
