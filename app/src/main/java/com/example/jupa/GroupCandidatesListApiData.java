package com.example.jupa;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupCandidatesListApiData {

    @SerializedName("success")
    String success;

    @SerializedName("data")
    ArrayList<Candidate> groupCandidates;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<Candidate> getGroupCandidates() {
        return groupCandidates;
    }

    public void setGroupCandidates(ArrayList<Candidate> groupCandidates) {
        this.groupCandidates = groupCandidates;
    }
}
