package com.example.jupa.Skill;

import android.os.Parcel;
import android.os.Parcelable;

public class Skill implements Parcelable {

    String name;

    public Skill(String name) {
        this.name = name;
    }

    protected Skill(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
