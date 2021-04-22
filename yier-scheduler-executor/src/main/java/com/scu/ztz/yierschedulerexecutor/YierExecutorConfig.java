package com.scu.ztz.yierschedulerexecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerexecutor.business.BossServiceRequester;
import com.scu.ztz.yierschedulerexecutor.business.ExecutorServiceProvider;
import com.scu.ztz.yierschedulerutils.DAO.ExecutorDao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 存储所有全局参数和全局常量
@Component
public class YierExecutorConfig implements InitializingBean, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(YierExecutorConfig.class);

    @Autowired
    private ExecutorDao exexutorDao;

    // Service消息类型
    public static final BossServiceEnums bossServiceEnums = new BossServiceEnums();
    public static final CommonServiceEnums commonServiceEnums = new CommonServiceEnums();
    public static final ExecutorServiceEnums executorService = new ExecutorServiceEnums();

    // ServiceProvider和ServiceRequester,和返回消息的队列
    private static final BossServiceRequester boosServiceProvider = new BossServiceRequester();
    private static final ExecutorServiceProvider executorServiceRequester = new ExecutorServiceProvider();
    private static final int MAX_QUEUE_SIZE = 128;
    private static final ArrayBlockingQueue<ReturnDatagram> returnDatagramQueue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE,true);

    public static BossServiceRequester getBoosServiceProvider() {
        return boosServiceProvider;
    }

    public static ExecutorServiceProvider getExecutorServiceRequester() {
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
