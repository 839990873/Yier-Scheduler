package com.scu.ztz.yierschedulerboss.controller;

import java.sql.Timestamp;
import java.util.HashMap;

import com.scu.ztz.yierschedulerboss.YierBossConfig;
import com.scu.ztz.yierschedulerboss.Thread.JobTrigger;
import com.scu.ztz.yierschedulerboss.router.RouteStrategyEnum;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;
import com.scu.ztz.yierschedulerutils.enums.GlueTypeEnum;
import com.scu.ztz.yierschedulerutils.enums.ScheduleTypeEnum;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class index {
    @RequestMapping("/")
    public ModelAndView indexPage(Model model) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("welcome", "hello fishpro");
        model.addAttribute(hm);
        ModelAndView foo = new ModelAndView("index", hm);
        return foo;
    }

    @RequestMapping(value = "/testTrigger", method = RequestMethod.GET)
    @ResponseBody
    public String requestMethodName(@RequestParam String param) {
        // Test JobInfo
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(100);
        jobInfo.setJobName("100");
        jobInfo.setGlueSourceCode("100");
        jobInfo.setGlueProerties("100");
        jobInfo.setExecutorParam("100");
        jobInfo.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
        jobInfo.setRouteStrategy(RouteStrategyEnum.RANDOM.name());
        JobTrigger.getInstance().addTrigger(jobInfo);
        return "1";
    }

    @RequestMapping(value = "/testInsertJob", method = RequestMethod.GET)
    @ResponseBody
    public String testInsertJob(@RequestParam String param) {
        // Test JobInfo
        JobInfo jobInfo = new JobInfo();
        jobInfo.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
        jobInfo.setSchedulePlan("10");
        jobInfo.setGlueType(GlueTypeEnum.GLUE_GROOVY.getDesc());
        jobInfo.setTriggerable(1);
        jobInfo.setTriggerLastTime(new Timestamp(System.currentTimeMillis()));
        jobInfo.setTriggerNextTime(new Timestamp(System.currentTimeMillis() + 5 * 1000));
        jobInfo.setRouteStrategy(RouteStrategyEnum.RANDOM.name());
        YierBossConfig.getBossConfig().getJobInfoDao().insert(jobInfo);
        return "1";
    }

    @RequestMapping(value = "/testEnableJob", method = RequestMethod.GET)
    @ResponseBody
    public String testEnableJob(@RequestParam String param) {
        // enable JobInfo now
        YierBossConfig.getBossConfig().getJobInfoDao().enableJobNow(4);
        return "1";
    }
    
    @RequestMapping(value = "/testDisableJob", method = RequestMethod.GET)
    @ResponseBody
    public String testUpDisableJob(@RequestParam String param) {
        // enable JobInfo now
        YierBossConfig.getBossConfig().getJobInfoDao().disableJobNow(4);
        return "1";
    }

     
    @RequestMapping(value = "/testInsertCode", method = RequestMethod.GET)
    @ResponseBody
    public String insertCode(@RequestParam String param) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobDescription("测试插入");
        jobInfo.setEmail("zhongtongzhuo@126.com");
        jobInfo.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
        jobInfo.setSchedulePlan("10");
        // jobInfo.setExecutorHandler(executorHandler);
        // jobInfo.setExecutorParam(executorHandler);
        // jobInfo.setExecutorTimeoutxecutorHandler);
        // jobInfo.setExecutorFailRetryCount(executorFailRetryCount);
        jobInfo.setGlueType(GlueTypeEnum.GLUE_GROOVY.getDesc());
        jobInfo.setGlueSourceCode("package com.scu.ztz.yierschedulerexecutor;" + "\n"
                + "import com.scu.ztz.yierschedulerutils.utils.JobRunner;" + "\n"
                + " public class testGlueCode extends JobRunner{" + "\n" + "    @Override"
                + "    public void run() throws Exception {" + "        System.out.println(123);" + "    }" + "\n"
                + "}");
        jobInfo.setTriggerable(0);
        jobInfo.setTriggerLastTime(new Timestamp(System.currentTimeMillis()));
        jobInfo.setTriggerNextTime(new Timestamp(System.currentTimeMillis() + 5 * 1000));
        jobInfo.setRouteStrategy(RouteStrategyEnum.RANDOM.name());
        YierBossConfig.getBossConfig().getJobInfoDao().insert(jobInfo);
        return "1";
    }

    @RequestMapping(value = "/enableCode", method = RequestMethod.GET)
    @ResponseBody
    public String enableCode(@RequestParam String param) {
        YierBossConfig.getBossConfig().getJobInfoDao().enableJobNow(Integer.parseInt(param));
        return "1";
    }
    

}
