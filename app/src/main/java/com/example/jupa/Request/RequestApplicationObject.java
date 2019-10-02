package com.example.jupa.Request;

public class RequestApplicationObject {

    int request_id;

    int candidate_id;

    String regNo;

    String experience;

    String qualification;

    String Reason;


    public RequestApplicationObject(int candidate_id, String regNo, String experience, String qualification, String reason) {
        this.candidate_id = candidate_id;
        this.regNo = regNo;
        this.experience = experience;
        this.qualification = qualification;
        Reason = reason;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
