package com.scu.ztz.yierschedulerboss.dao;

import com.scu.ztz.yierschedulerutils.DAO.ExecutorDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// 以后可以删了的测试代码
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExecutorDaoTest {
    @Autowired
    private ExecutorDao exexutorDao;
    static final Logger logger = LoggerFactory.getLogger(ExecutorDaoTest.class);

    @Test
    public void getAllExecutor() {
        var res = exexutorDao.getAllExecutor();
        assert (res.get(0).getId() == 1);
        assert (1 == 1);
        logger.info("gggggggggggggg");
    }
}
