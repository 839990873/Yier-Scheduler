package com.scu.ztz.yierschedulerboss;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.scu.ztz.yierschedulerutils.datagram.ReturnDatagram;
import com.scu.ztz.yierschedulerutils.enums.service.BossServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.CommonServiceEnums;
import com.scu.ztz.yierschedulerutils.enums.service.ExecutorServiceEnums;
import com.scu.ztz.yierschedulerboss.Thread.JobScheduler;
import com.scu.ztz.yierschedulerboss.Thread.JobTrigger;
import com.scu.ztz.yierschedulerboss.business.BossServiceProvider;
import com.scu.ztz.yierschedulerboss.business.ExecutorServiceRequester;
import com.scu.ztz.yierschedulerboss.server.BossServer;
import com.scu.ztz.yierschedulerutils.DAO.ExecutorDao;
import com.scu.ztz.yierschedulerutils.DAO.JobInfoDao;
import com.scu.ztz.yierschedulerutils.DAO.JobLogDao;
import com.scu.ztz.yierschedulerutils.DO.Executor;

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
    private static YierBossConfig bossConfig = null;

    public static YierBossConfig getBossConfig() {
        return bossConfig;
    }

    @Autowired
    public ExecutorDao exexutorDao;

    @Autowired
    public JobInfoDao jobInfoDao;

    @Autowired
    public JobLogDao jobLogDao;

    public JobLogDao getJobLogDao() {
        return jobLogDao;
    }

    public ExecutorDao getExexutorDao() {
        return exexutorDao;
    }

    public JobInfoDao getJobInfoDao() {
        return jobInfoDao;
    }

    // Service消息类型
    public static final BossServiceEnums bossServiceEnums = new BossServiceEnums();
    public static final CommonServiceEnums commonServiceEnums = new CommonServiceEnums();
    public static final ExecutorServiceEnums executorServiceEnums = new ExecutorServiceEnums();

    // 保存executor和对应的channel和锁
    private static final ConcurrentHashMap<String, Channel> executorMap = new ConcurrentHashMap<>();
    private static final CopyOnWriteArrayList<Executor> executorList = new CopyOnWriteArrayList<>();
    private static final ConcurrentHashMap<String, Lock> executorLocks = new ConcurrentHashMap<>();

    public static List<Executor> getExecutorlist() {
        return executorList;
    }

    public static Channel getChannel(String address) {
        return executorMap.get(address);
    }

    public static Lock getLock(String address) {
        return executorLocks.get(address);
    }

    public static boolean addExecutror(Executor e, Channel c) {
        synchronized (YierBossConfig.class) {
            // 允许重复添加,删除旧值
            if (executorMap.putIfAbsent(e.getExecutorAddress(), c) != null) {
                logger.warn("Duplicate Executor Address");
                executorList.removeIf(i -> i.getExecutorAddress() == e.getExecutorAddress());
                executorMap.put(e.getExecutorAddress(), c);
            }
            executorLocks.put(e.getExecutorAddress(), new ReentrantLock(true));
            executorList.add(e);
            logger.info(executorList.toString());
            logger.info(executorMap.toString());
        }
        return true;
    }

    // todo
    public static void rmvExecutror(String address, Channel c) {
        synchronized (YierBossConfig.class) {
            executorMap.remove(address);
            executorList.removeIf(i -> i.getExecutorAddress() == address);
            executorLocks.remove(address);
        }
    }

    // ServiceProvider和ServiceRequester,和返回消息的队列
    private static final int MAX_QUEUE_SIZE = 128;
    private static final ArrayBlockingQueue<ReturnDatagram> returnDatagramQueue = new ArrayBlockingQueue<>(
            MAX_QUEUE_SIZE, true);
            //顺序有影响，上面的要摆在上面
    private static final BossServiceProvider boosServiceProvider = new BossServiceProvider();
    private static final ExecutorServiceRequester executorServiceRequester = new ExecutorServiceRequester();

    public static BossServiceProvider getBoosServiceProvider() {
        return boosServiceProvider;
    }

    public static ExecutorServiceRequester getExecutorServiceRequester() {
        return executorServiceRequester;
    }

    public static ArrayBlockingQueue<ReturnDatagram> getReturndatagramqueue() {
        return returnDatagramQueue;
    }

    // 线程相关
    JobScheduler jobScheduler = JobScheduler.getInstance();
    JobTrigger jobTrigger = JobTrigger.getInstance();
    // 业务线程池,参数todo
    public static ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(10, 10, 256, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(256),new ThreadFactory(){
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Boss业务线程池" + r.hashCode());
                };
            });

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化配置
        bossConfig = this;
        // 启动Boos的Server服务
        BossServer.getInstance().init();
        // 启动线程
        // 任务触发线程
        jobTrigger.start();
        jobScheduler.start();
    }

    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
    }

}
