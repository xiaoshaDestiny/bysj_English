package com.kg.xiaosha.bysj_english.controller;

import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层组件，查询数据先返回成Json数据
 * @author xiaosha
 * @create 2019-02-11 14:22
 */
@Controller
public class QuestionContrpller {

    @Autowired
    QuestionService questionService;

    @ResponseBody
    @RequestMapping("/que/{id}")
    public Question getQuestion(@PathVariable("id") int id){
        Question question = questionService.getQuestionById(id);
        return question;
    }


    /**
     * 测试视图解析器
     * @param model
     * @return
     */
    @GetMapping("/hello")
    public String hello(Model model) {
        String name = "xiaosha";
        model.addAttribute("name", name);
        return "hello";
    }


    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPage")
    public List<Question> fdf(){
/*        Map<String,Object> map=new HashMap<String,Object>();
        List<ClassTable> ct=dataShowService.queryInfox(curr,limit);
        //总数居条数
        int countx=dataShowService.queryAllCountxx();
        map.put("data",ct);
        map.put("ct",countx);
        return map;*/
        return questionService.getQuestionPage(2, 4);
    }

}
