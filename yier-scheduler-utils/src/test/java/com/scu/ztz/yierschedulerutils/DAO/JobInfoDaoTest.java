package com.scu.ztz.yierschedulerutils.DAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.scu.ztz.yierschedulerutils.YierSchedulerUtilsApplication;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes ={YierSchedulerUtilsApplication.class})
public class JobInfoDaoTest {
    @Autowired
    private JobInfoDao jobInfoDao;
    static final Logger logger = LoggerFactory.getLogger(ExecutorDaoTest.class);

    @Test
    public void getAllExecutor() {
        String i = jobInfoDao.getGlueSource(6);
        System.out.println(i);
    }
}
