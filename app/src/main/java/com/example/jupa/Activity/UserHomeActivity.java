package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;
import com.example.jupa.Helpers.showProgressbar;

public class UserHomeActivity extends AppCompatActivity {

    public static Candidate thisCandidate;
    CardView ViewMyProfileCard,ViewMyRequestsCard, ViewCandidatesCard, ViewGroupDetailsCard, ViewIncomingRequestsCard;
    TextView not_verified;
    public final static String CANDIDATE_ROLE = "Candidate", ASSESSOR_ROLE= "Assessor", GROUP_ADMIN_ROLE = "Group Admin", ADMINISTRATOR_ROLE = "Administrator";
    GroupBackgroundApiTasks groupBackgroundApiTasks;
    static Group candidateGroup;
    showProgressbar showProgress;
    public static String loggedInUserRole;
    public static Boolean ACCOUNT_STATUS,search_purpose;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        intent = getIntent();

        search_purpose = intent.getBooleanExtra(InstitutionsActivity.SEARCH_PURPOSE,false);

        if (search_purpose){

            candidateGroup = intent.getParcelableExtra(GroupQuestionsActivity.GROUP_EXTRA);

            if (candidateGroup!=null){

                ViewGroupDetails(null);

            }

        }else {

            showProgress = new showProgressbar(this);
            showProgress.setMessage("Checking information....a moment");
            groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(this);
            ViewMyProfileCard = (CardView)findViewById(R.id.my_profile_view);
            ViewMyRequestsCard = (CardView)findViewById(R.id.my_requests_view);
            ViewCandidatesCard = (CardView)findViewById(R.id.assessor_candidates_view);
            ViewGroupDetailsCard = (CardView)findViewById(R.id.group_admin_group_details);
            ViewIncomingRequestsCard = (CardView)findViewById(R.id.group_admin_in_coming_requests);
//        ViewManageQuestionsCard = (CardView)findViewById(R.id.group_admin_manage_group_questions);
            thisCandidate = LoggedInUser.getInstance().getLoggedInCandidate();
            loggedInUserRole = thisCandidate.getRole();
            not_verified = findViewById(R.id.not_verified);

            ACCOUNT_STATUS = thisCandidate.getStatus() != null && thisCandidate.getStatus().equals((getResources().getStringArray(R.array.status_array)[2]));

            showProgress.show();
            Log.e("candidate_group", "onCreate: "+thisCandidate.getGroup() );
            new getCandidateGroup().execute(thisCandidate.getGroup());

        }

    }

///////////////////    check the user role and display the appropriate views //////////////////
    private void ManageDisplay() {

        makeVisible(ViewMyRequestsCard);

        if (ACCOUNT_STATUS){

            switch (loggedInUserRole){

                case ASSESSOR_ROLE:
                    makeVisible(ViewCandidatesCard);
                    break;

                case GROUP_ADMIN_ROLE:
                    makeVisible(ViewGroupDetailsCard);
                    makeVisible(ViewIncomingRequestsCard);
//                    makeVisible(ViewManageQuestionsCard);
                    break;

                case ADMINISTRATOR_ROLE:
                    Intent adminHomeIntent = new Intent(this, AdminHomeActivity.class);
                    startActivity(adminHomeIntent);
                    break;
            }

        }else {

            not_verified.setVisibility(View.VISIBLE);

        }

    }

// //////////////////////////////////////////////////////////////////////////////////////

//////////////// make specific card visible if user has rights ///////////
    private void makeVisible(CardView cardView) {

        cardView.setVisibility(View.VISIBLE);

    }

    public void ViewMyProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.CANDIDATE_EXTRA,thisCandidate);
        intent.putExtra(GroupActivity.GROUP_TAG,candidateGroup);
        startActivity(intent);
    }

    public void ViewMyRequests(View view){

        Intent myRequestsIntent = new Intent(this,MyRequestsActivity.class);
        myRequestsIntent.putExtra(RankRequestActivity.CANDIDATE_EXTRA,thisCandidate);
        startActivity(myRequestsIntent);
    }

    public void ViewCandidates(View view){

        Intent viewCandidatesIntent = new Intent(this, CandidatesDisplayActivity.class);
        startActivity(viewCandidatesIntent);

    }

    public void ViewGroupDetails(View view){

        Intent intent = new Intent(this, GroupSearchActivity.class);
        intent.putExtra(GroupActivity.GROUP_TAG,candidateGroup);
        startActivity(intent);

    }

    public void ViewGroupIncomingRequests(View view){

        Intent intent = new Intent(this,ApplicationsActivity.class);
        intent.putExtra(ApplicationsActivity.GROUP_ADMIN_VIEW,true);
        intent.putExtra(ApplicationsActivity.GROUP_ID,thisCandidate.getId());
        startActivity(intent);

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
    public void onBackPressed() {

        moveTaskToBack(true);

    }


    public class getCandidateGroup extends AsyncTask<Integer,Void,Group>{

        @Override
        protected void onPostExecute(Group group) {

            if (group!=null){

                candidateGroup = group;
                ManageDisplay();

            }else {

                Toast.makeText(UserHomeActivity.this, "Failed to load user information, please consider logging in again", Toast.LENGTH_LONG).show();

            }

            showProgress.dismiss();

        }

        @Override
        protected Group doInBackground(Integer... integers) {
            Group returnedGroup;

            synchronized (groupBackgroundApiTasks){

                groupBackgroundApiTasks.getGroupById(integers[0]);
                try {
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedGroup = groupBackgroundApiTasks.getGroup();
            }

            return returnedGroup;
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


}
