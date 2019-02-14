package com.kg.xiaosha.bysj_english.entity;

import javax.persistence.*;

/**
 *         / **
 *         * @Entity：代表这个类是一个实体类
 *         * @Table（name =“question）：这个注解代表对应表的名称，question就是利用jpa要生成的表名称
 *         * @id：表示此属性是主键
 *         * @GeneratedValue：表示的是主键生成策略，默认主键是自增长的，当然你也可以用uuid
 *         * @Column：表明此属性在数据库中对应的字段，如果实体中的属性与数据库的字段一样，可以省略不写。
 *         * /
 * @author xiaosha
 * @create 2019-01-19 21:21
 */
@Entity
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qid")
    private int qid;

    @Column(name = "word")
    private String word;

    @Column(name = "options")
    private String options;

    @Column(name = "answer")
    private String answer;

    @Column(name = "level")
    private int level;

    @Column(name = "pronounce")
    private String pronounce;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public Question() {
    }

    public Question(String word, String options, String answer, int level, String pronounce) {
        this.word = word;
        this.options = options;
        this.answer = answer;
        this.level = level;
        this.pronounce = pronounce;
    }

    public int getQid() {
        return qid;
    }

    public String getWord() {
        return word;
    }

    public String getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "Question{" +
                "qid=" + qid +
                ", word='" + word + '\'' +
                ", options='" + options + '\'' +
                ", answer='" + answer + '\'' +
                ", level=" + level +
                ", pronounce='" + pronounce + '\'' +
                '}';
    }
}





