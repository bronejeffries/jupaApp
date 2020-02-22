package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Helpers.LoadImageToView;
import com.example.jupa.Helpers.LoggedInInstitution;
import com.example.jupa.Helpers.PaymentDetails;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

public class InstitutionActivity extends AppCompatActivity {

    TextView Welcome;
    Institution institution;
    LinearLayout candidates_more, assessor_more;
    RelativeLayout relativeLayout;
    Boolean candidates_more_open=false,assessors_more_open=false,InstitutionLogin=false,search_purpose=false;
    public static final String INSTITUTION_EXTRA = "institution_extra";
    Intent intent;
    Button verify_inst,disable_inst;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;
    private final int amount=3500;
    showProgressbar showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution);

        showProgress = new showProgressbar(this);

        verify_inst = (Button)findViewById(R.id.verify_inst_btn);
        disable_inst = (Button)findViewById(R.id.disable_inst_btn);
        intent = getIntent();
        institution = LoggedInInstitution.getInstance().getLoggedInInstitution();
        search_purpose = intent.getBooleanExtra(InstitutionsActivity.SEARCH_PURPOSE,false);
        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);

        verify_inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress.setMessage("Verifying Institution..");
                showProgress.show();
                institution.setStatus(1);
                new verifyInstitutionStatus().execute(institution);
            }
        });

        disable_inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress.setMessage("Disabling Institution...");
                showProgress.show();
                institution.setStatus(0);
                new verifyInstitutionStatus().execute(institution);
            }
        });

//        if an institute has logged in
        if (institution!=null){

            setInstitutionLogin(true);
            checkPayment();

        }else {

//        if an institute has not logged in
           institution = intent.getParcelableExtra(INSTITUTION_EXTRA);
           setInstitutionLogin(false);
           checkStatus();

        }

        if (search_purpose!=null && search_purpose){

            viewCandidatesClick(null);

        }else {

            Welcome = (TextView)findViewById(R.id.welcome_text);
            String welcome_text = institution.getName()+" ("+ institution.getCenter_No() +")";
            Welcome.setText(welcome_text);

            candidates_more = (LinearLayout)findViewById(R.id.view_candidates_more);
            assessor_more = (LinearLayout)findViewById(R.id.view_assessors_more);
            relativeLayout = (RelativeLayout)findViewById(R.id.relative_home);
            LoadImageToView.setAsBackground(this,institution.getPhoto_url(),relativeLayout);

        }

    }

    public void checkPayment(){

        if (!institution.getPaid()){

            startPaymentIntent();

        }

    }

    public void checkStatus(){
        if (institution.getStatus().equals(0)){

            verify_inst.setVisibility(View.VISIBLE);

        }else{

            disable_inst.setVisibility(View.VISIBLE);
        }
    }

    private void startPaymentIntent() {

        PaymentDetails paymentDetails = new PaymentDetails(institution.getEmail_address(),institution.getName(),institution.getCenter_No(),"Payment for Registration",
                                                            institution.getCenter_No(),"UG","UGX",amount);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.PAYMENT_DETAILS_EXTRA,paymentDetails);
        startActivityForResult(intent,PaymentActivity.RESULT_CODE);

    }

    public void viewCandidatesClick(View view){

        Intent groupsIntent = new Intent(this,GroupsActivity.class);
        groupsIntent.putExtra(GroupsActivity.INTENT_PURPOSE,UserHomeActivity.CANDIDATE_ROLE);
        groupsIntent.putExtra(GroupsActivity.INSTITUTION_EXTRA,institution);
        startActivity(groupsIntent);

    }

    public void viewAllCandidatesClick(View view){

        Intent candidatesIntent = new Intent(this,GroupActivity.class);
        candidatesIntent.putExtra(GroupActivity.MEMBER_LEVEL,UserHomeActivity.CANDIDATE_ROLE);
        candidatesIntent.putExtra(GroupActivity.INSTITUTION,institution);
        candidatesIntent.putExtra(GroupActivity.INSTITUTION_CANDIDATE_ASSESSOR_VIEW,true);
        startActivity(candidatesIntent);

    }


    public void viewAssessorsClicked(View view){

        Intent groupsIntent = new Intent(this,GroupsActivity.class);
        groupsIntent.putExtra(GroupsActivity.INTENT_PURPOSE,UserHomeActivity.ASSESSOR_ROLE);
        groupsIntent.putExtra(GroupsActivity.INSTITUTION_EXTRA,institution);
        startActivity(groupsIntent);
    }

    public void viewAllAssessorsClick(View view){

        Intent assessorsIntent = new Intent(this,GroupActivity.class);
        assessorsIntent.putExtra(GroupActivity.MEMBER_LEVEL,UserHomeActivity.ASSESSOR_ROLE);
        assessorsIntent.putExtra(GroupActivity.INSTITUTION,institution);
        assessorsIntent.putExtra(GroupActivity.INSTITUTION_CANDIDATE_ASSESSOR_VIEW,true);
        startActivity(assessorsIntent);

    }

    public void viewResultsClicked(View view){

        Intent results_intent = new Intent(this, InstitutionResultsActivity.class);
        results_intent.putExtra(InstitutionResultsActivity.Institution_Extra,institution);
        startActivity(results_intent);

    }

    public void viewInstitutionProfile(View view){

        Intent institution_intent = new Intent(this, InstitutionProfileActivity.class);
        institution_intent.putExtra(InstitutionActivity.INSTITUTION_EXTRA,institution);
        startActivity(institution_intent);

    }

    public void coursesClicked(View view){

        Intent institution_courses_intent = new Intent(this, InstitutionCoursesActivity.class);
        institution_courses_intent.putExtra(InstitutionCoursesActivity.INSTITUTION_EXTRA,institution);
        startActivity(institution_courses_intent);

    }

    public void viewCandidatesMore(View view){

        if (!getCandidates_more_open()){

            candidates_more.setVisibility(View.VISIBLE);
            setCandidates_more_open(true);
        }else {

            candidates_more.setVisibility(View.GONE);
            setCandidates_more_open(false);
        }

    }

    public void viewAssessorsMore(View view){

        if (!getAssessors_more_open()){

            assessor_more.setVisibility(View.VISIBLE);
            setAssessors_more_open(true);
        }else {

            assessor_more.setVisibility(View.GONE);
            setAssessors_more_open(false);
        }

    }

    public Boolean getCandidates_more_open() {
        return candidates_more_open;
    }

    public void setCandidates_more_open(Boolean candidates_more_open) {
        this.candidates_more_open = candidates_more_open;
    }

    public Boolean getAssessors_more_open() {
        return assessors_more_open;
    }

    public void setAssessors_more_open(Boolean assessors_more_open) {
        this.assessors_more_open = assessors_more_open;
    }

    public Boolean getInstitutionLogin() {
        return InstitutionLogin;
    }

    public void setInstitutionLogin(Boolean institutionLogin) {
        InstitutionLogin = institutionLogin;
    }

    @Override
    public void onBackPressed() {

        if (getInstitutionLogin()){

            moveTaskToBack(true);

        }else {

            super.onBackPressed();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PaymentActivity.RESULT_CODE){

            Boolean result = false;

            if (data!=null){

                result = data.getBooleanExtra(PaymentActivity.RESULT_INTENT_DATA,false);

            }

            handleResult(result);

        }

    }

    private void handleResult(Boolean result) {

        if (result){
            showProgress.setMessage("Updating payment information");
            new verifyInstitutionPayment().execute(institution);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.userhomemenu,menu);

        return true;

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.logout_link:
                Intent logoutIntent = new Intent(this, MainActivity.class);
                startActivity(logoutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart", "onRestart: "+intent.getBooleanExtra(InstitutionsActivity.SEARCH_PURPOSE,false));
        if(intent.getBooleanExtra(InstitutionsActivity.SEARCH_PURPOSE,false)){
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "onResume: "+intent.getBooleanExtra(InstitutionsActivity.SEARCH_PURPOSE,false));
    }

    public class verifyInstitutionPayment extends AsyncTask<Institution,Void,String >{
        @Override
        protected void onPostExecute(String s) {

            if(s!=null&&s.equals("success")){

                institution.setPaid(1);
                checkPayment();
                Toast.makeText(InstitutionActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(InstitutionActivity.this, s, Toast.LENGTH_SHORT).show();

            }

            showProgress.dismiss();


        }

        @Override
        protected String doInBackground(Institution... institutions) {
            String message=null;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.payRequest(institutions[0],amount,RequestApplicationViewActivity.action);

                try {

                    institutionApiBackgroundTasks.wait();
                    message = institutionApiBackgroundTasks.getMessage();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }
            return message;
        }
    }

    public class verifyInstitutionStatus extends AsyncTask<Institution,Void,String >{
        @Override
        protected void onPostExecute(String s) {

            if(s!=null&&s.equals("success")){

                institution.setStatus(1);
                verify_inst.setVisibility(View.GONE);
                Toast.makeText(InstitutionActivity.this, "Institution Successfully Verified", Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(InstitutionActivity.this, s, Toast.LENGTH_SHORT).show();

            }

            showProgress.dismiss();

        }

        @Override
        protected String doInBackground(Institution... institutions) {
            String message=null;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.verifyInstitution(institutions[0]);

                try {

                    institutionApiBackgroundTasks.wait();
                    message = institutionApiBackgroundTasks.getMessage();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }
            return message;
        }
    }

}
