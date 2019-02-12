package com.kg.xiaosha.bysj_english;

import com.kg.xiaosha.bysj_english.dao.QuestionRepsotory;
import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BysjEnglishApplicationTests {
//    @Autowired
//    QuestionRepsotory questionRepsotory;
    @Autowired
    QuestionService questionService;


    Question question;

    @Test
    public void contextLoads() {
        //根据Id查询单词

        question = questionService.getQuestionById(1);
        System.out.println(question.toString());

        //随机查询10个单词
        List<Question> questionList = questionService.getAGroupQuestion();
        System.out.println(questionList.get(0).toString());
        System.out.println(questionList.get(1).toString());
    }

}

