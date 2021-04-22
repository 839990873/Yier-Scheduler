package com.scu.ztz.yierschedulerboss.controller;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class index {
    @RequestMapping("/")
    public ModelAndView indexPage(Model model) {
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("welcome", "hello fishpro");
        model.addAttribute(hm);
        ModelAndView foo = new ModelAndView("index", hm);
        return foo;
    }
}
