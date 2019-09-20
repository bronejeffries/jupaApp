package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProjectAssessmentsActivity extends AppCompatActivity {

    public static final String CANDIDATE_PROJECT_EXTRA ="candidate_project" ;

    CandidateProject candidateProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_assessments);

    }

}
