package com.example.jupa.Institution.Api;

import com.example.jupa.Institution.Institution;
import com.example.jupa.Question.Api.ApiObject;
import com.google.gson.annotations.SerializedName;

public class InstitutionApiData extends ApiObject {

    @SerializedName("data")
    Institution institution;

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
}
