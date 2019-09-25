package com.example.jupa.Assessment.Api;

import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Question.Api.ApiObject;
import com.google.gson.annotations.SerializedName;

public class AssessmentApiData extends ApiObject {


    @SerializedName("data")
    Assessment assessment;


    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}
