package com.kg.xiaosha.bysj_english.bean;


/**题目编号  题目  选项   答案  全是String类型
 * @author xiaosha
 * @create 2019-02-15 9:30
 */
public class QuestionMessage {

/*    "questionId":"1",
            "questionTitle":"下丘脑与腺垂体之间主要通过下列哪条途径联系？ ",
            "questionItems":"神经纤维;神经纤维和门脉系统;垂体门脉系统;垂体束;轴浆运输",
            "questionAnswer":"c"},*/
    private String questionId;//题目编号
    private String questionTitle;//questionTitle
    private String questionItems;//选项
    private String questionAnswer;//答案  只有abcd

    public QuestionMessage() {
    }

    public QuestionMessage(String questionId, String questionTitle, String questionItems, String questionAnswer) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionItems = questionItems;
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(String questionItems) {
        this.questionItems = questionItems;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
