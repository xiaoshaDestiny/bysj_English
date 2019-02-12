package com.kg.xiaosha.bysj_english.controller;

import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层组件，查询数据先返回成Json数据
 * @author xiaosha
 * @create 2019-02-11 14:22
 */
@RestController
public class QuestionContrpller {

    @Autowired
    QuestionService questionService;

    @RequestMapping("/que/{id}")
    public Question getQuestion(@PathVariable("id") int id){
        Question question = questionService.getQuestionById(id);
        return question;
    }
}
