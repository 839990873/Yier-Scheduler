package com.scu.ztz.yierschedulerexecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerexecutor.business.BossServiceRequester;
import com.scu.ztz.yierschedulerexecutor.business.ExecutorServiceProvider;
import com.scu.ztz.yierschedulerexecutor.server.ExecutorServer;
import com.scu.ztz.yierschedulerutils.DAO.ExecutorDao;
import com.scu.ztz.yierschedulerutils.DAO.JobInfoDao;
import com.scu.ztz.yierschedulerutils.DAO.JobLogDao;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 存储所有全局参数和全局常量
@Component
public class YierExecutorConfig implements InitializingBean, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(YierExecutorConfig.class);
    private static YierExecutorConfig executorConfig = null;

    public static YierExecutorConfig getExecutorConfig() {
        return executorConfig;
    }

    @Autowired
    public JobInfoDao jobInfoDao;

    @Autowired
    public JobLogDao jobLogDao;

    public JobInfoDao getJobInfoDao() {
        return jobInfoDao;
    }

    public JobLogDao getJobLogDao() {
        return jobLogDao;
    }


    // ServiceProvider和ServiceRequester,和返回消息的队列,保证前面的初始化了再初始化他们，顺序调转会导致问题，最好改造成Autowired和Bean
    private static final int MAX_QUEUE_SIZE = 128;
    private static final ArrayBlockingQueue<ReturnDatagram> returnDatagramQueue = new ArrayBlockingQueue<>(
            MAX_QUEUE_SIZE, true);
    private static BossServiceRequester bossServiceRequester;
    private static ExecutorServiceProvider executorServiceProvider;


    // Service消息类型
    public static final BossServiceEnums bossServiceEnums = new BossServiceEnums();
    public static final CommonServiceEnums commonServiceEnums = new CommonServiceEnums();
    public static final ExecutorServiceEnums executorServiceEnums = new ExecutorServiceEnums();

    
    // 返回消息的最大时长
    public static int MAX_TIMEOUT;
    
    @Value("${yier.time.timeout}")
    public void setTimeout(int timeout) {
        MAX_TIMEOUT = timeout;
    }
    
    // 业务线程池,参数todo
    public static ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(10, 10, MAX_TIMEOUT, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(256),new ThreadFactory(){
        public Thread newThread(Runnable r) {
            return new Thread(r, "Executor业务线程池" + r.hashCode());
        };
    });
    
    public static int getMAX_TIMEOUT() {
        return MAX_TIMEOUT;
    }
    
    public static String localAddress; // executor的ip和端口号
    private static String bossIP;// Boss的远端地址
    private static int bossPort;
    
    @Value("${yier.boss.ip}")
    public void setBossIP(String ip) {
        bossIP = ip;
    }
    
    @Value("${yier.boss.port}")
    public void setBossPort(int port) {
        bossPort = port;
    }
    
    public static String getBossIP() {
        return bossIP;
    }
    
    public static int getBossPort() {
        return bossPort;
    }
    
    public static BossServiceRequester getBossServiceRequester() {
        return bossServiceRequester;
    }
    
    public static ExecutorServiceProvider getExecutorServiceProvider() {
        return executorServiceProvider;
    }
    
    public static ArrayBlockingQueue<ReturnDatagram> getReturndatagramqueue() {
        return returnDatagramQueue;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        executorConfig = this;
        bossServiceRequester = new BossServiceRequester();
        executorServiceProvider =  new ExecutorServiceProvider();
        // 启动Executor的server服务
        ExecutorServer.getInstance().init();
    }
    
    @Override
    public void destroy() throws Exception {

    }
    
}
