package com.scu.ztz.yierschedulerexecutor;

import com.scu.ztz.yierschedulerutils.utils.JobRunner;

public class testGlueCode extends JobRunner{

    @Override
    public void run() throws Exception {
        if(System.currentTimeMillis()%2==0)
            throw new Exception("随机错误");
    }

}