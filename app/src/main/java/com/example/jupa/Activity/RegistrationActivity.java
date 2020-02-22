package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Group.Group;
import com.example.jupa.Helpers.PopulateRegistrationSpinners;
import com.example.jupa.R;
import com.example.jupa.Helpers.showProgressbar;

public class RegistrationActivity extends AppCompatActivity {


    Spinner groupSpinner,  category, genderSpinner,education;
    EditText firstName, lastName, familyName, mobile_no, other_no, email, dob, address;
    String firstNameText, lastNameText, familyNameText, mobile_noText, other_noText, emailText, dobText,
            addressText, educationText, genderText, groupText, categoryText;

    Button save_btn;
    showProgressbar showprogress;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    String member_level;
    Intent intent;
    PopulateRegistrationSpinners populateRegistrationSpinners;

    public static final String LEVEL_EXTRA="level_extra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        intent = getIntent();
        member_level = intent.getStringExtra(LEVEL_EXTRA);

//        candidateCategoryBackgroundApiTasks
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);
        showprogress = new showProgressbar(this);
        showprogress.setMessage("Loading");
        showprogress.show();

        groupSpinner = (Spinner)findViewById(R.id.group_input);
        category = (Spinner)findViewById(R.id.category_input);
        genderSpinner = (Spinner)findViewById(R.id.gender_input);

        populateRegistrationSpinners = new PopulateRegistrationSpinners(this,groupSpinner,category,genderSpinner);
        populateRegistrationSpinners.setShowProgress(showprogress);


        firstName = (EditText)findViewById(R.id.first_name_input);
        lastName = (EditText)findViewById(R.id.last_name_input);
        familyName = (EditText)findViewById(R.id.family_name_input);
        mobile_no = (EditText)findViewById(R.id.mobile_no_input);
        other_no = (EditText)findViewById(R.id.other_no_input);
        address = (EditText)findViewById(R.id.address_input);
        education = (Spinner)findViewById(R.id.education_input);

        email = (EditText)findViewById(R.id.email_input);
        dob = (EditText)findViewById(R.id.dob_input);

        save_btn = (Button)findViewById(R.id.register_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (save_btn.getText().toString().equals(getResources().getString(R.string.reload))){
                    startActivity(getIntent());
                }else {
                    registerCandidate();
                }
            }
        });

        populateRegistrationSpinners.setButton(save_btn);
        populateRegistrationSpinners.populateGroupSpinner();
    }


    private void registerCandidate() {

        firstNameText = firstName.getText().toString();
        lastNameText = lastName.getText().toString();
        familyNameText = familyName.getText().toString();

        mobile_noText = mobile_no.getText().toString();
        other_noText = other_no.getText().toString();

        addressText = address.getText().toString();
        genderText = genderSpinner.getSelectedItem().toString();
        educationText = education.getSelectedItem().toString();

        emailText = email.getText().toString();
        dobText = dob.getText().toString();

        groupText = groupSpinner.getSelectedItem()!=null?groupSpinner.getSelectedItem().toString():null;

        categoryText = category.getSelectedItem()!=null?category.getSelectedItem().toString():null;
        Integer group_id;
        Group group = populateRegistrationSpinners.groupsList.findGroupByName(groupText);
        group_id = (group!=null)?group.getId():null;

        Integer category_id;
        CandidateCategory candidateCategory = populateRegistrationSpinners.groupsList.findCandidateCategoryByName(categoryText);
        category_id = candidateCategory!=null?candidateCategory.getId():null;

        Candidate newCandidate = new Candidate(null,null, firstNameText, lastNameText, familyNameText,
                genderText,emailText,dobText,mobile_noText,other_noText,null,addressText,educationText,group_id,category_id,
                null,null,null,null,null,null,null,null);

        showprogress.setMessage("A moment as we set you up!");
        showprogress.show();
        new sendRegistrationData().execute(newCandidate);

    }


    public void showSelectAssociation(Candidate candidate){

        //skip selection for assessor

        if (member_level!=null&&member_level.equals(UserHomeActivity.ASSESSOR_ROLE)){

            Intent assessor_intent = new Intent(this, AssessorRequestActivity.class);
            assessor_intent.putExtra(RankRequestActivity.INITIAL_RANK,true);
            assessor_intent.putExtra(RankRequestActivity.CANDIDATE_EXTRA,candidate);
            startActivity(assessor_intent);

        }else{

            Intent selectAssociation = new Intent(this, SelectAssociationActivity.class);
            selectAssociation.putExtra(LEVEL_EXTRA,member_level);
            selectAssociation.putExtra(RankRequestActivity.CANDIDATE_EXTRA,candidate);
            startActivity(selectAssociation);

        }


    }


    private void buildNotificationDialog(String message,final Candidate candidate) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_help_outline_black_24dp);
        builder.setTitle("Registration");
        builder.setCancelable(false);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                showSelectAssociation(candidate);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public class sendRegistrationData extends AsyncTask<Candidate,Void,Candidate>{

        @Override
        protected void onPostExecute(Candidate candidate) {

            showprogress.dismiss();

            Toast.makeText(RegistrationActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_LONG).show();

            if (candidate!=null){

                buildNotificationDialog(candidateBackgroundApiTasks.getMessage(),candidate);

            }

        }

        @Override
        protected Candidate doInBackground(Candidate... candidates) {

            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){

                try {

                    candidateBackgroundApiTasks.registerNewCandidate(candidates[0]);
                    candidateBackgroundApiTasks.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedCandidate = candidateBackgroundApiTasks.getCandidate();

            }

            return returnedCandidate;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onBackPressed();
    }
}
