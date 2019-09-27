package com.example.jupa.Institution;

import android.os.Parcel;
import android.os.Parcelable;

public class Institution implements Parcelable {

    Integer institution_id;
    String center_No;
    String name;
    String photo_url;
    String contactPerson;
    String telephone_number;
    String email_address;
    String about_institution;
    String website;
    String facebook;


    public Institution(String center_No, String name, String photo_url, String contactPerson, String telephone_number, String email_address, String about_institution, String website, String facebook) {
        this.center_No = center_No;
        this.name = name;
        this.photo_url = photo_url;
        this.contactPerson = contactPerson;
        this.telephone_number = telephone_number;
        this.email_address = email_address;
        this.about_institution = about_institution;
        this.website = website;
        this.facebook = facebook;
    }


    protected Institution(Parcel in) {
        if (in.readByte() == 0) {
            institution_id = null;
        } else {
            institution_id = in.readInt();
        }
        center_No = in.readString();
        name = in.readString();
        photo_url = in.readString();
        contactPerson = in.readString();
        telephone_number = in.readString();
        email_address = in.readString();
        about_institution = in.readString();
        website = in.readString();
        facebook = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (institution_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(institution_id);
        }
        dest.writeString(center_No);
        dest.writeString(name);
        dest.writeString(photo_url);
        dest.writeString(contactPerson);
        dest.writeString(telephone_number);
        dest.writeString(email_address);
        dest.writeString(about_institution);
        dest.writeString(website);
        dest.writeString(facebook);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Institution> CREATOR = new Creator<Institution>() {
        @Override
        public Institution createFromParcel(Parcel in) {
            return new Institution(in);
        }

        @Override
        public Institution[] newArray(int size) {
            return new Institution[size];
        }
    };

    public Integer getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(Integer institution_id) {
        this.institution_id = institution_id;
    }

    public String getCenter_No() {
        return center_No;
    }

    public void setCenter_No(String center_No) {
        this.center_No = center_No;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(String telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getAbout_institution() {
        return about_institution;
    }

    public void setAbout_institution(String about_institution) {
        this.about_institution = about_institution;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
