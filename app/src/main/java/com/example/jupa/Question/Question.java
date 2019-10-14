package com.example.jupa.Question;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Question implements Parcelable {

    @SerializedName("question_id")
    int question_id;

    @SerializedName("user_id")
    int user_id;

    @SerializedName("assessment_cat_id")
    int category_id;

    @SerializedName("question_description")
    String title;

    @SerializedName("unique_no")
    String question_code;

    public Question(int user_id, int category_id, String title, String question_code) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.question_code = question_code;
    }

    protected Question(Parcel in) {
        question_id = in.readInt();
        user_id = in.readInt();
        category_id = in.readInt();
        title = in.readString();
        question_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question_id);
        dest.writeInt(user_id);
        dest.writeInt(category_id);
        dest.writeString(title);
        dest.writeString(question_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_code() {
        return question_code;
    }

    public void setQuestion_code(String question_code) {
        this.question_code = question_code;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
