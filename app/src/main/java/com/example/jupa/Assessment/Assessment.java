package com.example.jupa.Assessment;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jupa.Assessment.Adapter.FilterableObject;
import com.example.jupa.Candidate.Candidate;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class Assessment extends FilterableObject implements Parcelable {

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

    @SerializedName("project_id")
    Integer project_id;

    @SerializedName("assessor_id")
    Integer assessor_id;

    @SerializedName("institution_id")
    Integer institution_id;

    @SerializedName("institute_remarks")
    String institute_remarks;

    public Assessment(Integer assessment_group_id, Integer question_id, String grade, String other_remarks, Integer candidate_id, Integer project_id, Integer assessor_id, Integer institution_id) {
        this.assessment_group_id = assessment_group_id;
        this.question_id = question_id;
        this.grade = grade;
        this.other_remarks = other_remarks;
        this.candidate_id = candidate_id;
        this.project_id = project_id;
        this.assessor_id = assessor_id;
        this.institution_id = institution_id;
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
        if (in.readByte() == 0) {
            project_id = null;
        } else {
            project_id = in.readInt();
        }
        if (in.readByte() == 0) {
            assessor_id = null;
        } else {
            assessor_id = in.readInt();
        }
        if (in.readByte() == 0) {
            institution_id = null;
        } else {
            institution_id = in.readInt();
        }
        institute_remarks = in.readString();
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
        if (project_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(project_id);
        }
        if (assessor_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(assessor_id);
        }
        if (institution_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(institution_id);
        }
        dest.writeString(institute_remarks);
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

    public Integer getAssessor_id() {
        return assessor_id;
    }

    public void setAssessor_id(Integer assessor_id) {
        this.assessor_id = assessor_id;
    }

    public Integer getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(Integer institution_id) {
        this.institution_id = institution_id;
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

    public Integer getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(Integer candidate_id) {
        this.candidate_id = candidate_id;
    }

    public Integer getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(Integer assessment_id) {
        this.assessment_id = assessment_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getInstitute_remarks() {

        return institute_remarks;
    }

    public void setInstitute_remarks(String institute_remarks) {
        this.institute_remarks = institute_remarks;
    }
}
