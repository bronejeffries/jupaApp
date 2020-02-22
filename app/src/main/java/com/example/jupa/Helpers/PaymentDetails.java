package com.example.jupa.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class PaymentDetails implements Parcelable {

    private String email = "example@email.com";
    private String fName = "FirstName";
    private String lName = "LastName";
    private String narration = "Payment for registration";
    private String txRef;
    private String country = "UG";
    private String currency = "UGX";
    private int amount;

    public PaymentDetails() {


    }

    public PaymentDetails(String email, String fName, String lName, String narration, String txRef, String country, String currency, int amount) {

        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.narration = narration;
        this.txRef = txRef+(UUID.randomUUID().toString());
        this.country = country;
        this.currency = currency;
        this.amount = amount;

    }


    protected PaymentDetails(Parcel in) {
        email = in.readString();
        fName = in.readString();
        lName = in.readString();
        narration = in.readString();
        txRef = in.readString();
        country = in.readString();
        currency = in.readString();
        amount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeString(narration);
        dest.writeString(txRef);
        dest.writeString(country);
        dest.writeString(currency);
        dest.writeInt(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaymentDetails> CREATOR = new Creator<PaymentDetails>() {
        @Override
        public PaymentDetails createFromParcel(Parcel in) {
            return new PaymentDetails(in);
        }

        @Override
        public PaymentDetails[] newArray(int size) {
            return new PaymentDetails[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTxRef() {
        return txRef;
    }

    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
