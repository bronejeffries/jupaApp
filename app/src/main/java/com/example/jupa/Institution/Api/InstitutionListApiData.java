package com.example.jupa.Institution.Api;

import com.example.jupa.Institution.Institution;
import com.example.jupa.Question.Api.ApiObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InstitutionListApiData extends ApiObject {

    @SerializedName("data")
    ArrayList<Institution> institutionArrayList;

    public ArrayList<Institution> getInstitutionArrayList() {
        return institutionArrayList;
    }

    public void setInstitutionArrayList(ArrayList<Institution> institutionArrayList) {
        this.institutionArrayList = institutionArrayList;
    }
}
