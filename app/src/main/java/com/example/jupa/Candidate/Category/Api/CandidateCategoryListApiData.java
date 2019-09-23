package com.example.jupa.Candidate.Category.Api;

import com.example.jupa.Candidate.Category.CandidateCategory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CandidateCategoryListApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    Integer success;

    @SerializedName("data")
    ArrayList<CandidateCategory> candidateCategoryArrayList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public ArrayList<CandidateCategory> getCandidateCategoryArrayList() {
        return candidateCategoryArrayList;
    }

    public void setCandidateCategoryArrayList(ArrayList<CandidateCategory> candidateCategoryArrayList) {
        this.candidateCategoryArrayList = candidateCategoryArrayList;
    }
}
