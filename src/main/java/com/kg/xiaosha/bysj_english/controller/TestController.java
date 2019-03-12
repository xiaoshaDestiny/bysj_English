package com.kg.xiaosha.bysj_english.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaosha
 * @create 2019-03-01 11:48
 */
@Controller
public class TestController {
    /**
     * 测试视图解析器
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model) {
        String name = "xiaosha";
        model.addAttribute("name", name);
        return "hello";
    }


}
