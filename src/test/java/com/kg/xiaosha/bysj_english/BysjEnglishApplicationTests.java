package com.kg.xiaosha.bysj_english;

import com.kg.xiaosha.bysj_english.dao.QuestionRepsotory;
import com.kg.xiaosha.bysj_english.entity.Question;
import com.kg.xiaosha.bysj_english.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BysjEnglishApplicationTests {
//    @Autowired
//    QuestionRepsotory questionRepsotory;
    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepsotory questionRepsotory;

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


    /**
     * 这里是WordSQLStep2
     */
    @Test
    @Transactional
    public void resolve(){
        String[] options = new String[3];
        int random;
        int maxNum = questionRepsotory.queryNumber();
        Question question = new Question();
        Question[] opques = new Question[3];

        for(int i =1;i<= maxNum;i++){
            //每一条记录都需要更新
            question = questionRepsotory.getByQid(i);
            if(question==null)continue;

            //封装三个
            for(int j =0;j<=2;j++){
                random = (int)(Math.random()*maxNum+1);
                if(random==i) {
                    random= random +1;
                }opques[j]= questionRepsotory.getByQid(random);
                options[j]=opques[j].getAnswer();
            }
            question.setOptions(options[0]+";"+options[1]+";"+options[2]);

            System.out.println(question.toString());
            questionRepsotory.updateQuestion(question.getAnswer(),question.getLevel(),question.getOptions(),question.getWord());

        }
    }

}

