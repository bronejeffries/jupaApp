package com.example.jupa.Request.Api;

import com.example.jupa.Question.Api.ApiObject;
import com.example.jupa.Request.RequestApplicationObject;
import com.google.gson.annotations.SerializedName;

public class RequestApiData extends ApiObject {

    @SerializedName("data")
    RequestApplicationObject requestApplication;

    public RequestApplicationObject getRequestApplication() {
        return requestApplication;
    }

    public void setRequestApplication(RequestApplicationObject requestApplication) {
        this.requestApplication = requestApplication;
    }
}
