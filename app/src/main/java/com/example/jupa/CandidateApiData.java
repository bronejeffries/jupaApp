package com.example.jupa;

import com.google.gson.annotations.SerializedName;

public class CandidateApiData {

    @SerializedName("success")
    String success;

    @SerializedName("data")
    Candidate candidate;

    @SerializedName("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
