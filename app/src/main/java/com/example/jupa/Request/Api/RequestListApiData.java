package com.example.jupa.Request.Api;

import com.example.jupa.Question.Api.ApiObject;
import com.example.jupa.Request.RequestApplicationObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestListApiData extends ApiObject {

    @SerializedName("data")
    ArrayList<RequestApplicationObject> requestApplicationsArrayList;

    public ArrayList<RequestApplicationObject> getRequestApplicationsArrayList() {
        return requestApplicationsArrayList;
    }

    public void setRequestApplicationsArrayList(ArrayList<RequestApplicationObject> requestApplicationsArrayList) {
        this.requestApplicationsArrayList = requestApplicationsArrayList;
    }
}
