package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.R;

public class AssessementActivity extends AppCompatActivity {


    CandidateProject candidateProject;
    Intent intent;
    final static String ASSESSMENT_GROUP_EXTRA = "assessment_group";
    AssessmentGroup assessmentGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessement);
        intent = getIntent();
        assessmentGroup = intent.getParcelableExtra(ASSESSMENT_GROUP_EXTRA);
        candidateProject = ProjectAssessmentsActivity.candidateProject;





    }
}
