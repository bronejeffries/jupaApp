package com.example.jupa.Question.Api.QuestionCategory;

import com.example.jupa.Question.Api.ApiObject;
import com.example.jupa.Question.QuestionCategory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionCategoriesListApiData extends ApiObject {


    @SerializedName("data")
    ArrayList<QuestionCategory> questionCategoryArrayList;

    public ArrayList<QuestionCategory> getQuestionCategoryArrayList() {
        return questionCategoryArrayList;
    }

    public void setQuestionCategoryArrayList(ArrayList<QuestionCategory> questionCategoryArrayList) {
        this.questionCategoryArrayList = questionCategoryArrayList;
    }
}
