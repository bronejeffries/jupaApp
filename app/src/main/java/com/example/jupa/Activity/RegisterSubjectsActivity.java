package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jupa.R;

public class RegisterSubjectsActivity extends AppCompatActivity {

    public static String CANDIDATE_EXTRA = "candidate", INSTITUTION_EXTRA = "institution_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_subjects);

    }
}
