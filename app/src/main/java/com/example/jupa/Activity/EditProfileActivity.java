package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;

public class EditProfileActivity extends AppCompatActivity {

    public static final String CANDIDATE_EXTRA="candidate";
    Candidate candidate;
    Intent intent;

    EditText mobile_no, other_no, email, address, education;
    String mobile_noText, other_noText, emailText,addressText, educationText;
    Button save_btn;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        intent = getIntent();
        candidate = intent.getParcelableExtra(CANDIDATE_EXTRA);

        showProgress = new showProgressbar(this);

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        email = (EditText)findViewById(R.id.email_input);
        email.setText(candidate.getEmail());

        mobile_no = (EditText)findViewById(R.id.mobile_no_input);
        mobile_no.setText(candidate.getMobile_number());

        other_no = (EditText)findViewById(R.id.other_no_input);
        other_no.setText(candidate.getOther_number());

        address = (EditText)findViewById(R.id.address_input);
        address.setText(candidate.getAddress());

        education = (EditText)findViewById(R.id.education_input);
        education.setText(candidate.getEducation());

        save_btn = (Button)findViewById(R.id.register_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailText = email.getText().toString();
                mobile_noText = mobile_no.getText().toString();
                other_noText = other_no.getText().toString();
                addressText = address.getText().toString();
                educationText = education.getText().toString();
                candidate.setEmail(emailText);
                candidate.setMobile_number(mobile_noText);
                candidate.setOther_number(other_noText);
                candidate.setAddress(addressText);
                candidate.setEducation(educationText);
                showProgress.setMessage("updating profile..");
                showProgress.show();
                new updateCandidate().execute(candidate);

            }
        });

    }

    public class updateCandidate extends AsyncTask<Candidate,Void,Candidate> {

        @Override
        protected void onPostExecute(Candidate returnedCandidate) {

            showProgress.dismiss();
            Toast.makeText(EditProfileActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            if (returnedCandidate!=null){

                candidate = returnedCandidate;
//                ProfileActivity.profile_edited = true;
//                finish();
            }

        }

        @Override
        protected Candidate doInBackground(Candidate... candidates) {

            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){


                candidateBackgroundApiTasks.updateCandidate(candidates[0]);

                try {


                    candidateBackgroundApiTasks.wait();


                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                returnedCandidate = candidateBackgroundApiTasks.getCandidate();

            }

            return returnedCandidate;

        }

    }

}
