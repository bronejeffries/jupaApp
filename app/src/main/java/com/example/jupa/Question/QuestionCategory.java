package com.example.jupa.Question;

import com.google.gson.annotations.SerializedName;

public class QuestionCategory {


    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("group_id")
    int group_id;

    @SerializedName("status")
    int status;

    public QuestionCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
