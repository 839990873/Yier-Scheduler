package com.scu.ztz.yierschedulerboss.Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;
import com.scu.ztz.yierschedulerutils.enums.ScheduleTypeEnum;

public class JobScheduler {
    private static Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    private JobScheduler() {} //private构造函数，保证单例
    private static JobScheduler instance = new JobScheduler();

    public static JobScheduler getInstance() {
        return instance;
    }

    private Thread scheduleThread;
    private Thread ringThread;
    private volatile boolean scheduleThreadStopFlag = false;
    private volatile boolean ringThreadStopFlag = false;
    private volatile static Map<Integer, List<JobInfo>> ring = new ConcurrentHashMap<>();
    private static final int SCHEDULER_READTIME = 5000;
    private static final int SLEEP_TIME = 1000; // 调度的最小时间单位

    public void start() {
        startScheduleThread();
        startRingThread();
    }

    public void stop() {
        //todo
    }

    private void startScheduleThread() {
        scheduleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("--------- 调度线程初始化 ---------");
                while (!scheduleThreadStopFlag) {
                    // Scan Job
                    long start = System.currentTimeMillis();
                    try {
                        // 获得(当前时间时间点+5s)之前的所有任务
                        long nowTime = System.currentTimeMillis();
                        List<JobInfo> scheduleList = YierBossConfig.getBossConfig().getJobInfoDao()
                                .scheduleJobQuery(nowTime + SCHEDULER_READTIME);
                        if (scheduleList != null && scheduleList.size() > 0) {
                            for (JobInfo jobInfo : scheduleList) {
                                // (-∞，nowTime)，代表过期任务
                                if (nowTime > jobInfo.getTriggerNextTime()) {
                                    // 过期任务的处理：todo
                                    logger.info("过期任务:" + jobInfo.getJobDescription());
                                } else {
                                    // (nowtime,nowTime+5s),当前需要触发的任务
                                    // 获得启动的时间
                                    int ringSecond = (int) ((jobInfo.getTriggerNextTime() / 1000) % 60);
                                    // 向时间轮推送
                                    logger.info("时间轮推送:");
                                    pushJobToRing(ringSecond, jobInfo);
                                    // 刷新下次执行时间
                                    refreshNextValidTime(jobInfo);
                                }
                            }
                            // 3、update trigger info
                            for (JobInfo jobInfo : scheduleList) {
                                YierBossConfig.getBossConfig().getJobInfoDao().scheduleUpdate(jobInfo);
                            }
                        } else {
                            logger.info("没有找到有效的job");
                        }
                    } catch (Exception e) {
                        if (!scheduleThreadStopFlag) {
                            logger.error("调度线程意外终止", e);
                        }
                    } finally {
                    }
                    long cost = System.currentTimeMillis() - start;
                    // 对齐秒数
                    if (cost < SLEEP_TIME) {
                        alignSeconds(SLEEP_TIME);
                    } else {
                        logger.warn("警告: 调度超时");
                    }
                }
                logger.info("调度线程终止");
            }
        });
        scheduleThread.setDaemon(true);
        scheduleThread.setName("调度线程");
        scheduleThread.start();
    }

    private void startRingThread() {
        ringThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 时间轮需要先对齐秒数
                alignSeconds(1000);
                while (!ringThreadStopFlag) {
                    try {
                        List<JobInfo> ringItemData = new ArrayList<>();
                        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
                        List<JobInfo> tmpData = ring.remove(nowSecond + 60 % 60);
                        if (tmpData == null) {
                            logger.info("当前时间轮为空，跳过");
                            continue;
                        }
                        ringItemData.addAll(tmpData);
                        logger.debug("时间轮触发:" + nowSecond + " = " + Arrays.asList(ringItemData));
                        ringItemData.forEach(i -> JobTrigger.getInstance().addTrigger(i));
                        logger.debug("时间轮触发:" + nowSecond + " = " +"完成");
                    } catch (Exception e) {
                        if (!ringThreadStopFlag) {
                            logger.error("时间轮意外终止,{}", e);
                        }
                    } finally {
                        alignSeconds(1000);
                    }
                }
                logger.info("时间轮线程停止");
            }
        });
        ringThread.setDaemon(true);
        ringThread.setName("时间轮线程");
        ringThread.start();
    }

    private void alignSeconds(int millSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(millSeconds - System.currentTimeMillis() % millSeconds);
        } catch (InterruptedException e) {
            logger.warn("align Seconds Failed");
        }
    }

    private void pushJobToRing(int second, JobInfo jobInfo) {
        List<JobInfo> ringItem = ring.get(second);
        if (ringItem == null) {
            ringItem = new ArrayList<JobInfo>();
            ring.put(second, ringItem);
        }
        ringItem.add(jobInfo);
        logger.debug("安排任务到时间轮上 : " + second + " = " + Arrays.asList(ringItem));
    }

    private void refreshNextValidTime(JobInfo jobInfo) {
        Date nextValidTime = generateNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));
        if (nextValidTime != null) {
            jobInfo.setTriggerLastTime(jobInfo.getTriggerNextTime());
            jobInfo.setTriggerNextTime(nextValidTime.getTime());
        } else {
            jobInfo.setTriggerable(0);
            jobInfo.setTriggerLastTime(0);
            jobInfo.setTriggerNextTime(0);
            logger.warn("refreshNextValidTime: Fail for job={},scheduleType={},schedulePlan={}", jobInfo.getId(),
                    jobInfo.getScheduleType(), jobInfo.getSchedulePlan());
        }
    }

    public static Date generateNextValidTime(JobInfo jobInfo, Date fromTime) {
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(jobInfo.getScheduleType(), null);
        if (ScheduleTypeEnum.CRON == scheduleTypeEnum) {
            return null;
            // todo
        } else if (ScheduleTypeEnum.FIX_RATE == scheduleTypeEnum) {
            return new Date(fromTime.getTime() + Integer.valueOf(jobInfo.getSchedulePlan()) * 1000);
        }
        return null;
    }
}
