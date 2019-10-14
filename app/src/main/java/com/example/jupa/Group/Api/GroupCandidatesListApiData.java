package com.example.jupa.Group.Api;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Question.Api.ApiObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupCandidatesListApiData extends ApiObject {

    @SerializedName("data")
    ArrayList<Candidate> groupCandidates;

    public ArrayList<Candidate> getGroupCandidates() {
        return groupCandidates;
    }

    public void setGroupCandidates(ArrayList<Candidate> groupCandidates) {
        this.groupCandidates = groupCandidates;
    }
}
