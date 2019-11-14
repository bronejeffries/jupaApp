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

import com.example.jupa.Fragments.InstitutionLoginFragment;
import com.example.jupa.Helpers.LoggedInInstitution;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

public class InstitutionLoginActivity extends AppCompatActivity {


    TextView registration_link;
    Button login_btn;
    EditText email,password;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;
    showProgressbar showProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_login);

        showProgress = new showProgressbar(InstitutionLoginActivity.this);

        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(InstitutionLoginActivity.this);

        email = (EditText)findViewById(R.id.sign_in_email_input);
        password = (EditText)findViewById(R.id.password_input);

        ////////////////////view registration activity//////////////////////////

        registration_link = (TextView)findViewById(R.id.register_link);

        registration_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstitutionLoginActivity.this, NewInstitutionActivity.class);
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
                    new loginInstitution().execute(emailText,passwordText);

                }else {

                    return;

                }
            }
        });


///////////////////////////////////////

    }

    public class loginInstitution extends AsyncTask<String,Void, Institution> {

        @Override
        protected void onPostExecute(Institution institution) {

            showProgress.dismiss();

            if (institution!=null){

                LoggedInInstitution.getInstance().LogginInstitution(institution);
                Intent userHomeIntent = new Intent(InstitutionLoginActivity.this, InstitutionActivity.class);
                startActivity(userHomeIntent);

            }else {

                Toast.makeText(InstitutionLoginActivity.this, institutionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();

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
