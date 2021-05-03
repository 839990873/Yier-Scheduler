package com.scu.ztz.yierschedulerexecutor.controller;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class executorIndex {
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
        return "1";
    }
}
