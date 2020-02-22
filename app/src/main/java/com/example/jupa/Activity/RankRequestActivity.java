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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.GroupsList;
import com.example.jupa.Helpers.PaymentChoiceHelper;
import com.example.jupa.Helpers.SelectFileHelper;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;
import com.example.jupa.Rank.Api.RankBackgroundApiTasks;
import com.example.jupa.Rank.Rank;
import com.example.jupa.Request.Api.RequestApiBackgroundTasks;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RankRequestActivity extends AppCompatActivity {

    public static final String INITIAL_RANK = "initial_rank_request_extra", CANDIDATE_EXTRA = "candidate";
    showProgressbar showProgress;
    RankBackgroundApiTasks rankBackgroundApiTasks;
    RequestApiBackgroundTasks requestApiBackgroundTasks;
    GroupsList groupsList;
    ArrayList<Rank> rankArrayList;
    Candidate ApplyingCandidate;
    RequestApplicationObject requestApplicationObject;
    String regNoText, experience_text, qualificationText, reasonText;

    Integer rank_id, isPaid;

    TextView candidate_rank;

    EditText regNoInput, years_of_experience, present_qualification, reason;
    Spinner rank_appliedFor;
    Button submitRequest;
    ImageView recommendationView;
    Bitmap mBitmap;
    Boolean initial_rank_request = false,restarting = false;
    public static Boolean recommendation_selection_started = true;
    SelectFileHelper selectFileHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_request);

        initial_rank_request = getIntent().getBooleanExtra(INITIAL_RANK,false);

        showProgress = new showProgressbar(this);
        rankBackgroundApiTasks = RankBackgroundApiTasks.getInstance(this);
        requestApiBackgroundTasks = RequestApiBackgroundTasks.getInstance(this);

        recommendationView = (ImageView)findViewById(R.id.recommendation_input);

        ApplyingCandidate = getIntent().getParcelableExtra(CANDIDATE_EXTRA);

        selectFileHelper = SelectFileHelper.getInstance();

        regNoInput = (EditText)findViewById(R.id.regno__input);
        regNoInput.setText(ApplyingCandidate.getRegistration_no());
        regNoInput.setEnabled(false);

        years_of_experience = (EditText)findViewById(R.id.experience_input);
        present_qualification = (EditText)findViewById(R.id.qualifications_input);
        reason = (EditText)findViewById(R.id.reason_for_application_input);

        rank_appliedFor = (Spinner)findViewById(R.id.rank_applied_input);
        candidate_rank = (TextView)findViewById(R.id.candidate_rank);

        submitRequest = (Button)findViewById(R.id.request_submission_btn);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (initial_rank_request){

                    rank_id = 1;
                    isPaid = 1;

                }else{

                    rank_id = groupsList.findRankByName(rank_appliedFor.getSelectedItem().toString()).getId();
                    isPaid = 0;
                }

                regNoText = regNoInput.getText().toString();
                experience_text = years_of_experience.getText().toString();
                qualificationText = present_qualification.getText().toString();
                reasonText = reason.getText().toString();

                if (isValid()){

                    requestApplicationObject = new RequestApplicationObject(RequestApplicationViewActivity.RANK_REQUEST_TYPE,
                            ApplyingCandidate.getId(),regNoText,experience_text,qualificationText,reasonText,
                            ApplyingCandidate.getGroup(),0,rank_id,null,ApplyingCandidate.getAssociation_id());
                    requestApplicationObject.setPaid(isPaid);
                    String recommendation = selectFileHelper.getStringImage(mBitmap);
                    requestApplicationObject.setFile_body(recommendation);
                    submitRequestApplication(requestApplicationObject);

                }else {
                    Toast.makeText(RankRequestActivity.this, "Please Check the information provided", Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });

        if (!initial_rank_request){

            rank_appliedFor.setVisibility(View.VISIBLE);
            loadRanks();

        }else {

            candidate_rank.setVisibility(View.VISIBLE);

        }

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

        if (mBitmap == null){

            valid = false;
            Toast.makeText(this, "Attach recommendation", Toast.LENGTH_SHORT).show();

        }

        return  valid;
    }


    private void loadRanks() {

        showProgress.setMessage("loading Information");
        showProgress.show();
        groupsList = new GroupsList(this);
        new getRanks().execute();

    }

    private void submitRequestApplication(RequestApplicationObject requestApplicationObject) {

        showProgress.setMessage("Submitting Application..");
        showProgress.show();
        new submitRequest().execute(requestApplicationObject);

    }



    public class submitRequest extends AsyncTask<RequestApplicationObject,Void,RequestApplicationObject>{


        @Override
        protected void onPostExecute(RequestApplicationObject requestApplicationObject) {

            Toast.makeText(RankRequestActivity.this, requestApiBackgroundTasks.getMessage(), Toast.LENGTH_LONG).show();
            showProgress.dismiss();

            if (requestApplicationObject!=null){

                if (!initial_rank_request){

                    PaymentChoiceHelper.createdRequestApplicationObject = requestApplicationObject;
                    PaymentChoiceHelper.showApplicationPaymentChoice(RankRequestActivity.this,ApplyingCandidate);

                }else {

                    finish();

                }
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



    public void populateRanksSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,groupsList.getRanksSpinnerArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rank_appliedFor.setAdapter(adapter);
        showProgress.dismiss();
    }


    public class getRanks extends AsyncTask<Void,Void, ArrayList<Rank>> {

        @Override
        protected void onPostExecute(ArrayList<Rank> ranks) {

            rankArrayList = ranks;
            groupsList.makeRankListFrom(rankArrayList);
            populateRanksSpinner();

        }

        @Override
        protected ArrayList<Rank> doInBackground(Void... voids) {

            ArrayList<Rank> returnedArrayList;
            synchronized (rankBackgroundApiTasks){

                rankBackgroundApiTasks.getAllRanks();
                try {
                    rankBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedArrayList = rankBackgroundApiTasks.getRankArrayList();

            }

            return returnedArrayList;
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
                    RankRequestActivity.recommendation_selection_started = true;
                }
            }

        }

    }

    public void selectRecommendation(View view){

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

        if (initial_rank_request&&!restarting){

            moveTaskToBack(true);

        }else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!RankRequestActivity.recommendation_selection_started){
            restarting =true;
            onBackPressed();
        }else {
            RankRequestActivity.recommendation_selection_started = false;
        }
    }

}
