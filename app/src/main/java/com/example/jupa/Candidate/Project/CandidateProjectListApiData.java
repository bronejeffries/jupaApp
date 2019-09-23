package com.example.jupa.Candidate.Project;

import com.example.jupa.Candidate.Project.CandidateProject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CandidateProjectListApiData {

    @SerializedName("success")
    String success;

    @SerializedName("data")
    ArrayList<CandidateProject> candidateProjectArrayList;

    @SerializedName("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<CandidateProject> getCandidateProjectArrayList() {
        return candidateProjectArrayList;
    }

    public void setCandidateProjectArrayList(ArrayList<CandidateProject> candidateProjectArrayList) {
        this.candidateProjectArrayList = candidateProjectArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
