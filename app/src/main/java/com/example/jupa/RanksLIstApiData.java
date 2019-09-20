package com.example.jupa;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RanksLIstApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("data")
    ArrayList<Rank> rankArrayList;

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

    public ArrayList<Rank> getRankArrayList() {
        return rankArrayList;
    }

    public void setRankArrayList(ArrayList<Rank> rankArrayList) {
        this.rankArrayList = rankArrayList;
    }
}
