package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    TextView registration_link;
    Button login_btn;
    EditText  email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText)findViewById(R.id.sign_in_email_input);
        password = (EditText)findViewById(R.id.password_input);

////////////////////view registration activity//////////////////////////

        registration_link = (TextView)findViewById(R.id.register_link);
        registration_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
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
                    if (emailText.equals("admin@admin.com")) {
                        Intent mainActivityIntent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                        startActivity(mainActivityIntent);
                    }else {
                        Candidate candidate = RegisteredCandidates.getInstance().retrieveCandidateByEMail(emailText);
                        if (candidate!=null){

                            LoggedInUser.getInstance().LoginUser(candidate);
                            Intent userHomeIntent = new Intent(LoginActivity.this, UserHomeActivity.class);
                            startActivity(userHomeIntent);

                        }else {

                            Toast.makeText(LoginActivity.this, "Sign in Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }else {
                    return;
                }
            }
        });


///////////////////////////////////////

    }

    private boolean verifyInput() {

        boolean valid = true;

        if (email.getText().length() <= 0){
            valid = false;
            email.setError("Email required!");
        }

        if (password.getText().length() <= 0){
            valid = false;
            password.setError("password required!");
        }

        return valid;
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

    }


}
