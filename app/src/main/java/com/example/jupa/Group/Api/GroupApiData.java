package com.example.jupa.Group.Api;

import com.example.jupa.Group.Group;
import com.google.gson.annotations.SerializedName;

public class GroupApiData {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("data")
    Group group;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
