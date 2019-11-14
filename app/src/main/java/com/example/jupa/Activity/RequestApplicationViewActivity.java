package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;
import com.example.jupa.Rank.Api.RankBackgroundApiTasks;
import com.example.jupa.Rank.Rank;
import com.example.jupa.Request.Adapter.RequestApplicationAdapter;
import com.example.jupa.Request.Api.RequestApiBackgroundTasks;
import com.example.jupa.Request.RequestApplicationObject;

public class RequestApplicationViewActivity extends AppCompatActivity {

    TextView titleView, regNoInput, years_of_experience, present_qualification, reason, rank_applied_for, institutionView;
    LinearLayout rank_layout, institution_layout;
    Button confirm_button, decline_button, forward_button;
    RequestApplicationObject thisRequestApplicationObject;
    RequestApiBackgroundTasks requestApiBackgroundTasks;
    public static final String ASSESSOR_REQUEST_TYPE ="ASSESSOR", RANK_REQUEST_TYPE = "RANK", REQUEST_EXTRA = "APPLICATION",
                                POSITION_EXTRA="position",VIEW_PLATFORM_EXTRA="view platform";
    public boolean assessorType, rankType,personal_view;
    public String TitleText;
    Intent intent;
    private int approval_status,adapterPosition,view_platform;
    showProgressbar showProgress;
    ProgressBar rank_progress, institution_progress;
    RankBackgroundApiTasks rankBackgroundApiTasks;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;
    Candidate candidate_viewing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_application_view);

        showProgress = new showProgressbar(this);

        intent = getIntent();
        thisRequestApplicationObject = intent.getParcelableExtra(REQUEST_EXTRA);
        adapterPosition = intent.getIntExtra(POSITION_EXTRA,0);

        view_platform = intent.getIntExtra(VIEW_PLATFORM_EXTRA,0);
        personal_view = (view_platform==RequestApplicationAdapter.PERSONAL_VIEW);

        requestApiBackgroundTasks = RequestApiBackgroundTasks.getInstance(this);
        rankBackgroundApiTasks = RankBackgroundApiTasks.getInstance(this);
        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);
        candidate_viewing = UserHomeActivity.thisCandidate;

        checkApplicationType();

        titleView = (TextView)findViewById(R.id.page_title);
        titleView.setText(getTitleText());

        regNoInput = (TextView)findViewById(R.id.regno__view);
        regNoInput.setText(thisRequestApplicationObject.getRegNo());

        years_of_experience = (TextView) findViewById(R.id.experience_view);
        years_of_experience.setText(thisRequestApplicationObject.getExperience());

        present_qualification = (TextView)findViewById(R.id.qualifications_view);
        present_qualification.setText(thisRequestApplicationObject.getQualification());

        reason = (TextView)findViewById(R.id.reason_for_application_view);
        reason.setText(thisRequestApplicationObject.getReason());

        rank_applied_for = (TextView)findViewById(R.id.rank_applied_view);
        rank_layout = (LinearLayout)findViewById(R.id.rank_layout);

        institutionView = (TextView)findViewById(R.id.institution_applied_view);
        institution_layout = (LinearLayout)findViewById(R.id.institution_layout);

        institution_progress = (ProgressBar)findViewById(R.id.institution_progress);
        rank_progress = (ProgressBar)findViewById(R.id.rank_progress);

        if (isAssessorType()){

            rank_layout.setVisibility(View.GONE);
            institution_progress.setVisibility(View.VISIBLE);
            new getCandidateInstitution().execute(thisRequestApplicationObject.getInstitution_id());

        }else if (isRankType()){

            institution_layout.setVisibility(View.GONE);
            rank_progress.setVisibility(View.VISIBLE);
            new getCandidateRank().execute(thisRequestApplicationObject.getRank_id());

        }

        confirm_button = (Button)findViewById(R.id.request_acceptance_btn);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                approval_status = RequestApplicationAdapter.APPROVED;
                thisRequestApplicationObject.setStatus(approval_status);
                thisRequestApplicationObject.setUser_id(candidate_viewing.getId());
                update_request();
            }
        });
        decline_button = (Button)findViewById(R.id.request_denial_btn);
        decline_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                approval_status = RequestApplicationAdapter.REJECTED;
                thisRequestApplicationObject.setStatus(approval_status);
                thisRequestApplicationObject.setUser_id(candidate_viewing.getId());
                showCommentDialog();

            }
        });

        forward_button = (Button)findViewById(R.id.forward_btn);
        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                approval_status = RequestApplicationAdapter.FORWARDED;
                thisRequestApplicationObject.setStatus(approval_status);
                thisRequestApplicationObject.setUser_id(candidate_viewing.getId());
                update_request();

            }
        });

        checkViewLevel();

    }

    private void showCommentDialog() {

        final EditText comment_edit_Text;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.attach_comment_bottom_sheet,null,false);
        comment_edit_Text = (EditText)relativeLayout.findViewById(R.id.attach_comment_input);

        alertBuilder.setView(relativeLayout);
        alertBuilder.setPositiveButton(R.string.add_group, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String comment = comment_edit_Text.getText().toString();
                thisRequestApplicationObject.setComment(comment);
                update_request();
            }
        });

        alertBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        alertBuilder.create().show();

    }


    public void checkApplicationType(){

        switch (thisRequestApplicationObject.getRequest_type()){

            case ASSESSOR_REQUEST_TYPE:
                setAssessorType(true);
                setTitleText(getResources().getString(R.string.become_assessor_request));
                break;

            case RANK_REQUEST_TYPE:
                setRankType(true);
                setTitleText(getResources().getString(R.string.apply_for_a_higher_rank));
                break;

        }

    }

    public void update_request(){

        showProgress.setMessage("Updating Application");
        showProgress.show();
        new updatingApplication().execute(thisRequestApplicationObject);

    }

    public class updatingApplication extends AsyncTask<RequestApplicationObject,Void, RequestApplicationObject>{

        @Override
        protected void onPostExecute(RequestApplicationObject requestApplicationObject) {

            Toast.makeText(RequestApplicationViewActivity.this, requestApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
            showProgress.dismiss();

            if (requestApplicationObject!=null){

                if (personal_view){

                    MyRequestsActivity.requestApplicationAdapter.changeItemStatus(adapterPosition,thisRequestApplicationObject.getStatus());

                }else {

                    ApplicationsActivity.requestApplicationAdapter.changeItemStatus(adapterPosition,thisRequestApplicationObject.getStatus());

                }

                finish();
            }

        }

        @Override
        protected RequestApplicationObject doInBackground(RequestApplicationObject... requestApplicationObjects) {
            RequestApplicationObject requestApplicationObject;

            synchronized (requestApiBackgroundTasks){

                requestApiBackgroundTasks.approveRequest(requestApplicationObjects[0]);

                try {

                    requestApiBackgroundTasks.wait();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                requestApplicationObject = requestApiBackgroundTasks.getRequestApplicationObject();

            }

            return requestApplicationObject;
        }
    }

    public class getCandidateInstitution extends AsyncTask<Integer,Void, Institution>{

        @Override
        protected void onPostExecute(Institution institution) {

            if (institution!=null){

                institutionView.setText(institution.getTitltleRepresentation());

            }else{

                Toast.makeText(RequestApplicationViewActivity.this, institutionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }

            institution_progress.setVisibility(View.GONE);

        }

        @Override
        protected Institution doInBackground(Integer... integers) {

            Institution returnedInstitution;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.get_institutionById(integers[0]);
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

    public class getCandidateRank extends  AsyncTask<Integer, Void, Rank>{


        @Override
        protected void onPostExecute(Rank rank) {

            if (rank!=null){
                rank_applied_for.setText(rank.getName());
            }else {
                Toast.makeText(RequestApplicationViewActivity.this, rankBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

            rank_progress.setVisibility(View.GONE);

        }

        @Override
        protected Rank doInBackground(Integer... integers) {
            Rank returnedRank;

            synchronized (rankBackgroundApiTasks){

                rankBackgroundApiTasks.getRankById(integers[0]);
                try {
                    rankBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedRank = rankBackgroundApiTasks.getRank();

            }

            return returnedRank;

        }
    }

    public void checkViewLevel(){

        if (!candidate_viewing.getId().equals(thisRequestApplicationObject.getCandidate_id())&&thisRequestApplicationObject.getPaid()){

            if (UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.GROUP_ADMIN_ROLE)){

                decline_button.setVisibility(View.VISIBLE);
                forward_button.setVisibility(View.VISIBLE);

            }else if(UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ADMINISTRATOR_ROLE)) {

                decline_button.setVisibility(View.VISIBLE);
                confirm_button.setVisibility(View.VISIBLE);

            }

        }


    }

    public boolean isAssessorType() {
        return assessorType;
    }

    public void setAssessorType(boolean assessorType) {
        this.assessorType = assessorType;
    }

    public boolean isRankType() {
        return rankType;
    }

    public void setRankType(boolean rankType) {
        this.rankType = rankType;
    }

    public String getTitleText() {
        return TitleText;
    }

    public void setTitleText(String titleText) {
        TitleText = titleText;
    }
}
