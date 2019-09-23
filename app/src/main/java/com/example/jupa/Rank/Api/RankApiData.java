package com.example.jupa.Rank.Api;

import com.example.jupa.Rank.Rank;
import com.google.gson.annotations.SerializedName;

public class RankApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("data")
    Rank rank;


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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
