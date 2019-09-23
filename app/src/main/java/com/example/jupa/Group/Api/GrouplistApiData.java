package com.example.jupa.Group.Api;

import com.example.jupa.Group.Group;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GrouplistApiData {

    @SerializedName("success")
    String success;

    @SerializedName("data")
    ArrayList<Group> groupArrayList;

    public GrouplistApiData(String success, ArrayList<Group> groupArrayList) {
        this.success = success;
        this.groupArrayList = groupArrayList;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public void setGroupArrayList(ArrayList<Group> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }
}
