package com.kg.xiaosha.bysj_english.controller;

import com.kg.xiaosha.bysj_english.bean.QuestionMessage;
import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaosha
 * @create 2019-02-18 11:59
 */
@Controller
public class QuestionSocketController {
    @Autowired
    QuestionService questionService;

    @ResponseBody
    @RequestMapping("/getQuestionList")
    public List<QuestionMessage> getQuestion(@RequestParam String level){
        //List<Question> questions = questionService.getAGroupQuestion();//这是随机查询数据库10条数据，没有难度区分
        List<Question> questions = questionService.getAGroupQuestionByLevel(Integer.parseInt(level));//有难度区分的
        List<QuestionMessage> questionMessageList = new ArrayList<QuestionMessage>();
        for(int i = 0;i<questions.size();i++){
            Question question = questions.get(i);
            String[] options = question.getOptions().split(";");
            QuestionMessage qm = new QuestionMessage();
            int random=(int)(Math.random()*4+1);//取1到4之间随机的一个随机数
            String item = "";
            switch (random){
                case 1:{item = item + question.getAnswer() +";"+ question.getOptions(); qm.setQuestionAnswer("A");} break;                  //A
                case 2:{item = item + options[0] +";"+question.getAnswer()+";"+options[1]+";"+options[2];qm.setQuestionAnswer("B");}break;  //B
                case 3:{item = item + options[0] +";"+options[1]+";"+question.getAnswer()+";"+options[2];qm.setQuestionAnswer("C");}break;  //C
                case 4:{item = item + question.getOptions()+";"+ question.getAnswer();qm.setQuestionAnswer("D");} break;                    //D
            }
            qm.setQuestionId(Integer.toString(i+1));
            qm.setQuestionItems(item);
            qm.setQuestionTitle(question.getWord());
            questionMessageList.add(qm);
        }

        return questionMessageList;
    }

}
