package com.kg.xiaosha.bysj_english.service;

import com.kg.xiaosha.bysj_english.dao.QuestionRepsotory;
import com.kg.xiaosha.bysj_english.entity.Question;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
     * @Casheable注解标注的方法表示这个方法的结果会被缓存管理起来
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
                                            unless = "#result == null"   表示只有查询的结果不为空的时候才进行缓存
                                            unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
     6、sync：是否使用异步模式

     * @param id
     * @return
     */
    @Cacheable(cacheNames = {"question"})
    public Question getQuestionById(int id){
        System.out.println("查询"+id+"号单词");
        return questionRepsotory.getByQid(id);
    }

 /*   @CachePut 在方法运行以后给缓存中添加数据

        想要让更新操作去同步更新缓存，要保证key值一样

*/

    public List<Question> getAGroupQuestion(){
        System.out.println("随机查询2个单词");
        List<Question> questions = questionRepsotory.qryAGroupWorrds();
        return questions;
    }


}
