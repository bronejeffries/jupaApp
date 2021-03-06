package com.example.jupa.Candidate;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Group.Group;
import com.example.jupa.Skill.Skill;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class Candidate implements Parcelable {

    @SerializedName("candidate_id")
    Integer id;

    @SerializedName("reg_no")
    String registration_no;

    @SerializedName("first_name")
    String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("family_name")
    String family_name;

    @SerializedName("gender")
    String gender;

    @SerializedName("candidate_email")
    String email;

    @SerializedName("dob")
    String date_of_birth;

    @SerializedName("mobile_number")
    String mobile_number;

    @SerializedName("other_number")
    String other_number;

    @SerializedName("candidate_photo")
    String photo_url;

    @SerializedName("available")
    String available;

    @SerializedName("date_available")
    String date_available;

    @SerializedName("address")
    String address;

    @SerializedName("member_level")
    String role;

    @SerializedName("category_id")
    Integer category_id;

    @SerializedName("education")
    String education;

    @SerializedName("enroll_status")
    String status;

    @SerializedName("group_id")
    Integer group;

    @SerializedName("rank_id")
    Integer rank_id;

    @SerializedName("country_id")
    Integer country_id;

    @SerializedName("state_id")
    Integer state_id;

    @SerializedName("city_id")
    Integer city_id;

    @SerializedName("institution_id")
    Integer institution_id;

    @SerializedName("association_name")
    String association_name;

    @SerializedName("association_reg_date")
    String association_reg_date;

    @SerializedName("association_address")
    String association_address;

    @SerializedName("association_id")
    Integer association_id;

    String  file_body;

    ArrayList<Skill> candidateSkills;

    Group Candidate_group;

    ArrayList<CandidateProject> CandidateProjectsList = new ArrayList<>();



    public Candidate(@Nullable Integer id, String registration_no, String first_name, String last_name, String family_name, String gender, String email, String date_of_birth, String mobile_number, String other_number, String photo_url, String address, String education, Integer group, Integer category_id ,@Nullable Integer rank_id,@Nullable Integer country_id,@Nullable Integer state_id, @Nullable Integer city_id, @Nullable Integer association_id, @Nullable String association_name, @Nullable String association_reg_date,@Nullable String association_address) {
        this.id = id;
        this.registration_no = registration_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.family_name = family_name;
        this.gender = gender;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.mobile_number = mobile_number;
        this.other_number = other_number;
        this.photo_url = photo_url;
        this.address = address;
        this.education = education;
        this.group = group;
        this.rank_id = rank_id;
        this.country_id = country_id;
        this.state_id = state_id;
        this.city_id = city_id;
        this.category_id = category_id;
        this.association_id = association_id;
        this.association_name = association_name;
        this.association_reg_date = association_reg_date;
        this.association_address = association_address;
    }


    protected Candidate(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        registration_no = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        family_name = in.readString();
        gender = in.readString();
        email = in.readString();
        date_of_birth = in.readString();
        mobile_number = in.readString();
        other_number = in.readString();
        photo_url = in.readString();
        available = in.readString();
        date_available = in.readString();
        address = in.readString();
        role = in.readString();
        if (in.readByte() == 0) {
            category_id = null;
        } else {
            category_id = in.readInt();
        }
        education = in.readString();
        status = in.readString();
        if (in.readByte() == 0) {
            group = null;
        } else {
            group = in.readInt();
        }
        if (in.readByte() == 0) {
            rank_id = null;
        } else {
            rank_id = in.readInt();
        }
        if (in.readByte() == 0) {
            country_id = null;
        } else {
            country_id = in.readInt();
        }
        if (in.readByte() == 0) {
            state_id = null;
        } else {
            state_id = in.readInt();
        }
        if (in.readByte() == 0) {
            city_id = null;
        } else {
            city_id = in.readInt();
        }
        if (in.readByte() == 0) {
            institution_id = null;
        } else {
            institution_id = in.readInt();
        }
        association_name = in.readString();
        association_reg_date = in.readString();
        association_address = in.readString();
        if (in.readByte() == 0) {
            association_id = null;
        } else {
            association_id = in.readInt();
        }
        file_body = in.readString();
        candidateSkills = in.createTypedArrayList(Skill.CREATOR);
        Candidate_group = in.readParcelable(Group.class.getClassLoader());
        CandidateProjectsList = in.createTypedArrayList(CandidateProject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(registration_no);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(family_name);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeString(date_of_birth);
        dest.writeString(mobile_number);
        dest.writeString(other_number);
        dest.writeString(photo_url);
        dest.writeString(available);
        dest.writeString(date_available);
        dest.writeString(address);
        dest.writeString(role);
        if (category_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(category_id);
        }
        dest.writeString(education);
        dest.writeString(status);
        if (group == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(group);
        }
        if (rank_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rank_id);
        }
        if (country_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(country_id);
        }
        if (state_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(state_id);
        }
        if (city_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(city_id);
        }
        if (institution_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(institution_id);
        }
        dest.writeString(association_name);
        dest.writeString(association_reg_date);
        dest.writeString(association_address);
        if (association_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(association_id);
        }
        dest.writeString(file_body);
        dest.writeTypedList(candidateSkills);
        dest.writeParcelable(Candidate_group, flags);
        dest.writeTypedList(CandidateProjectsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Candidate> CREATOR = new Creator<Candidate>() {
        @Override
        public Candidate createFromParcel(Parcel in) {
            return new Candidate(in);
        }

        @Override
        public Candidate[] newArray(int size) {
            return new Candidate[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getOther_number() {
        return other_number;
    }

    public void setOther_number(String other_number) {
        this.other_number = other_number;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDate_available() {
        return date_available;
    }

    public void setDate_available(String date_available) {
        this.date_available = date_available;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getRank_id() {
        return rank_id;
    }

    public void setRank_id(Integer rank_id) {
        this.rank_id = rank_id;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public ArrayList<Skill> getCandidateSkills() {
        return candidateSkills;
    }

    public void setCandidateSkills(ArrayList<Skill> candidateSkills) {
        this.candidateSkills = candidateSkills;
    }

    public Group getCandidate_group() {
        return Candidate_group;
    }

    public void setCandidate_group(Group candidate_group) {
        Candidate_group = candidate_group;
    }

    public ArrayList<CandidateProject> getCandidateProjectsList() {
        return CandidateProjectsList;
    }

    public void setCandidateProjectsList(ArrayList<CandidateProject> candidateProjectsList) {
        CandidateProjectsList = candidateProjectsList;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName(){
        return this.getFirst_name()+" "+this.getLast_name()+" "+this.getFamily_name();
    }

    public Integer getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(Integer institution_id) {
        this.institution_id = institution_id;
    }

    public String getAssociation_name() {
        return association_name;
    }

    public void setAssociation_name(String association_name) {
        this.association_name = association_name;
    }

    public String getAssociation_reg_date() {
        return association_reg_date;
    }

    public void setAssociation_reg_date(String association_reg_date) {
        this.association_reg_date = association_reg_date;
    }

    public Integer getAssociation_id() {
        return association_id;
    }

    public void setAssociation_id(Integer association_id) {
        this.association_id = association_id;
    }

    public String getAssociation_address() {
        return association_address;
    }

    public void setAssociation_address(String association_address) {
        this.association_address = association_address;
    }

    public String  getFile_body() {
        return this.file_body;
    }

    public void setFile_body(String  file_body) {
        this.file_body = file_body;
    }
}

