package com.kg.xiaosha.bysj_english.controller;

import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * 控制层组件，关于数据表question的请求映射
 * @author xiaosha
 * @create 2019-02-11 14:22
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;


    /**
     *回退到首页forward:/index.html
     */
    @RequestMapping(value = "/backindex")
    public String home() {
        return "forward:/index.html";
    }

    /**
     * 顶部导航栏点击了答题的处理器
     * @return  收到这个请求后返回到答题页面
     */
    @RequestMapping(value="/socketQuestion")
    public String socketQuestion( Model model,@RequestParam String level){
        model.addAttribute("level",level);
        return "questionsocket";
    }


    /**
     *点击修改/新增表单提交按钮会在这里来处理
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submitQuestion")
    public Question submitQuestion(@RequestParam int id, @RequestParam String word, @RequestParam String answer,
    @RequestParam String option1,@RequestParam String option2,@RequestParam String option3,@RequestParam String level){
        Question que = new Question();
        que.setQid(id);
        que.setAnswer(answer);
        que.setLevel(Integer.parseInt(level));
        String options =  option1 + ";" + option2 + ";" + option3;
        que.setOptions(options);
        que.setWord(word);
        System.out.println("更新、修改传入的参数是"+que.toString());
        questionService.insertOrUpdateQuestion(que);
        return que;
    }


    /**
     * 点击编辑按钮会发送请求 /getQuestion  会携带一个参数 word 在数据表上点击编辑，这个ajax用来回显数据的，写的好难受
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/editQuestion")
    public Map<String,Object> editQuestion(@RequestParam String word){
        System.out.println("输出查询条件：word是 "+word);
        Map<String,Object> map=new HashMap<String,Object>();

        Question question = questionService.getQuestionByWord(word);
        String[] options = question.getOptions().split(";");

        map.put("word",question.getWord());
        map.put("pronounce",question.getPronounce());
        map.put("answer",question.getAnswer());
        map.put("option1",options[0]);
        map.put("option2",options[1]);
        map.put("option3",options[2]);
        map.put("level",question.getLevel());

        return map;
    }


    /**
     * 获取一个Question数据填写到form表单中会被这个方法处理
     * 1:点击头部导航栏会发送请求 /getQuestion?level=1 ，也就是刚进这个页面的时候需要给一个默认的数据显示在页面上
     * 2:点击查询按钮会发送请求 是一个form表单 input的name是word ，这里是自己输入单词进行查询
     *
     * @param model
     * @param level
     * @param word
     * @return
     */
    @RequestMapping("/getQuestion")
    public String getQuestionToForm(Model model, @RequestParam(required=false) String level,
                     @RequestParam(required=false)String word,@RequestParam(required=false) String flag) {
        Question question = new Question();
        String[] options = {"null","null","nll"};
        question.setPronounce("点击导航栏刷新");
        question.setWord("走丢了！");
        if(level != null){//这是头部导航栏会发送请求 这里会有一个错误，就是level莫名其妙拼接了多个  把它截取第一个作为参数吧
            if(level.length()>"1".length()){
                level = level.split(",")[0];
            }
            question = questionService.getAQUestionByLevel(level); //查询出第一条记录 要显示到表单里面
            options = question.getOptions().split(";");
        }
        if(word != null){ //给thymeleaf回显数据的
            question = questionService.getQuestionByWord(word);
            if(question == null){
                return "redirect:error.html";
            }
            else{
                options = question.getOptions().split(";");
            }
        }
        if(level == null){
            level = "0";   //为0的时候代表查询所有的吧
            //question = questionService.getQuestionByWord("abstract");
        }
        model.addAttribute("que",question);
        model.addAttribute("options",options);

        model.addAttribute("level",level);//这个不是用来显示的，是传递给分页查询的
        return "questionlist";
    }

    /**
     * 查询单词 按钮提交之后是在这里来处理的   是在jS里面写的
     * @param model
     * @return
     */
    @RequestMapping("/backtolist")
    public String backtolist(Model model) {
        Question question = questionService.getQuestionById(2);
        String[] options = question.getOptions().split(";");
        model.addAttribute("que",question);
        model.addAttribute("options",options);
        return "questionlist";
    }


    /**
     * 删除一条记录的MVC控制器
     * 前端在数据表格上点击删除之后会被这个方法处理
     * URL是这个： http://localhost:8080/deleteQuestion?id=17
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteQuestion")
    public String deleteQuestionById(HttpServletRequest request){
        int qid = Integer.parseInt(request.getParameter("id"));
        questionService.deleteQuestionById(qid);
        return "200";       //返回200，表示删除成功了
    }


    /**
     * 分页查询，这是自动调用的，layui前端发起的ajax请求
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPage")
    public Map<String,Object> getQuestionPage(@RequestParam(required=true) String page,
              @RequestParam(required=true) String limit,@RequestParam(required=false) String levle){
        Map<String,Object> map=new HashMap<String,Object>();
        List<Question> questionlist = new ArrayList<Question>();
        int countx = 0;
        if(levle.equals("0")){//这里是查询全部吔
            //小龙、大龙、蓝Buff、红Buff,我全都要！！！
            questionlist = questionService.getQuestionPage(page,limit);
            countx =  questionService.getQuestionNumber();
        }else{
            //算了，我头不铁，我只要大龙...
            questionlist = questionService.getQuestionPageByLevel(page,limit,levle);
            countx = questionService.getQuestionNumberBylevel(levle);
        }

        map.put("code",0);
        map.put("msg","");
        map.put("count",countx);
        map.put("data",questionlist);
        return map;
    }


/*    *//**
     * 分页查询，这是自动调用的，layui前端发起的ajax请求
     * @return
     *//*
    @ResponseBody
    @RequestMapping("/getPage")
    public Map<String,Object> getQuestionPage(@RequestParam(required=true) String page,
                                              @RequestParam(required=true) String limit,@RequestParam(required=true) String levle){
        System.out.println("levle="+levle);


        Map<String,Object> map=new HashMap<String,Object>();
        List<Question> questionlist = questionService.getQuestionPage(page, limit);
        int countx=  questionService.getQuestionNumber();
        map.put("code",0);
        map.put("msg","");
        map.put("count",countx);
        map.put("data",questionlist);
        return map;
    }*/


    /**
     * 下面是一些测试MVC控制器
     */


    /**
     * 项目前期做的一些测试，看前端页面是否有响应
     * @param id
     * @return
     */
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
     * 更新单词的option选项
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateOtionns")
    public String updateAllQuestionOptions(){
        questionService.updateOptions();
        return "success";
    }

}
