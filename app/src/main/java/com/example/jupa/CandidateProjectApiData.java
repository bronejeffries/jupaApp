package com.example.jupa;

import com.google.gson.annotations.SerializedName;

public class CandidateProjectApiData {

    @SerializedName("success")
    String success;

    @SerializedName("data")
    CandidateProject candidateProject;

    @SerializedName("message")
    String message;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public CandidateProject getCandidateProject() {
        return candidateProject;
    }

    public void setCandidateProject(CandidateProject candidateProject) {
        this.candidateProject = candidateProject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
