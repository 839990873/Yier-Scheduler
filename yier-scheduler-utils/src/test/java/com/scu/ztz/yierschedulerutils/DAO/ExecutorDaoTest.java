package com.scu.ztz.yierschedulerutils.DAO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.scu.ztz.yierschedulerutils.YierSchedulerUtilsApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes ={YierSchedulerUtilsApplication.class})
public class ExecutorDaoTest {
    @Autowired
    private ExecutorDao exexutorDao;
    static final Logger logger = LoggerFactory.getLogger(ExecutorDaoTest.class);

    @Test
    public void getAllExecutor() {
        var res = exexutorDao.getAllExecutor();
        assert (res.get(0).getId() == 1);
        logger.info("gggggggggggggg");
    }
}