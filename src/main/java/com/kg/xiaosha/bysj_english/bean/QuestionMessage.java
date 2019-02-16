package com.kg.xiaosha.bysj_english.bean;


/**
 * @author xiaosha
 * @create 2019-02-15 9:30
 */
public class QuestionMessage {

    private String word;
    private String[] options;
    private String answer;
    private int level;
    private String pronounce;

    public QuestionMessage() {
    }

    public QuestionMessage(String word, String[] options, String answer, int level, String pronounce) {
        this.word = word;
        this.options = options;
        this.answer = answer;
        this.level = level;
        this.pronounce = pronounce;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

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
}
