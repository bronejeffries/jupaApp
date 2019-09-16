package com.example.jupa;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Group implements Parcelable {

    String group_name,group_admin;
    Integer Id;

    ArrayList<Candidate> mansoryList;
    ArrayList<Candidate> professionalList;

    public Group(String group_name, Integer id, @Nullable String group_admin, @Nullable ArrayList<Candidate> mansoryList, @Nullable ArrayList<Candidate> professionalList) {
        this.group_name = group_name;
        Id = id;
        this.group_admin = group_admin;
        this.mansoryList = mansoryList;
        this.professionalList = professionalList;
    }

    protected Group(Parcel in) {
        group_name = in.readString();
        group_admin = in.readString();
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getGroup_admin() {
        return group_admin;
    }

    public void setGroup_admin(String group_admin) {
        this.group_admin = group_admin;
    }

    public ArrayList<Candidate> getMansoryList() {
        return mansoryList;
    }

    public void setMansoryList(ArrayList<Candidate> mansoryList) {
        this.mansoryList = mansoryList;
    }

    public ArrayList<Candidate> getProfessionalList() {
        return professionalList;
    }

    public void setProfessionalList(ArrayList<Candidate> professionalList) {
        this.professionalList = professionalList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(group_name);
        parcel.writeString(group_admin);
        if (Id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(Id);
        }
    }
}
