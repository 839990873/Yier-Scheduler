package com.scu.ztz.yierschedulerboss.controller;
import java.util.HashMap;

import com.scu.ztz.yierschedulerboss.Thread.JobTrigger;
import com.scu.ztz.yierschedulerboss.router.RouteStrategyEnum;
import com.scu.ztz.yierschedulerutils.DO.JobInfo;
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
        jobInfo.setGlueSource("100");
        jobInfo.setGlueProerties("100");
        jobInfo.setExecutorParam("100");
        jobInfo.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
        jobInfo.setRouteStrategy(RouteStrategyEnum.RANDOM.name());
        JobTrigger.getInstance().addTrigger(jobInfo);
        return "1";
    }
}
