package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

public class NewInstitutionActivity extends AppCompatActivity {

    showProgressbar showProgress;
    EditText name_input, center_no_input,contact_person_input,telephone_number_input, physical_address,email_input, about_input, website_input, facebook_input;
    Button save_institution;
    String nameText, centerNoText, contactPersonText, telText, physical_addressText ,emailText, aboutText, websiteText, facebookText;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_institution);

        showProgress = new showProgressbar(this);
        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);

        name_input = (EditText) findViewById(R.id.institutions_name_input);
        center_no_input = (EditText) findViewById(R.id.institutions_center_no_input);
        contact_person_input = (EditText) findViewById(R.id.institutions_contact_person_input);
        telephone_number_input = (EditText)findViewById(R.id.institutions_tel_number_input);
        physical_address = (EditText)findViewById(R.id.institutions_physical_address_input);
        email_input = (EditText)findViewById(R.id.institutions_email_input);
        about_input = (EditText)findViewById(R.id.institutions_about_input);
        website_input = (EditText)findViewById(R.id.institutions_website_input);
        facebook_input = (EditText)findViewById(R.id.institutions_facebook_input);
        save_institution = (Button)findViewById(R.id.save_institution);

        save_institution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInstitution();
            }
        });

    }

    private void saveInstitution() {

        nameText = name_input.getText().toString();
        centerNoText = center_no_input.getText().toString();
        contactPersonText = contact_person_input.getText().toString();
        telText = telephone_number_input.getText().toString();
        physical_addressText = physical_address.getText().toString();
        emailText = email_input.getText().toString();
        aboutText = about_input.getText().toString();
        websiteText = website_input.getText().toString();
        facebookText = facebook_input.getText().toString();

        if (inputValid()){

            Institution newInstitution = new Institution(centerNoText,nameText,null,contactPersonText,telText,emailText,physical_addressText,
                    aboutText,websiteText,facebookText);
            showProgress.setMessage("Saving...");
            showProgress.show();
            new saveInstitutions().execute(newInstitution);


        }else {

            return;

        }
    }

    private boolean inputValid() {

        boolean valid = true;
        if (nameText.isEmpty()){
            valid = false;
            name_input.setError("Name is required!");
        }

        if (centerNoText.isEmpty()){
            valid = false;
            center_no_input.setError("Center number is required!");
        }

        if (contactPersonText.isEmpty()){

            valid = false;
            contact_person_input.setError("This is required");

        }

        if (telText.isEmpty()){

            valid = false;
            telephone_number_input.setError("This is required");

        }

        if (physical_addressText.isEmpty()){

            valid = false;
            physical_address.setError("This is required");
        }

        if (aboutText.isEmpty()){

            valid = false;
            about_input.setError("This is required");

        }

        if (emailText.isEmpty()){

            valid = false;
            email_input.setError("Email is required");

        }


        return valid;
    }


    public class saveInstitutions extends AsyncTask<Institution,Void,Institution>{


        @Override
        protected void onPostExecute(Institution institution) {


            if (institution!=null){

                if (InstitutionsActivity.institutionsAdapter!=null){
                    InstitutionsActivity.institutionsAdapter.addItem(institution);
                    showProgress.dismiss();
                    onBackPressed();
                }else {
                    showProgress.dismiss();
                    buildNotificationDialog(institutionApiBackgroundTasks.getMessage());
                }

            }else {

                Toast.makeText(NewInstitutionActivity.this, institutionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress.dismiss();

            }

        }

        @Override
        protected Institution doInBackground(Institution... institutions) {


            Institution returnedInstitution;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.add_institutionTask(institutions[0]);
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

    private void buildNotificationDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_help_outline_black_24dp);
        builder.setTitle("Registration Complete");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                onBackPressed();

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
