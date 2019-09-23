package com.example.jupa.Assessment.AssessmentGroup;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.google.gson.annotations.SerializedName;

public class AssessmentGroupApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("data")
    AssessmentGroup assessmentGroup;

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

    public AssessmentGroup getAssessmentGroup() {
        return assessmentGroup;
    }

    public void setAssessmentGroup(AssessmentGroup assessmentGroup) {
        this.assessmentGroup = assessmentGroup;
    }
}
