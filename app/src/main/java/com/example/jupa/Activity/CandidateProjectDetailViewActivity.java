package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button assess_btn,verifyProjectBtn;
    RelativeLayout photo_View;
    boolean permission = false;
    Candidate project_owner;
    AlertDialog verifyDialog;

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
        verifyProjectBtn = (Button)findViewById(R.id.verify_project_btn);
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
            verifyProjectBtn.setVisibility(View.GONE);
        }

    }

    private void showAssessments(CandidateProject candidateProject) {

        Intent showAssessmentIntent = new Intent(this, ProjectAssessmentsActivity.class);
        showAssessmentIntent.putExtra(ProjectAssessmentsActivity.CANDIDATE_PROJECT_EXTRA,candidateProject);
        startActivity(showAssessmentIntent);

    }

    public void verifyProject(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final String[] contacts = {candidateProject.getClient_contact()};
        final String[] contactsDisplay = {"Use client contact\n"+candidateProject.getClient_contact(),"Use a custom number","Cancel"};

        alertDialogBuilder.setItems(contactsDisplay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){

                    case 0:

                        launchMessageSetUp(contacts[0].toString());
                        break;

                    case 1:
                        launchCustomSetUp();
                        break;

                    case 2:
                        dialogInterface.dismiss();
                        break;
                }
            }
        });

        verifyDialog = alertDialogBuilder.create();
        verifyDialog.show();

    }

    private void launchCustomSetUp() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.custom_project_verification,null,false);
        final EditText editText = (EditText)cardView.findViewById(R.id.custom_number);
        Button confirm_verify = (Button)cardView.findViewById(R.id.custom_verify_btn);

        confirm_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String customNumber = editText.getText().toString();
                launchMessageSetUp(customNumber);
            }
        });

        alertBuilder.setView(cardView);

        alertBuilder.create().show();

    }

    private void launchMessageSetUp(String Number) {

        Log.e("launch", "launchMessageSetUp: "+Number );

//        TODO: generate Code and initiate message action intent

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:"+Number));
        smsIntent.putExtra("sms_body"  , "Test ");

        try {

            startActivity(smsIntent);

        }catch (android.content.ActivityNotFoundException e){



        }

    }

}
