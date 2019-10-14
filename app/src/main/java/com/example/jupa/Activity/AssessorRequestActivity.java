package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.GroupsList;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;
import com.example.jupa.Request.Api.RequestApiBackgroundTasks;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

public class AssessorRequestActivity extends AppCompatActivity {


    showProgressbar showProgress;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;
    RequestApiBackgroundTasks requestApiBackgroundTasks;
    GroupsList groupsList;
    ArrayList<Institution> institutionArrayList;
    Candidate ApplyingCandidate;

    String regNoText, experience_text, qualificationText, reasonText;
    Institution institution;

    EditText regNoInput, years_of_experience, present_qualification, reason;
    Spinner institutionSpinner;
    Button submitRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor_request);

        showProgress = new showProgressbar(this);
        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);
        requestApiBackgroundTasks = RequestApiBackgroundTasks.getInstance(this);

        ApplyingCandidate = UserHomeActivity.thisCandidate;

        regNoInput = (EditText)findViewById(R.id.regno__input);
        regNoInput.setText(ApplyingCandidate.getRegistration_no());

        years_of_experience = (EditText)findViewById(R.id.experience_input);
        present_qualification = (EditText)findViewById(R.id.qualifications_input);
        reason = (EditText)findViewById(R.id.reason_for_application_input);
        institutionSpinner = (Spinner)findViewById(R.id.institution_attachment_input);
        submitRequest = (Button)findViewById(R.id.request_submission_btn);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                institution = groupsList.getInstitutionArrayList().get(institutionSpinner.getSelectedItemPosition());
                regNoText = regNoInput.getText().toString();
                experience_text = years_of_experience.getText().toString();
                qualificationText = present_qualification.getText().toString();
                reasonText = reason.getText().toString();
                RequestApplicationObject newApplication = new RequestApplicationObject(RequestApplicationViewActivity.ASSESSOR_REQUEST_TYPE, ApplyingCandidate.getId(),regNoText,
                                                                                        experience_text,qualificationText,reasonText,ApplyingCandidate.getGroup(),
                                                                                        0,null,institution.getInstitution_id());
                submitRequestApplication(newApplication);
            }
        });

        loadInstitutions();

    }

    private void loadInstitutions() {

        showProgress.setMessage("loading Information");
        showProgress.show();
        groupsList = new GroupsList(this);
        new getInstitutions().execute();

    }

    private void submitRequestApplication(RequestApplicationObject requestApplicationObject) {

        showProgress.setMessage("Submitting Application..");
        showProgress.show();
        new submitRequest().execute(requestApplicationObject);

    }

    public void populateRanksSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,groupsList.getInstitutionSpinnerArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        institutionSpinner.setAdapter(adapter);
        showProgress.dismiss();

    }



    public class getInstitutions extends AsyncTask<Void,Void,ArrayList<Institution>>{

        @Override
        protected void onPostExecute(ArrayList<Institution> institutions) {

            institutionArrayList = institutions;
            groupsList.makeInstitutionListFrom(institutionArrayList);
            populateRanksSpinner();

        }

        @Override
        protected ArrayList<Institution> doInBackground(Void... voids) {

            ArrayList<Institution> returnedInstitution;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.getAllInstitutions();
                try {
                    institutionApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedInstitution = institutionApiBackgroundTasks.getInstitutionArrayList();
            }

            return returnedInstitution;
        }
    }


    public class submitRequest extends AsyncTask<RequestApplicationObject,Void,RequestApplicationObject>{


        @Override
        protected void onPostExecute(RequestApplicationObject requestApplicationObject) {

            Toast.makeText(AssessorRequestActivity.this, requestApiBackgroundTasks.getMessage(), Toast.LENGTH_LONG).show();
            showProgress.dismiss();

            if (requestApplicationObject!=null){

                MyRequestsActivity.requestApplicationAdapter.addItem(requestApplicationObject);
                finish();

            }

        }

        @Override
        protected RequestApplicationObject doInBackground(RequestApplicationObject... requestApplicationObjects) {

            RequestApplicationObject returnedApplication;

            synchronized (requestApiBackgroundTasks){

                requestApiBackgroundTasks.submitRequest(requestApplicationObjects[0]);
                try {
                    requestApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedApplication = requestApiBackgroundTasks.getRequestApplicationObject();

            }

            return returnedApplication;

        }
    }

}
