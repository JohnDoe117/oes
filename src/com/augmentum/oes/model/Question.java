package com.augmentum.oes.model;

import java.sql.Date;

public class Question {
    private int id;
    private String question;
    private String desciption;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private Date creat_timeDate;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", question=" + question + ", desciption=" + desciption + ", optionA=" + optionA
                + ", optionB=" + optionB + ", optionC=" + optionC + ", optionD=" + optionD + ", answer=" + answer
                + ", creat_timeDate=" + creat_timeDate + ", update_time=" + update_time + "]";
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreat_timeDate() {
        return creat_timeDate;
    }

    public void setCreat_timeDate(Date creat_timeDate) {
        this.creat_timeDate = creat_timeDate;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

}
