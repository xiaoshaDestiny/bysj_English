package com.kg.xiaosha.bysj_english.controller;

import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层组件，查询数据先返回成Json数据
 * @author xiaosha
 * @create 2019-02-11 14:22
 */
@Controller
public class QuestionController {

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
    public Map<String,Object> getQuestionPage(HttpServletRequest request){
        //获取前台当前页
        int currentPage = Integer.parseInt(request.getParameter("page"));


        //获取前台每页显示数据
        int pageSize = Integer.parseInt(request.getParameter("limit"));

        //计算当前页
        //先救给他扔在控制层吧 之后再做代码优化
        currentPage =  (currentPage -1)*pageSize;

        Map<String,Object> map=new HashMap<String,Object>();
        List<Question> questionlist = questionService.getQuestionPage(currentPage, pageSize);
        int countx=  questionService.getQuestionNumber();
        map.put("code",0);
        map.put("msg","");
        map.put("count",countx);
        map.put("data",questionlist);
        System.out.println(map.toString());
        return map;
    }


    @RequestMapping("/manager1")
    public String manager1() {
        return "listtest";
    }

    @RequestMapping("/manager2")
    public String manager2() {
        return "questionlist";
    }


    //http://localhost:8080/deleteQuestion?id=17
    @ResponseBody
    @RequestMapping("/deleteQuestion")
    public String deleteQuestionById(HttpServletRequest request){
        int qid = Integer.parseInt(request.getParameter("id"));
        questionService.deleteQuestionById(qid);
        return "200";//返回200，表示删除成功了
    }


}
