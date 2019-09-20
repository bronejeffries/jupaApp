package com.example.jupa;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

class Rank implements Parcelable {

    @SerializedName("rank_name")
    String name;

    @SerializedName("rank_code")
    String code;

    @SerializedName("rank_status")
    int status;

    @SerializedName("rank_id")
    int id;


    public Rank(String name, String code) {
        this.name = name;
        this.code = code;
    }

    protected Rank(Parcel in) {
        name = in.readString();
        code = in.readString();
        status = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Rank> CREATOR = new Creator<Rank>() {
        @Override
        public Rank createFromParcel(Parcel in) {
            return new Rank(in);
        }

        @Override
        public Rank[] newArray(int size) {
            return new Rank[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(code);
        parcel.writeInt(status);
        parcel.writeInt(id);
    }
}
