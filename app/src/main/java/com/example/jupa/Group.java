package com.example.jupa;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Group implements Parcelable {

    @SerializedName("group_name")
    String group_name;

    @SerializedName("admin_id")
    int group_admin;

    @SerializedName("group_id")
    Integer Id;

    @SerializedName("group_code")
    String group_code;


    public Group(String group_name, String group_code) {
        this.group_name = group_name;
        this.group_code = group_code;
    }

    protected Group(Parcel in) {
        group_name = in.readString();
        group_admin = in.readInt();
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        group_code = in.readString();
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

    public int getGroup_admin() {
        return group_admin;
    }

    public void setGroup_admin(int group_admin) {
        this.group_admin = group_admin;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(group_name);
        parcel.writeInt(group_admin);
        if (Id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(Id);
        }
        parcel.writeString(group_code);
    }
}
