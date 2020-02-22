package com.example.jupa.Candidate.Project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import okhttp3.RequestBody;

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

    String file_body;

    Boolean verified;

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
        file_body = in.readString();
        byte tmpVerified = in.readByte();
        verified = tmpVerified == 0 ? null : tmpVerified == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (project_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(project_id);
        }
        if (candidate_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(candidate_id);
        }
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(date_of_completion);
        dest.writeString(client_name);
        dest.writeString(client_contact);
        dest.writeString(client_email);
        dest.writeString(client_address);
        dest.writeString(description);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(project_photo);
        dest.writeString(file_body);
        dest.writeByte((byte) (verified == null ? 0 : verified ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getFile_body() {

        return file_body;
    }

    public void setFile_body(String  file_body) {
        this.file_body = file_body;
    }

}
