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

import com.example.jupa.Activity.InstitutionActivity;
import com.example.jupa.Activity.NewInstitutionActivity;
import com.example.jupa.Helpers.LoggedInInstitution;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstitutionLoginFragment extends Fragment {

    TextView registration_link;
    Button login_btn;
    EditText email,password;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;
    showProgressbar showProgress;
    private View view;


    public InstitutionLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_institution_login, container, false);

        showProgress = new showProgressbar(getContext());

        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(getContext());

        email = (EditText)view.findViewById(R.id.sign_in_email_input);
        password = (EditText)view.findViewById(R.id.password_input);

        ////////////////////view registration activity//////////////////////////

        registration_link = (TextView)view.findViewById(R.id.register_link);

        registration_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewInstitutionActivity.class);
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

                        showProgress.setMessage("Authenticating...");
                        showProgress.show();
                        new loginInstitution().execute(emailText,passwordText);

                }else {

                    return;

                }
            }
        });


///////////////////////////////////////

        return view;
    }


    public class loginInstitution extends AsyncTask<String,Void, Institution> {

        @Override
        protected void onPostExecute(Institution institution) {

            showProgress.dismiss();

            if (institution!=null){

                LoggedInInstitution.getInstance().LogginInstitution(institution);
                Intent userHomeIntent = new Intent(getContext(), InstitutionActivity.class);
                startActivity(userHomeIntent);

            }else {

                Toast.makeText(getContext(), institutionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Institution doInBackground(String... strings) {
            Institution returnedInstitution;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.LoginInstitution(strings[0],strings[1]);
                try {
                    institutionApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedInstitution = institutionApiBackgroundTasks.getInstitution();

            }

            return returnedInstitution;

        }

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

}
