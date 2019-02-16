package com.kg.xiaosha.bysj_english.dao;

import com.kg.xiaosha.bysj_english.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
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

/*    @Modifying
    @Query("UPDATE Person p SET p.lastName = :lastName WHERE id = :id")
    void updatePersonName(@Param("id") Integer id, @Param("lastName") String lastName);*/


    //新增数据
    @Modifying
   public Question save(Question question);

    /**
     * 根据word更新Question表
     */
    @Modifying
    @Query(value = "UPDATE Question q SET q.answer = :answer,q.level = :level,q.options = :options WHERE q.word = :word",nativeQuery = true)
    public void updateQuestion(@Param("answer")String answer,@Param("level")int level,@Param("options")String options,@Param("word")String word);

    //根据 qid 来获取对应的单词
    Question getByQid(int qid);

    //分页查询
    @Query(value = "SELECT * FROM Question LIMIT :PageNo,:PageSize",nativeQuery = true)
    List<Question> queryQuestionPage(@Param("PageNo") int PageNo, @Param("PageSize") int PageSize);

    //查询数据表总的记录数
    @Query(value = "SELECT COUNT(*) FROM Question",nativeQuery = true)
    public int queryNumber();

    //根据id删除单词
    @Modifying
    @Query(value = "DELETE  FROM Question WHERE qid = :qid",nativeQuery = true)
    public void deleteByQid(@Param("qid") int qid);

    //根据word查询Question表
    @Query(value = "SELECT *  FROM Question WHERE word = :word LIMIT 1",nativeQuery = true)
    public Question getQuestionByWord(@Param("word")String word);

    //根据level查询Question表 只返回第一条数据
    @Query(value = "SELECT *  FROM Question WHERE level = :level LIMIT 1",nativeQuery = true)
    public Question getQuestionByLevel(@Param("level")int level);


    //下面是项目初期的一些测试

    //为 @Query 注解传递参数的方式1: 命名参数的方式.
//    @Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email = :email")
//    List<Person> testQueryAnnotationParams2(@Param("email") String email, @Param("lastName") String lastName);


    //查询出一组题目2个
    @Query(value = "SELECT * FROM Question order by rand() LIMIT 2",nativeQuery = true)
    List<Question> qryAGroupWorrds();
}
