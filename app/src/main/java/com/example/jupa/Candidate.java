package com.example.jupa;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Candidate implements Parcelable {


    String name, contact, email, date_of_birth, category, country, state, city, town, role,status,available, institution;
    ArrayList<Skill> candidateSkills;
    Group Candidate_group;
    Candidate CandidateAssessor;

    public Candidate(String name, String contact, String email, String date_of_birth, Group group, String category, String country, String state, String city, String town) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.Candidate_group = group;
        this.category = category;
        this.country = country;
        this.state = state;
        this.city = city;
        this.town = town;
        this.role = "Candidate";
        this.status = "available";
        this.available = "now";
        this.candidateSkills = new ArrayList<>();
    }


    protected Candidate(Parcel in) {
        name = in.readString();
        contact = in.readString();
        email = in.readString();
        date_of_birth = in.readString();
        category = in.readString();
        country = in.readString();
        state = in.readString();
        city = in.readString();
        town = in.readString();
        role = in.readString();
        status = in.readString();
        available = in.readString();
        institution = in.readString();
        candidateSkills = in.createTypedArrayList(Skill.CREATOR);
        Candidate_group = in.readParcelable(Group.class.getClassLoader());
        CandidateAssessor = in.readParcelable(Candidate.class.getClassLoader());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public Group getGroup() {
        return Candidate_group;
    }

    public void setGroup(Group group) {
        Candidate_group = group;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<Skill> getSkills() {
        return candidateSkills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.candidateSkills = skills;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Candidate getCandidateAssessor() {
        return CandidateAssessor;
    }

    public void setCandidateAssessor(Candidate candidateAssessor) {
        CandidateAssessor = candidateAssessor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(contact);
        parcel.writeString(email);
        parcel.writeString(date_of_birth);
        parcel.writeString(category);
        parcel.writeString(country);
        parcel.writeString(state);
        parcel.writeString(city);
        parcel.writeString(town);
        parcel.writeString(role);
        parcel.writeString(status);
        parcel.writeString(available);
        parcel.writeString(institution);
        parcel.writeTypedList(candidateSkills);
        parcel.writeParcelable(Candidate_group, i);
        parcel.writeParcelable(CandidateAssessor, i);
    }
}

