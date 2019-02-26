package com.kg.xiaosha.bysj_english.service;

import com.kg.xiaosha.bysj_english.dao.QuestionRepsotory;
import com.kg.xiaosha.bysj_english.entity.Question;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 这里是查询数据库的服务层组件
 * @author xiaosha
 * @create 2019-02-11 14:13
 */

@Service
public class QuestionService {

    @Autowired
    QuestionRepsotory questionRepsotory;

    /**
     * 把表单数据更新到数据库或者新增
     * @param question 一个Question对象
     */
    @Transactional
    public void  insertOrUpdateQuestion(Question question){
        //先根据word查询数据库
        System.out.println("==========>log(xiaosha):根据word查询Question,参数是"+question.getWord());
         //  Question queryQuestion = questionRepsotory.getQuestionByWord(question.getWord());
        //System.out.println("新增/修改从数据库查询出来的结果是："+ queryQuestion);

        if(questionRepsotory.getQuestionByWord(question.getWord()) == null){
           /* System.out.println("==========>log(xiaosha):获取Question数据表的总记录数量");
            int maxNum = questionRepsotory.queryNumber();
            question.setQid(maxNum+1);
            questionRepsotory.deleteByQid(maxNum+1);*/
            System.out.println("==========>log(xiaosha):新增一条记录"+question.toString());
            //questionRepsotory.save(question);
            insertAQuestion(question);
        }
       else{ //数据库里面已经有这个单词了  更新
            System.out.println("==========>log(xiaosha):根据word更新Question,参数是"+question.getAnswer()+","+question.getLevel() +","+question.getOptions()+","+question.getWord());
            updateAQuestion(question);//这个方法是写在本类中的
        }
    }



    /**
     * 分页查询单词，将参数的计算弄到service层 这里是查询全部数据库的
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    public List<Question> getQuestionPage(String page,String limit){
        int currentPage = Integer.parseInt(page);//当前页
        int pageSize = Integer.parseInt(limit);//每页显示数据
        System.out.println("==========>log(xiaosha):分页查询,参数第"+currentPage+"页，每页"+pageSize+"条数据");
        //计算当前页
        currentPage =  (currentPage -1)*pageSize;
        List<Question> questions = questionRepsotory.queryQuestionPage(currentPage,pageSize);
        return questions;
    }


    /**
     * 分页查询单词，将参数的计算弄到service层  这里是根据难度等级去查询的
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    public List<Question> getQuestionPageByLevel(String page,String limit,String level){
        int currentPage = Integer.parseInt(page);//当前页
        int pageSize = Integer.parseInt(limit);//每页显示数据
        int _level = Integer.parseInt(level);//每页显示数据
        System.out.println("==========>log(xiaosha):根据难度等级分页查询,参数第"+currentPage+"页，每页"+pageSize+"条数据，level="+_level);
        //计算当前页
        currentPage =  (currentPage -1)*pageSize;
        List<Question> questions = questionRepsotory.queryQuestionPageByLevel(currentPage,pageSize,_level);
        return questions;
    }


    /**
     * 获取数据表的总记录条数
     */
    public int getQuestionNumber(){
        System.out.println("==========>log(xiaosha):获取Question数据表的总记录数量");
        return questionRepsotory.queryNumber();
    }

    /**
     * 根据难度等级获取数据表的总记录条数
     */
    public int getQuestionNumberBylevel(String level){
        int _level = Integer.parseInt(level);//每页显示数据
        System.out.println("==========>log(xiaosha):根据难度等级获取Question数据表的记录数量，参数level="+level);
        return questionRepsotory.queryNumberByLevel(_level);
    }

    /*
     *根据难度随机查询若干个单词作为题目
     */
    public List<Question> getAGroupQuestionByLevel(int level){
        System.out.println("==========>log(xiaosha):根据难度随机查询若干个单词作为题目，参数是level="+level);
        List<Question> questions = questionRepsotory.qryAGroupWorrdsByLevel(level);
        return questions;
    }



    /**
     * 查询  使用缓存统一将key存为word  下面是使用缓存了的
     */
     //根据word查询question数据表
    //@Cacheable(cacheNames="question",key = "#word")
    public Question getQuestionByWord(String word){
        System.out.println("==========>log(xiaosha):根据word查询Question表里的一条数据,参数word="+word);
        return questionRepsotory.getQuestionByWord(word);
    }


    //查询question表，只返回第一条，用在前端页面做展示
   // @Cacheable(key = "#level")
    public Question getAQUestionByLevel(String level){
        int lev = Integer.parseInt(level);
        System.out.println("==========>log(xiaosha):根据难度等级查询Question,参数是"+lev);
        Question question = questionRepsotory.getQuestionByLevel(lev);
        return question;
    }

    //根据id查询Question数据表
    //@Cacheable(cacheNames = {"question"})
    public Question getQuestionById(int id){
        System.out.println("==========>log(xiaosha):根据id查询Question数据表，参数id="+id+"(使用了缓存)");
        return questionRepsotory.getByQid(id);
    }


    /**
     * 增加
     */
    //新增一条数据
    @Transactional
    //@CachePut(cacheNames = {"question"},key = "#question.word")
    public Question insertAQuestion(Question question){
        System.out.println("==========>log(xiaosha):新增加一条记录");
        questionRepsotory.insert(question.getOptions(),question.getAnswer(),question.getWord(),question.getLevel(),question.getPronounce());
        return question;
    }

    /**
     * 更新
     */

    @Transactional
    //@CachePut(cacheNames="question",key = "#question.word")
    public Question updateAQuestion(Question question){
        questionRepsotory.updateQuestion(question.getAnswer(),question.getLevel(),
                question.getOptions(),question.getWord());
        return question;
    }

    /**
     * 删除
     */
    @Transactional
    public void deleteQuestionById(int id){
        System.out.println("==========>log(xiaosha):根据Id删除Question表里的一条数据,参数id="+id);
        Question question = questionRepsotory.getByQid(id);
        deleteQuestionByWord(question.getWord());
    }

    //根据ID删除Question表的一条记录,删除和增加要在service上加事务
    @Transactional
    //@CacheEvict(cacheNames = {"question"},beforeInvocation = true,key = "#word")
    public Question deleteQuestionByWord(String word){
        Question question = questionRepsotory.getQuestionByWord(word);
        questionRepsotory.deleteByWord(word);
        return question;
    }



    /**
     *@Casheable注解标注的方法表示这个方法的结果会被缓存管理起来
     在@Casheable注解里面可以填的属性有：
     1、cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
     2、key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值  1-方法的返回值
        也可以编写SpEL表达式； #id; 参数id的值   #a0  #p0  #root.args[0] 这几个跟#id是同样的效果
       另外，指定key为 getQuestionById[1] 这样的一个字符串 可以在key属性中写  value="#root.methodName+'['+#id+']'"
       也可以使用自定义的keyGenerator生成器。
         keyGenerator：key的生成器；可以自己指定key的生成器的组件id
         key/keyGenerator：二选一使用;
     3、cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
     4、condition：指定符合条件的情况下才缓存；condition = "#id>0" 表示id大于0才去缓存
                                             condition = "#a0>1"：方法的第一个参数的值大于1的时候才进行缓存
     5、unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
                                             unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
     6、sync：是否使用异步模式


     * @CachePut：既调用方法，又更新缓存数据；同步更新缓存
     * 修改了数据库的某个数据，同时更新缓存；
     * 运行时机：
     *  1、先调用目标方法
     *  2、将目标方法的结果缓存起来


     * @CacheEvict：缓存清除
     *  key：指定要清除的数据
     *  allEntries = true：指定清除这个缓存中所有的数据
     *  beforeInvocation = false：缓存的清除是否在方法之前执行
     *      默认代表缓存清除操作是在方法执行之后执行;如果出现异常缓存就不会清除
     *  beforeInvocation = true：
     *      代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
     */

    /**
     * 这是项目初期的一些代码，当时是用来测试是否查到数据了
     * @return
     */
    public List<Question> getAGroupQuestion(){
        System.out.println("==========>log(xiaosha):随机查询若干个单词作为题目");
        List<Question> questions = questionRepsotory.qryAGroupWorrds();
        return questions;
    }


    /**
     * 下面这个方法是更新数据库里面每一个问题的干扰项的 不缓存 只作为工具方法使用
     */
    @Transactional
    public void updateOptions(){
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
                        random= random -1;
                    }System.out.println("随机数"+(j+1)+"是："+random);
                    opques[j]= questionRepsotory.getByQid(random);
                    options[j]=opques[j].getAnswer();
                }
                int level = (int)(Math.random()*3+1);
                question.setLevel(level);
                question.setOptions(options[0]+";"+options[1]+";"+options[2]);
                System.out.println(question.toString());
                questionRepsotory.updateQuestionOptions(question.getOptions(),question.getWord());

            }
    }

}
