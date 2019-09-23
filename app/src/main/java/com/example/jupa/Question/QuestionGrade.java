package com.example.jupa.Question;

public class QuestionGrade {

    String name;
    Integer awarded_marks;

    public QuestionGrade(String name, Integer awarded_marks) {
        this.name = name;
        this.awarded_marks = awarded_marks;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAwarded_marks() {
        return awarded_marks;
    }

    public void setAwarded_marks(Integer awarded_marks) {
        this.awarded_marks = awarded_marks;
    }


}
