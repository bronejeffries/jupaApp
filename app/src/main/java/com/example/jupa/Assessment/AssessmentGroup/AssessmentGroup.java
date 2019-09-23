package com.example.jupa.Assessment.AssessmentGroup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AssessmentGroup implements Parcelable {


    @SerializedName("project_id")
    Integer project_id;

    @SerializedName("name")
    String name;

    @SerializedName("id")
    int id;

    @SerializedName("assessor_id")
    int assessor_id;

    public AssessmentGroup(Integer project_id, String name, int assessor_id) {
        this.project_id = project_id;
        this.name = name;
        this.assessor_id = assessor_id;
    }

    protected AssessmentGroup(Parcel in) {
        if (in.readByte() == 0) {
            project_id = null;
        } else {
            project_id = in.readInt();
        }
        name = in.readString();
        id = in.readInt();
        assessor_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (project_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(project_id);
        }
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(assessor_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AssessmentGroup> CREATOR = new Creator<AssessmentGroup>() {
        @Override
        public AssessmentGroup createFromParcel(Parcel in) {
            return new AssessmentGroup(in);
        }

        @Override
        public AssessmentGroup[] newArray(int size) {
            return new AssessmentGroup[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAssessor_id() {
        return assessor_id;
    }

    public void setAssessor_id(int assessor_id) {
        this.assessor_id = assessor_id;
    }
}
