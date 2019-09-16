package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity {


    Spinner groupSpinner,  category;
    EditText name, contact_no, email, dob, country, state, city, town;
    String nameText, contactText, emailText, dobText, countryText, stateText, cityText, townText, groupText, categoryText;
    Button save_btn;
    private RegisteredCandidates registeredCandidates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        groupSpinner = (Spinner)findViewById(R.id.group_input);
        populateGroupSpinner();
        category = (Spinner)findViewById(R.id.category_input);
        name = (EditText)findViewById(R.id.name_input);
        contact_no = (EditText)findViewById(R.id.contact_input);
        email = (EditText)findViewById(R.id.email_input);
        dob = (EditText)findViewById(R.id.dob_input);
        country = (EditText)findViewById(R.id.country_input);
        state = (EditText)findViewById(R.id.state_input);
        city = (EditText)findViewById(R.id.city_input);
        town = (EditText)findViewById(R.id.town_input);

        save_btn = (Button)findViewById(R.id.register_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCandidate();
            }
        });
    }



    private void registerCandidate() {

        nameText = name.getText().toString();
        contactText = contact_no.getText().toString();
        emailText = email.getText().toString();
        dobText = dob.getText().toString();
        countryText = country.getText().toString();
        stateText = state.getText().toString();
        cityText = city.getText().toString();
        townText = town.getText().toString();
        groupText = groupSpinner.getSelectedItem().toString();
        categoryText = category.getSelectedItem().toString().toLowerCase();
        Group group = GroupsList.findGroupByName(groupText);

        Candidate newCandidate = new Candidate(nameText,contactText,emailText,dobText,group,categoryText,countryText,stateText,cityText,townText);
        registeredCandidates = RegisteredCandidates.getInstance();
        registeredCandidates.addToList(newCandidate);


    }

    private void populateGroupSpinner() {

        List<String> spinnerArray = GroupsList.makeList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);

    }




}
