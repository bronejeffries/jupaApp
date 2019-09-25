package com.example.jupa.Question.Api.QuestionCategory;

import com.example.jupa.Question.Api.ApiObject;
import com.example.jupa.Question.QuestionCategory;
import com.google.gson.annotations.SerializedName;

public class QuestionCategoryApiData extends ApiObject {


    @SerializedName("data")
    QuestionCategory questionCategory;


    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }
}
