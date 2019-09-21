package com.example.jupa;

import com.google.gson.annotations.SerializedName;

public class CandidateCategoryApiData {

    @SerializedName("success")
    String success;

    @SerializedName("data")
    CandidateCategory candidateCategory;

    @SerializedName("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public CandidateCategory getCandidateCategory() {
        return candidateCategory;
    }

    public void setCandidateCategory(CandidateCategory candidateCategory) {
        this.candidateCategory = candidateCategory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
