package com.example.jupa.Question.Api.Question;

import com.example.jupa.Question.Api.ApiObject;
import com.example.jupa.Question.Question;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionsListApiData extends ApiObject {


    @SerializedName("data")
    ArrayList<Question> questionArrayList;


    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public void setQuestionArrayList(ArrayList<Question> questionArrayList) {
        this.questionArrayList = questionArrayList;
    }
}

