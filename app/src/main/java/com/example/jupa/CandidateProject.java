package com.example.jupa;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class CandidateProject implements Parcelable {

    @SerializedName("project_id")
    Integer project_id;

    @SerializedName("candidate_id")
    Integer candidate_id;

    @SerializedName("title")
    String title;

    @SerializedName("location")
    String location;

    @SerializedName("publication_date")
    String date_of_completion;

    @SerializedName("client_name")
    String client_name;

    @SerializedName("client_contact")
    String client_contact;

    @SerializedName("client_email")
    String client_email;

    @SerializedName("client_address")
    String client_address;

    @SerializedName("descrp")
    String description;

    @SerializedName("project_status")
    Integer status;

    @SerializedName("project_photo")
    String project_photo;

    public CandidateProject(@Nullable Integer project_id, Integer candidate_id, String title, String location, String date_of_completion, String client_name, String client_contact, String client_email, String client_address, String description, Integer status, @Nullable String project_photo) {
        this.project_id = project_id;
        this.candidate_id = candidate_id;
        this.title = title;
        this.location = location;
        this.date_of_completion = date_of_completion;
        this.client_name = client_name;
        this.client_contact = client_contact;
        this.client_email = client_email;
        this.client_address = client_address;
        this.description = description;
        this.status = status;
        this.project_photo = project_photo;
    }

    protected CandidateProject(Parcel in) {
        if (in.readByte() == 0) {
            project_id = null;
        } else {
            project_id = in.readInt();
        }
        if (in.readByte() == 0) {
            candidate_id = null;
        } else {
            candidate_id = in.readInt();
        }
        title = in.readString();
        location = in.readString();
        date_of_completion = in.readString();
        client_name = in.readString();
        client_contact = in.readString();
        client_email = in.readString();
        client_address = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        project_photo = in.readString();
    }

    public static final Creator<CandidateProject> CREATOR = new Creator<CandidateProject>() {
        @Override
        public CandidateProject createFromParcel(Parcel in) {
            return new CandidateProject(in);
        }

        @Override
        public CandidateProject[] newArray(int size) {
            return new CandidateProject[size];
        }
    };

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(Integer candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate_of_completion() {
        return date_of_completion;
    }

    public void setDate_of_completion(String date_of_completion) {
        this.date_of_completion = date_of_completion;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(String client_contact) {
        this.client_contact = client_contact;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProject_photo() {
        return project_photo;
    }

    public void setProject_photo(String project_photo) {
        this.project_photo = project_photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (project_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(project_id);
        }
        if (candidate_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(candidate_id);
        }
        parcel.writeString(title);
        parcel.writeString(location);
        parcel.writeString(date_of_completion);
        parcel.writeString(client_name);
        parcel.writeString(client_contact);
        parcel.writeString(client_email);
        parcel.writeString(client_address);
        parcel.writeString(description);
        if (status == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(status);
        }
        parcel.writeString(project_photo);
    }
}
