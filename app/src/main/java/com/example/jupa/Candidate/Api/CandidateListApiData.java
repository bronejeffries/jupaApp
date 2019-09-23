package com.example.jupa.Candidate.Api;

import com.example.jupa.Candidate.Candidate;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CandidateListApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("data")
    ArrayList<Candidate> candidateArrayList;


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

    public ArrayList<Candidate> getCandidateArrayList() {
        return candidateArrayList;
    }

    public void setCandidateArrayList(ArrayList<Candidate> candidateArrayList) {
        this.candidateArrayList = candidateArrayList;
    }
}
