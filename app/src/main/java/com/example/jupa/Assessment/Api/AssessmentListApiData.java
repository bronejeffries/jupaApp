package com.example.jupa.Assessment.Api;

import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Question.Api.ApiObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AssessmentListApiData extends ApiObject {


    @SerializedName("data")
    ArrayList<Assessment> assessmentArrayList;


    public ArrayList<Assessment> getAssessmentArrayList() {
        return assessmentArrayList;
    }

    public void setAssessmentArrayList(ArrayList<Assessment> assessmentArrayList) {
        this.assessmentArrayList = assessmentArrayList;
    }
}
