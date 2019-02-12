package com.kg.xiaosha.bysj_english.dao;

import com.kg.xiaosha.bysj_english.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 这里是真正的查询数据库的业务逻辑实现
 * @author xiaosha
 * @create 2019-01-19 22:23
 *
 */
@Repository
@RepositoryDefinition(domainClass=Question.class,idClass=Integer.class)
public interface QuestionRepsotory extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question>, QuestionDao{

    //根据 qid 来获取对应的单词
    Question getByQid(int qid);

    //查询出一组题目10个
    @Query(value = "SELECT * FROM Question order by rand() limit 2",nativeQuery = true)
    List<Question> qryAGroupWorrds();

}
