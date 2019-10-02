package com.example.jupa.Request.RequestType;

import com.example.jupa.Request.RequestApplicationObject;

public class AssessorRequest extends RequestApplicationObject {

    int institution_id;


    public AssessorRequest(int candidate_id, String regNo, String experience, String qualification, String reason, int institution_id) {

        super(candidate_id, regNo, experience, qualification, reason);
        this.institution_id = institution_id;

    }

    public int getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(int institution_id) {
        this.institution_id = institution_id;
    }
}
