package com.example.jupa.Assessment;

import android.os.Parcel;
import android.os.Parcelable;

public class Assessment implements Parcelable {

    Integer assessment_id;

    Integer assessment_group_id;

    Integer question_id;

    String grade;

    String other_remarks;

    public Assessment(Integer assessment_group_id, Integer question_id, String grade, String other_remarks) {
        this.assessment_group_id = assessment_group_id;
        this.question_id = question_id;
        this.grade = grade;
        this.other_remarks = other_remarks;
    }

    public Integer getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(Integer assessment_id) {
        this.assessment_id = assessment_id;
    }

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
}
