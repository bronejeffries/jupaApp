package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.GroupsList;
import com.example.jupa.Helpers.PaymentChoiceHelper;
import com.example.jupa.Helpers.SelectFileHelper;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;
import com.example.jupa.Request.Api.RequestApiBackgroundTasks;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

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

    ImageView recommendationView;
    Bitmap mBitmap;

    Boolean initial_request = false,restarting = false;
    public static Boolean recommendation_selection_started = true;
    RequestApplicationObject requestApplicationObject;
    SelectFileHelper selectFileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessor_request);

        showProgress = new showProgressbar(this);
        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);
        requestApiBackgroundTasks = RequestApiBackgroundTasks.getInstance(this);

        ApplyingCandidate = getIntent().getParcelableExtra(RankRequestActivity.CANDIDATE_EXTRA);
        initial_request = getIntent().getBooleanExtra(RankRequestActivity.INITIAL_RANK,false);

        selectFileHelper = SelectFileHelper.getInstance();

        if (ApplyingCandidate==null){

            ApplyingCandidate = UserHomeActivity.thisCandidate;

        }

        regNoInput = (EditText)findViewById(R.id.regno__input);
        regNoInput.setText(ApplyingCandidate.getRegistration_no());
        regNoInput.setEnabled(false);

        years_of_experience = (EditText)findViewById(R.id.experience_input);
        present_qualification = (EditText)findViewById(R.id.qualifications_input);
        reason = (EditText)findViewById(R.id.reason_for_application_input);
        institutionSpinner = (Spinner)findViewById(R.id.institution_attachment_input);
        recommendationView = (ImageView)findViewById(R.id.recommendation_input);
        submitRequest = (Button)findViewById(R.id.request_submission_btn);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                institution = groupsList.getInstitutionArrayList().get(institutionSpinner.getSelectedItemPosition());
                regNoText = regNoInput.getText().toString();
                experience_text = years_of_experience.getText().toString();
                qualificationText = present_qualification.getText().toString();
                reasonText = reason.getText().toString();

                if (isValid()){

                    requestApplicationObject = new RequestApplicationObject(RequestApplicationViewActivity.ASSESSOR_REQUEST_TYPE, ApplyingCandidate.getId(),regNoText,
                            experience_text,qualificationText,reasonText,ApplyingCandidate.getGroup(),0,null,institution.getInstitution_id(),null);
                    String recommendation = selectFileHelper.getStringImage(mBitmap);
                    requestApplicationObject.setFile_body(recommendation);
                    submitRequestApplication(requestApplicationObject);

                }else {
                    Toast.makeText(AssessorRequestActivity.this, "Please Check the information provided", Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });

        loadInstitutions();

    }

    private boolean isValid() {
        boolean valid = true;

        if (experience_text.isEmpty()){

            valid = false;
            years_of_experience.setError("Required");

        }

        if (qualificationText.isEmpty()){

            valid = false;
            present_qualification.setError("Required");

        }

        if (reasonText.isEmpty()){

            valid = false;
            reason.setError("Required");

        }

//        if (mBitmap == null){
//
//            valid = false;
//            Toast.makeText(this, "Attach recommendation", Toast.LENGTH_SHORT).show();
//
//        }

        return  valid;
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

                PaymentChoiceHelper.createdRequestApplicationObject = requestApplicationObject;
                PaymentChoiceHelper.showApplicationPaymentChoice(AssessorRequestActivity.this,ApplyingCandidate);

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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case SelectFileHelper.ALL_PERMISSIONS_RESULT:

                for (String perms : SelectFileHelper.permissionsToRequest) {

                    if (!SelectFileHelper.hasPermission(perms,this)) {

                        SelectFileHelper.permissionsRejected.add(perms);

                    }

                }

                if (SelectFileHelper.permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(SelectFileHelper.permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(SelectFileHelper.permissionsRejected.toArray(new String[SelectFileHelper.permissionsRejected.size()]), SelectFileHelper.ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SelectFileHelper.IMAGE_RESULT) {

                String filePath = SelectFileHelper.getImageFilePath(data,this);
                if (filePath != null) {
                    mBitmap = BitmapFactory.decodeFile(filePath);
                    recommendationView.setImageBitmap(mBitmap);
                    recommendationView.setVisibility(View.VISIBLE);
                    AssessorRequestActivity.recommendation_selection_started = true;
                }
            }

        }

    }

    public void selectRecommendation(View view){

        AssessorRequestActivity.recommendation_selection_started = true;
        SelectFileHelper.askPermissions(this);
        startActivityForResult(SelectFileHelper.getPickImageChooserIntent(this), SelectFileHelper.IMAGE_RESULT);

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new android.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    @Override
    public void onBackPressed() {

        if (initial_request&&!restarting){

            moveTaskToBack(true);

        }else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!AssessorRequestActivity.recommendation_selection_started){
            restarting =true;
            onBackPressed();
        }else {
            AssessorRequestActivity.recommendation_selection_started = false;
        }
    }
}