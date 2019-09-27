package com.example.jupa.Assessment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Assessment implements Parcelable {

    @SerializedName("assessment_id")
    Integer assessment_id;

    @SerializedName("assessment_group_id")
    Integer assessment_group_id;

    @SerializedName("question_id")
    Integer question_id;

    @SerializedName("grade")
    String grade;

    @SerializedName("remarks")
    String other_remarks;

    @SerializedName("candidate_id")
    Integer candidate_id;

    public Assessment(Integer assessment_group_id, Integer question_id, String grade, String other_remarks, Integer candidate_id) {

        this.assessment_group_id = assessment_group_id;

        this.question_id = question_id;

        this.grade = grade;

        this.other_remarks = other_remarks;

        this.candidate_id = candidate_id;


    }


    protected Assessment(Parcel in) {
        if (in.readByte() == 0) {
            assessment_id = null;
        } else {
            assessment_id = in.readInt();
        }
        if (in.readByte() == 0) {
            assessment_group_id = null;
        } else {
            assessment_group_id = in.readInt();
        }
        if (in.readByte() == 0) {
            question_id = null;
        } else {
            question_id = in.readInt();
        }
        grade = in.readString();
        other_remarks = in.readString();
        if (in.readByte() == 0) {
            candidate_id = null;
        } else {
            candidate_id = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (assessment_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(assessment_id);
        }
        if (assessment_group_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(assessment_group_id);
        }
        if (question_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(question_id);
        }
        dest.writeString(grade);
        dest.writeString(other_remarks);
        if (candidate_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(candidate_id);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Assessment> CREATOR = new Creator<Assessment>() {
        @Override
        public Assessment createFromParcel(Parcel in) {
            return new Assessment(in);
        }

        @Override
        public Assessment[] newArray(int size) {
            return new Assessment[size];
        }
    };

    public Integer getAssessment_group_id() {
        return assessment_group_id;
    }

    public void setAssessment_group_id(Integer assessment_group_id) {
        this.assessment_group_id = assessment_group_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOther_remarks() {
        return other_remarks;
    }

    public void setOther_remarks(String other_remarks) {
        this.other_remarks = other_remarks;
    }

    public Integer getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(Integer candidate_id) {
        this.candidate_id = candidate_id;
    }
}
