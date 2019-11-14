package com.example.jupa.Request;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.example.jupa.Candidate.Candidate;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestApplicationObject implements Parcelable {

    @SerializedName("application_id")
    int request_id;

    @SerializedName("request_type")
    String  request_type;

    @SerializedName("candidate_id")
    int candidate_id;

    @SerializedName("reg_no")
    String regNo;

    @SerializedName("experience")
    String experience;

    @SerializedName("awards")
    String qualification;

    @SerializedName("reason")
    String Reason;

    @SerializedName("group_id")
    int group_id;

    @SerializedName("status")
    int status;

    @SerializedName("rank_id")
    Integer rank_id;

    @SerializedName("institution_id")
    Integer institution_id;

    @SerializedName("record_date")
    String record_date;

    @SerializedName("user_id")
    Integer user_id;

    @SerializedName("comment")
    String comment;

    @SerializedName("candidate")
    Candidate candidate;

    @SerializedName("isPaid")
    Boolean paid;

    @SerializedName("amount")
    Integer amount;




    public RequestApplicationObject(String request_type, int candidate_id, String regNo, String experience, String qualification, String reason, int group_id, int status, @Nullable Integer rank_id, @Nullable Integer institution_id) {

        this.request_type = request_type;
        this.candidate_id = candidate_id;
        this.regNo = regNo;
        this.experience = experience;
        this.qualification = qualification;
        Reason = reason;
        this.group_id = group_id;
        this.status = status;
        this.rank_id = rank_id;
        this.institution_id = institution_id;

    }


    protected RequestApplicationObject(Parcel in) {
        request_id = in.readInt();
        request_type = in.readString();
        candidate_id = in.readInt();
        regNo = in.readString();
        experience = in.readString();
        qualification = in.readString();
        Reason = in.readString();
        group_id = in.readInt();
        status = in.readInt();
        if (in.readByte() == 0) {
            rank_id = null;
        } else {
            rank_id = in.readInt();
        }
        if (in.readByte() == 0) {
            institution_id = null;
        } else {
            institution_id = in.readInt();
        }
        record_date = in.readString();
        if (in.readByte() == 0) {
            user_id = null;
        } else {
            user_id = in.readInt();
        }
        comment = in.readString();
        candidate = in.readParcelable(Candidate.class.getClassLoader());
        byte tmpPaid = in.readByte();
        paid = tmpPaid == 0 ? null : tmpPaid == 1;
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(request_id);
        dest.writeString(request_type);
        dest.writeInt(candidate_id);
        dest.writeString(regNo);
        dest.writeString(experience);
        dest.writeString(qualification);
        dest.writeString(Reason);
        dest.writeInt(group_id);
        dest.writeInt(status);
        if (rank_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rank_id);
        }
        if (institution_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(institution_id);
        }
        dest.writeString(record_date);
        if (user_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(user_id);
        }
        dest.writeString(comment);
        dest.writeParcelable(candidate, flags);
        dest.writeByte((byte) (paid == null ? 0 : paid ? 1 : 2));
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(amount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RequestApplicationObject> CREATOR = new Creator<RequestApplicationObject>() {
        @Override
        public RequestApplicationObject createFromParcel(Parcel in) {
            return new RequestApplicationObject(in);
        }

        @Override
        public RequestApplicationObject[] newArray(int size) {
            return new RequestApplicationObject[size];
        }
    };

    public Boolean getPaid() {
        return paid!=null?paid:false;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRank_id() {
        return rank_id;
    }

    public void setRank_id(Integer rank_id) {
        this.rank_id = rank_id;
    }

    public Integer getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(Integer institution_id) {
        this.institution_id = institution_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPaymentStatus(){

        return getPaid()?"Cleared":"Pending";
    }
}
