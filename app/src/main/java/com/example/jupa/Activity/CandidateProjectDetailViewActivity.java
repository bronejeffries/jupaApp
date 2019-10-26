package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Helpers.LoggedInInstitution;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.R;


public class CandidateProjectDetailViewActivity extends AppCompatActivity {

    public static final String CANDIDATE_PROJECT="project";
    CandidateProject candidateProject;
    Intent intent;
    TextView location, date_of_completion, client_names, client_contact, client_mail, description, client_address, title;
    Button assess_btn;
    RelativeLayout photo_View;
    boolean permission = false;
    Candidate project_owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_project_detail_view);
        intent = getIntent();
        candidateProject = intent.getParcelableExtra(CANDIDATE_PROJECT);
        project_owner = ProfileActivity.candidate;
        location = (TextView)findViewById(R.id.project_location_input);
        date_of_completion = (TextView)findViewById(R.id.project_date_completion_input);
        client_names = (TextView)findViewById(R.id.project_client_name_input);
        client_contact = (TextView)findViewById(R.id.project_client_contact_input);
        client_mail = (TextView)findViewById(R.id.project_client_email_input);
        description = (TextView)findViewById(R.id.project_description_input);
        client_address = (TextView)findViewById(R.id.project_client_address_input);
        assess_btn = (Button)findViewById(R.id.assess_project_btn);
        title = (TextView)findViewById(R.id.project_title_input);
        photo_View = (RelativeLayout)findViewById(R.id.header_layout);

        checkPermission();
        populateViews();

    }

    public void checkPermission(){


        if (UserHomeActivity.thisCandidate != null){

                permission = UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ASSESSOR_ROLE)
                        && !UserHomeActivity.thisCandidate.getId().equals(candidateProject.getCandidate_id())
                        && UserHomeActivity.thisCandidate.getGroup().equals(project_owner.getGroup());

        }

    }

    private void populateViews() {

        title.setText(candidateProject.getTitle());
        location.setText(candidateProject.getLocation());
        date_of_completion.setText(candidateProject.getDate_of_completion());
        client_names.setText(candidateProject.getClient_name());
        client_contact.setText(candidateProject.getClient_contact());
        client_mail.setText(candidateProject.getClient_email());
        description.setText(candidateProject.getDescription());
        client_address.setText(candidateProject.getClient_address());

        if (permission){

            assess_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAssessments(candidateProject);
                }
            });

        }else {

            assess_btn.setVisibility(View.GONE);

        }

    }

    private void showAssessments(CandidateProject candidateProject) {

        Intent showAssessmentIntent = new Intent(this, ProjectAssessmentsActivity.class);
        showAssessmentIntent.putExtra(ProjectAssessmentsActivity.CANDIDATE_PROJECT_EXTRA,candidateProject);
        startActivity(showAssessmentIntent);

    }

}
