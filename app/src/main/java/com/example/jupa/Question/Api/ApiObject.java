package com.example.jupa.Question.Api;

import com.google.gson.annotations.SerializedName;

public class ApiObject {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;


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
}
