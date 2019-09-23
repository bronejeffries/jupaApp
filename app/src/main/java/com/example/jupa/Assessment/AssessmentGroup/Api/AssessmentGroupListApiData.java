package com.example.jupa.Assessment.AssessmentGroup.Api;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AssessmentGroupListApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("data")
    ArrayList<AssessmentGroup> assessmentGroupArrayList;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<AssessmentGroup> getAssessmentGroupArrayList() {
        return assessmentGroupArrayList;
    }

    public void setAssessmentGroupArrayList(ArrayList<AssessmentGroup> assessmentGroupArrayList) {
        this.assessmentGroupArrayList = assessmentGroupArrayList;
    }
}
