package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jupa.Helpers.LoggedInInstitution;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

public class InstitutionActivity extends AppCompatActivity {

    TextView Welcome;
    Institution institution;
    LinearLayout candidates_more, assessor_more;
    Boolean candidates_more_open=false,assessors_more_open=false,InstitutionLogin=false;
    public static final String INSTITUTION_EXTRA = "institution_extra";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution);

        intent = getIntent();
        institution = LoggedInInstitution.getInstance().getLoggedInInstitution();

//        if an institute has logged in
        if (institution!=null){

            setInstitutionLogin(true);

        }else {

//        if an institute has not logged in
           institution = intent.getParcelableExtra(INSTITUTION_EXTRA);
           setInstitutionLogin(false);

        }

        Welcome = (TextView)findViewById(R.id.welcome_text);
        String welcome_text = institution.getName()+" ("+ institution.getCenter_No() +")";
        Welcome.setText(welcome_text);

        candidates_more = (LinearLayout)findViewById(R.id.view_candidates_more);
        assessor_more = (LinearLayout)findViewById(R.id.view_assessors_more);

    }

    public void viewCandidatesClick(View view){

        Intent groupsIntent = new Intent(this,GroupsActivity.class);
        groupsIntent.putExtra(GroupsActivity.INTENT_PURPOSE,UserHomeActivity.CANDIDATE_ROLE);
        groupsIntent.putExtra(GroupsActivity.INSTITUTION_EXTRA,institution);
        startActivity(groupsIntent);

    }

    public void viewAllCandidatesClick(View view){

        Intent candidatesIntent = new Intent(this,GroupActivity.class);
        candidatesIntent.putExtra(GroupActivity.MEMBER_LEVEL,UserHomeActivity.CANDIDATE_ROLE);
        candidatesIntent.putExtra(GroupActivity.INSTITUTION,institution);
        candidatesIntent.putExtra(GroupActivity.INSTITUTION_CANDIDATE_ASSESSOR_VIEW,true);
        startActivity(candidatesIntent);

    }


    public void viewAssessorsClicked(View view){

        Intent groupsIntent = new Intent(this,GroupsActivity.class);
        groupsIntent.putExtra(GroupsActivity.INTENT_PURPOSE,UserHomeActivity.ASSESSOR_ROLE);
        groupsIntent.putExtra(GroupsActivity.INSTITUTION_EXTRA,institution);
        startActivity(groupsIntent);
    }

    public void viewAllAssessorsClick(View view){

        Intent assessorsIntent = new Intent(this,GroupActivity.class);
        assessorsIntent.putExtra(GroupActivity.MEMBER_LEVEL,UserHomeActivity.ASSESSOR_ROLE);
        assessorsIntent.putExtra(GroupActivity.INSTITUTION,institution);
        assessorsIntent.putExtra(GroupActivity.INSTITUTION_CANDIDATE_ASSESSOR_VIEW,true);
        startActivity(assessorsIntent);

    }

    public void viewResultsClicked(View view){

        Intent results_intent = new Intent(this, InstitutionResultsActivity.class);
        results_intent.putExtra(InstitutionResultsActivity.Institution_Extra,institution);
        startActivity(results_intent);

    }

    public void viewInstitutionProfile(View view){

        Intent institution_intent = new Intent(this, InstitutionProfileActivity.class);
        institution_intent.putExtra(InstitutionActivity.INSTITUTION_EXTRA,institution);
        startActivity(institution_intent);

    }

    public void viewCandidatesMore(View view){

        if (!getCandidates_more_open()){

            candidates_more.setVisibility(View.VISIBLE);
            setCandidates_more_open(true);
        }else {

            candidates_more.setVisibility(View.GONE);
            setCandidates_more_open(false);
        }

    }

    public void viewAssessorsMore(View view){

        if (!getAssessors_more_open()){

            assessor_more.setVisibility(View.VISIBLE);
            setAssessors_more_open(true);
        }else {

            assessor_more.setVisibility(View.GONE);
            setAssessors_more_open(false);
        }

    }

    public Boolean getCandidates_more_open() {
        return candidates_more_open;
    }

    public void setCandidates_more_open(Boolean candidates_more_open) {
        this.candidates_more_open = candidates_more_open;
    }

    public Boolean getAssessors_more_open() {
        return assessors_more_open;
    }

    public void setAssessors_more_open(Boolean assessors_more_open) {
        this.assessors_more_open = assessors_more_open;
    }

    public Boolean getInstitutionLogin() {
        return InstitutionLogin;
    }

    public void setInstitutionLogin(Boolean institutionLogin) {
        InstitutionLogin = institutionLogin;
    }

    @Override
    public void onBackPressed() {

        if (getInstitutionLogin()){

            moveTaskToBack(true);

        }else {

            super.onBackPressed();

        }

    }

}
