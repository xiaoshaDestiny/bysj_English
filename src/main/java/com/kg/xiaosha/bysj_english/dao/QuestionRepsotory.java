package com.kg.xiaosha.bysj_english.dao;

import com.kg.xiaosha.bysj_english.entity.Question;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 这里是真正的查询数据库的业务逻辑实现  springdata  数据库    ACID 隐式事务  显示事务  update insert
 * @author xiaosha
 * @create 2019-01-19 22:23
 *
 */
@Repository
@RepositoryDefinition(domainClass=Question.class,idClass=Integer.class)
public interface QuestionRepsotory extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question>, QuestionDao{

/*    @Modifying
    @Query("UPDATE Person p SET p.lastName = :lastName WHERE id = :id")
    void updatePersonName(@Param("id") Integer id, @Param("lastName") String lastName);*/


   @Modifying
   public Question save(Question question);


    //新增数据
   //INSERT INTO `question` VALUES ('1', 'n.杂草，野草vi.除草;a.清晰的vt.清除;n.记述；解释；帐目', 'vt.丢弃；放弃，抛弃', '﻿abandon', '2', 'ə’bændən');
   @Modifying
   @Query(value = "INSERT INTO Question (options,answer,word,level,pronounce) VALUES (:options,:answer,:word,:level,:pronounce)",nativeQuery = true)
   public void insert(@Param("options") String options,@Param("answer") String answer,@Param("word") String word,@Param("level") int level,@Param("pronounce") String pronounce );

    /**
     * 根据word更新Question表
     */
    @Modifying
    @Query(value = "UPDATE Question q SET q.answer = :answer,q.level = :level,q.options = :options WHERE q.word = :word",nativeQuery = true)
    public void updateQuestion(@Param("answer")String answer,@Param("level")int level,@Param("options")String options,@Param("word")String word);

    //根据 qid 来获取对应的单词
    Question getByQid(int qid);

    //分页查询全部的数据
    @Query(value = "SELECT * FROM Question LIMIT :PageNo,:PageSize",nativeQuery = true)
    List<Question> queryQuestionPage(@Param("PageNo") int PageNo, @Param("PageSize") int PageSize);

    //分页查询,根据难度等级的查询
    @Query(value = "SELECT * FROM Question WHERE level = :level LIMIT :PageNo,:PageSize",nativeQuery = true)
    List<Question> queryQuestionPageByLevel(@Param("PageNo") int PageNo, @Param("PageSize") int PageSize,@Param("level") int level);


    //查询数据表总的记录数
    @Query(value = "SELECT COUNT(*) FROM Question",nativeQuery = true)
    public int queryNumber();


    //查询数据表总的记录数
    @Query(value = "SELECT COUNT(*) FROM Question WHERE level =:level",nativeQuery = true)
    public int queryNumberByLevel(@Param("level") int level);


    //根据id删除单词
    @Modifying
    @Query(value = "DELETE  FROM Question WHERE qid = :qid",nativeQuery = true)
    public void deleteByQid(@Param("qid") int qid);

    //根据id删除单词
    @Modifying
    @Query(value = "DELETE  FROM Question WHERE word = :word",nativeQuery = true)
    public void deleteByWord(@Param("word") String word);

    //根据word查询Question表
    @Query(value = "SELECT *  FROM Question WHERE word = :word",nativeQuery = true)
    public Question getQuestionByWord(@Param("word")String word);

    //根据level查询Question表 只返回第一条数据
    @Query(value = "SELECT *  FROM Question WHERE level = :level LIMIT 1",nativeQuery = true)
    public Question getQuestionByLevel(@Param("level")int level);


    //下面是项目初期的一些测试
    //为 @Query 注解传递参数的方式1: 命名参数的方式.
//    @Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email = :email")
//    List<Person> testQueryAnnotationParams2(@Param("email") String email, @Param("lastName") String lastName);


    //随机查询出一组题目X个
    @Query(value = "SELECT * FROM Question order by rand() LIMIT 10",nativeQuery = true)
    List<Question> qryAGroupWorrds();

    //根据难度查询一组题目X个
    @Query(value = "SELECT * FROM Question WHERE level = :level order by rand() LIMIT 10",nativeQuery = true)
    List<Question> qryAGroupWorrdsByLevel(@Param("level")int level);

    @Modifying
    @Query(value = "UPDATE Question q SET q.options = :options WHERE q.word = :word",nativeQuery = true)
    public void updateQuestionOptions(@Param("options")String options,@Param("word")String word);

}
