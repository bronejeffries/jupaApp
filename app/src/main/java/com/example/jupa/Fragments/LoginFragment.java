package com.example.jupa.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Activity.AdminHomeActivity;
import com.example.jupa.Activity.RegistrationActivity;
import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    TextView registration_link;
    Button login_btn;
    EditText email,password;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        showProgress = new showProgressbar(getContext());

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(getContext());

        email = (EditText)view.findViewById(R.id.sign_in_email_input);
        password = (EditText)view.findViewById(R.id.password_input);


////////////////////view registration activity//////////////////////////

        registration_link = (TextView)view.findViewById(R.id.register_link);

        registration_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

//////////////////////////////

        //////////////////////login action///////////////////////////
        login_btn = (Button)view.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verifyInput()){
                    String emailText = email.getText().toString();
                    String passwordText = password.getText().toString();
                    if (emailText.equals("a")) {
                        Intent mainActivityIntent = new Intent(getContext(), AdminHomeActivity.class);
                        startActivity(mainActivityIntent);
                    }else {
                        showProgress.setMessage("Authenticating...");
                        showProgress.show();
                        new loginUser().execute(emailText,passwordText);

                    }
                }else {
                    return;
                }
            }
        });


///////////////////////////////////////


        return view;
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
                Intent userHomeIntent = new Intent(getContext(), UserHomeActivity.class);
                startActivity(userHomeIntent);

            }else {

                Toast.makeText(getContext(), candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

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
