package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jupa.Activity.InstitutionActivity;
import com.example.jupa.Institution.Institution;

public class InstitutionProfileActivity extends AppCompatActivity {


    TextView name_input, center_no_input,contact_person_input,telephone_number_input, physical_address,email_input, about_input, website_input, facebook_input;
    String nameText, centerNoText, contactPersonText, telText, physical_addressText ,emailText, aboutText, websiteText, facebookText;
    Intent intent;
    Institution institution;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_profile);

        intent = getIntent();

        institution = intent.getParcelableExtra(InstitutionActivity.INSTITUTION_EXTRA);

        name_input = (TextView) findViewById(R.id.institutions_name_input);
        center_no_input = (TextView) findViewById(R.id.institutions_center_no_input);
        contact_person_input = (TextView) findViewById(R.id.institutions_contact_person_input);
        telephone_number_input = (TextView)findViewById(R.id.institutions_tel_number_input);
        physical_address = (TextView)findViewById(R.id.institutions_physical_address_input);
        email_input = (TextView)findViewById(R.id.institutions_email_input);
        about_input = (TextView)findViewById(R.id.institutions_about_input);
        website_input = (TextView)findViewById(R.id.institutions_website_input);
        facebook_input = (TextView)findViewById(R.id.institutions_facebook_input);

        populateViews();

    }

    private void populateViews() {

        nameText = institution.getName();
        centerNoText = institution.getCenter_No();
        contactPersonText = institution.getContactPerson();
        telText = institution.getTelephone_number();
        physical_addressText = institution.getPhysical_address();
        emailText = institution.getEmail_address();
        aboutText = institution.getAbout_institution();
        websiteText = institution.getWebsite();
        facebookText = institution.getFacebook();

        name_input.setText(nameText);
        center_no_input.setText(centerNoText);
        contact_person_input.setText(contactPersonText);
        telephone_number_input.setText(telText);
        physical_address.setText(physical_addressText);
        email_input.setText(emailText);
        about_input.setText(aboutText);
        website_input.setText(websiteText);
        facebook_input.setText(facebookText);

    }
}
