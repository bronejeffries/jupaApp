package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;

public class CandidateLoginActivity extends AppCompatActivity {

    TextView registration_link;
    Button login_btn;
    EditText email,password;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_login);

        showProgress = new showProgressbar(this);

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        email = (EditText)findViewById(R.id.sign_in_email_input);
        password = (EditText)findViewById(R.id.password_input);


////////////////////view registration activity//////////////////////////

        registration_link = (TextView)findViewById(R.id.register_link);

        registration_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CandidateLoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

//////////////////////////////

        //////////////////////login action///////////////////////////
        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verifyInput()){
                    String emailText = email.getText().toString();
                    String passwordText = password.getText().toString();
                        showProgress.setMessage("Authenticating...");
                        showProgress.show();
                        new loginUser().execute(emailText,passwordText);
                }else {
                    return;
                }
            }
        });


///////////////////////////////////////



    }


    private boolean verifyInput() {

        boolean valid = true;

        if (email.getText().toString().isEmpty()){
            valid = false;
            email.setError("Email required!");
        }

        if (password.getText().toString().isEmpty()){
            valid = false;
            password.setError("password required!");
        }

        return valid;
    }

    public class loginUser extends AsyncTask<String,Void, Candidate> {

        @Override
        protected void onPostExecute(Candidate candidate) {

            showProgress.dismiss();

            if (candidate!=null){

                LoggedInUser.getInstance().LoginUser(candidate);
                Intent userHomeIntent = new Intent(CandidateLoginActivity.this, UserHomeActivity.class);
                startActivity(userHomeIntent);

            }else {

                Toast.makeText(CandidateLoginActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Candidate doInBackground(String... strings) {
            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.loginInTask(strings[0],strings[1]);
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
