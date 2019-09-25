package com.example.jupa.Question.Api.Question;

import com.example.jupa.Question.Api.ApiObject;
import com.example.jupa.Question.Question;
import com.google.gson.annotations.SerializedName;

public class QuestionApiData extends ApiObject {


    @SerializedName("data")
    Question question;



    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
