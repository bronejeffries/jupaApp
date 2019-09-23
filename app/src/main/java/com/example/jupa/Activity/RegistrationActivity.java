package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Candidate.Category.Api.CandidateCategoryBackgroundApiTasks;
import com.example.jupa.Group.Adapter.GroupsAdapter;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Group.GroupsList;
import com.example.jupa.R;
import com.example.jupa.Helpers.showProgressbar;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {


    Spinner groupSpinner,  category, genderSpinner;
    EditText firstname,lastname,familyname, mobile_no, other_no, email, dob, address, education, regNo;
    String firstnameText, lastnameText, familynameText, mobile_noText, other_noText, emailText, dobText,
            addressText, educationText, genderText, groupText, categoryText, regNoText;

    Button save_btn;
    showProgressbar showprogress;
    private GroupsList groupsList;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    private ArrayList<Group> groupArrayList;
    private ArrayList<CandidateCategory> candidateCategoryArrayList;
    private GroupBackgroundApiTasks groupBackgroundApiTasks;
    private CandidateCategoryBackgroundApiTasks candidateCategoryBackgroundApiTasks;
    private List<String> spinnerArray, categorySpinnerArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        candidateCategoryBackgroundApiTasks
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);
        showprogress = new showProgressbar(this);

        groupSpinner = (Spinner)findViewById(R.id.group_input);
        populateGroupSpinner();
        category = (Spinner)findViewById(R.id.category_input);
        genderSpinner = (Spinner)findViewById(R.id.gender_input);


        firstname = (EditText)findViewById(R.id.first_name_input);
        lastname = (EditText)findViewById(R.id.last_name_input);
        familyname = (EditText)findViewById(R.id.family_name_input);
        mobile_no = (EditText)findViewById(R.id.mobile_no_input);
        other_no = (EditText)findViewById(R.id.other_no_input);
        address = (EditText)findViewById(R.id.address_input);
        education = (EditText)findViewById(R.id.education_input);

        regNo = (EditText)findViewById(R.id.regno__input);

        email = (EditText)findViewById(R.id.email_input);
        dob = (EditText)findViewById(R.id.dob_input);

        showprogress.setMessage("Loading");
        showprogress.show();

        save_btn = (Button)findViewById(R.id.register_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCandidate();
            }
        });
    }



    private void registerCandidate() {

        firstnameText = firstname.getText().toString();
        lastnameText = lastname.getText().toString();
        familynameText = familyname.getText().toString();

        mobile_noText = mobile_no.getText().toString();
        other_noText = other_no.getText().toString();

        addressText = address.getText().toString();
        genderText = genderSpinner.getSelectedItem().toString();
        educationText = education.getText().toString();

        emailText = email.getText().toString();
        dobText = dob.getText().toString();
        if (groupSpinner.getSelectedItem()!=null){

            groupText = groupSpinner.getSelectedItem().toString();

        }else {

            groupText = null;

        }
        categoryText = category.getSelectedItem()!=null?category.getSelectedItem().toString():null;
        regNoText = regNo.getText().toString();
        Integer group_id;
        Group group = groupsList.findGroupByName(groupText);
        group_id = (group!=null)?group.getId():null;

        Integer category_id;
        CandidateCategory candidateCategory = groupsList.findCandidateCategoryByName(categoryText);
        category_id = candidateCategory!=null?candidateCategory.getId():null;

        Candidate newCandidate = new Candidate(null,regNoText,firstnameText,lastnameText,familynameText,
                genderText,emailText,dobText,mobile_noText,other_noText,null,addressText,educationText,group_id,category_id,
                null,null,null,null);

        showprogress.setMessage("A moment as we set you up!");
        showprogress.show();
        new sendRegistrationData().execute(newCandidate);

    }


    private void populateGroupSpinner() {

        groupsList = new GroupsList(this);
        spinnerArray = new ArrayList<>();
        GroupsAdapter current_adapter = GroupsAdapter.getInstance(null, null);
        if (current_adapter != null ){

            groupsList.makeListFrom(current_adapter.getGroupArrayList());
            spinnerArray = groupsList.getSpinnerArray();
            setGroupSpinner();
        }
        else {

            groupBackgroundApiTasks = new GroupBackgroundApiTasks(RegistrationActivity.this);
            fetchAllGroups();
        }

    }


    public void setGroupSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
        fetchAllCategories();

    }

    public void setCategorySpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                                                            groupsList.categorySpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        showprogress.dismiss();

    }


    private void fetchAllGroups() {
        new fetchgroups().execute();
    }

    private class fetchgroups extends AsyncTask<Void,Void, ArrayList<Group>> {

        @Override
        protected void onPostExecute(ArrayList<Group> arrayList) {

            groupArrayList = arrayList;
            groupsList.makeListFrom(groupArrayList);
            spinnerArray = groupsList.getSpinnerArray();
            setGroupSpinner();
        }

        @Override
        protected ArrayList<Group> doInBackground(Void... voids) {

            ArrayList<Group> groupArrayListReturned;

            synchronized (groupBackgroundApiTasks){

                groupBackgroundApiTasks.fetchGroups();
                try {
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                groupArrayListReturned = groupBackgroundApiTasks.getGroupArrayList();

            }
            return groupArrayListReturned;

        }
    }

    private void fetchAllCategories() {

        candidateCategoryBackgroundApiTasks = new CandidateCategoryBackgroundApiTasks(this);
        new fetchCategories().execute();

    }

    public class fetchCategories extends AsyncTask<Void,Void,ArrayList<CandidateCategory>>{

        @Override
        protected void onPostExecute(ArrayList<CandidateCategory> candidateCategories) {

            candidateCategoryArrayList = candidateCategories;
            Log.e("post execute", "onPostExecute: "+candidateCategoryArrayList );
            groupsList.makeCategoryListFrom(candidateCategoryArrayList);
//            categorySpinnerArray = groupsList.getCategorySpinnerArray();
            setCategorySpinner();

        }

        @Override
        protected ArrayList<CandidateCategory> doInBackground(Void... voids) {

            ArrayList<CandidateCategory> candidateCategoryArrayListReturned;

            synchronized (candidateCategoryBackgroundApiTasks){

                candidateCategoryBackgroundApiTasks.fetchCategories();
                try {
                    candidateCategoryBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                candidateCategoryArrayListReturned = candidateCategoryBackgroundApiTasks.getCandidateCategoryArrayList();
            }
            return candidateCategoryArrayListReturned;
        }
    }


    public class sendRegistrationData extends AsyncTask<Candidate,Void,String>{


        @Override
        protected void onPostExecute(String s) {

            showprogress.dismiss();
            Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(Candidate... candidates) {

            String message;

            synchronized (candidateBackgroundApiTasks){

                try {
                    candidateBackgroundApiTasks.registerNewCandidate(candidates[0]);
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message = candidateBackgroundApiTasks.getMessage();

            }

            return message;
        }
    }

}
