package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AssessementActivity extends AppCompatActivity {

    CandidateProject candidateProject;
    Intent intent;
    final static String PROJECT_EXTRA = "project";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessement);
        intent = getIntent();

    }
}
