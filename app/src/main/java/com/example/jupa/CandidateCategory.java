package com.example.jupa;

import com.google.gson.annotations.SerializedName;

public class CandidateCategory {

    @SerializedName("category_id")
    Integer id;

    @SerializedName("category_name")
    String name;

    @SerializedName("category_code")
    String code;

    @SerializedName("category_status")
    String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
