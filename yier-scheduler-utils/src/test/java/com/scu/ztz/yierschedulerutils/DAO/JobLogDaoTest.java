package com.scu.ztz.yierschedulerutils.DAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import com.scu.ztz.yierschedulerutils.YierSchedulerUtilsApplication;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;
import com.scu.ztz.yierschedulerutils.DO.JobLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes ={YierSchedulerUtilsApplication.class})
public class JobLogDaoTest {
    @Autowired
    private JobLogDao jobLogDao;
    static final Logger logger = LoggerFactory.getLogger(ExecutorDaoTest.class);

    @Test
    public void test() {
        JobLog jobLog = new JobLog();
        jobLog.setExecutorAddress("dd");
        jobLog.setTriggerTime(new Timestamp(System.currentTimeMillis()));
        jobLog.setJobId(1);
        jobLogDao.newTrigger(jobLog);
        var i = jobLog.getId();
        System.out.println(i);
        jobLogDao.setTriggerStatus(i, "SUCCESS", "NO trigger info");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jobLogDao.newHandle(i, new Timestamp(System.currentTimeMillis()));
        jobLogDao.setHandleStatus(i, "SUCCESS", "NO handle info");
    }
}

