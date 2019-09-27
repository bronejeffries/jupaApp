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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    Candidate thisCandidate;
    CardView ViewMyProfileCard,ViewMyRequestsCard, ViewCandidatesCard, ViewGroupDetailsCard, ViewIncomingRequestsCard, ViewManageQuestionsCard;
    public final static String CANDIDATE_ROLE = "Candidate", ASSESSOR_ROLE= "Assessor", GROUP_ADMIN_ROLE = "Group Admin", ADMINISTRATOR_ROLE = "Administrator";
    GroupBackgroundApiTasks groupBackgroundApiTasks;
    public static QuestionApiBackgroundTasks questionApiBackgroundTasks;
    static Group candidateGroup;
    showProgressbar showProgress;
    public static String loggedInUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        showProgress = new showProgressbar(this);
        showProgress.setMessage("Loading information....a moment");
        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(this);
        questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(this);
        ViewMyProfileCard = (CardView)findViewById(R.id.my_profile_view);
        ViewMyRequestsCard = (CardView)findViewById(R.id.my_requests_view);
        ViewCandidatesCard = (CardView)findViewById(R.id.assessor_candidates_view);
        ViewGroupDetailsCard = (CardView)findViewById(R.id.group_admin_group_details);
        ViewIncomingRequestsCard = (CardView)findViewById(R.id.group_admin_in_coming_requests);
        ViewManageQuestionsCard = (CardView)findViewById(R.id.group_admin_manage_group_questions);
        thisCandidate = LoggedInUser.getInstance().getLoggedInCandidate();
        loggedInUserRole = thisCandidate.getRole();

        showProgress.show();
        new getCandidateGroup().execute(thisCandidate.getId());

    }

///////////////////    check the user role and display the appropriate views //////////////////
    private void ManageDisplay() {

        switch (loggedInUserRole){

            case ASSESSOR_ROLE:
                makeVisible(ViewCandidatesCard);
                break;

            case GROUP_ADMIN_ROLE:
                makeVisible(ViewGroupDetailsCard);
                makeVisible(ViewIncomingRequestsCard);
                makeVisible(ViewManageQuestionsCard);
                break;

            case ADMINISTRATOR_ROLE:
                Intent adminHomeIntent = new Intent(this, AdminHomeActivity.class);
                startActivity(adminHomeIntent);
                break;
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


    }


    public void ManageGroupQuestions(View view){

        AlertDialog.Builder manageQuestionsAlertDialog = new AlertDialog.Builder(this);
        final String[] manageQuestionsAlertDialogOptions = {"View Group Questions","Add New QuestionCategory"};
        manageQuestionsAlertDialog.setItems(manageQuestionsAlertDialogOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){

                    case 0:
                        viewGroupQuestions();
                        break;
                    case 1:
                        AddNewQuestionCategory(UserHomeActivity.this);
                        break;
                }

            }
        });

        AlertDialog alertDialog = manageQuestionsAlertDialog.create();
        alertDialog.show();


    }

    private void AddNewQuestion() {

        AlertDialog.Builder addNewQuestionDialog = new AlertDialog.Builder(this);
        LinearLayout addQuestionLinearLayout = (LinearLayout) LayoutInflater.from(UserHomeActivity.this).inflate(R.layout.addnewquestiondialog,null,false);
        addNewQuestionDialog.setView(addQuestionLinearLayout);
        addNewQuestionDialog.setTitle("Add New Question");
        addNewQuestionDialog.create().show();

    }

    public static void AddNewQuestionCategory(Context context) {

        AlertDialog.Builder addNewCategoryDialog = new AlertDialog.Builder(context);
        LinearLayout addCategoryLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.addquestioncategory,null,false);
        final EditText category = (EditText)addCategoryLinearLayout.findViewById(R.id.category_input);
        Button save_btn = (Button) addCategoryLinearLayout.findViewById(R.id.save_category_btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QuestionCategory questionCategory = new QuestionCategory(category.getText().toString(), candidateGroup.getId());

            }
        });

        addNewCategoryDialog.setView(addCategoryLinearLayout);
        addNewCategoryDialog.setTitle("Add New Question Category");
        addNewCategoryDialog.create().show();

    }

    private void viewGroupQuestions() {

        Intent questionsIntent = new Intent(this, GroupQuestionsActivity.class);
        questionsIntent.putExtra(GroupQuestionsActivity.GROUP_EXTRA,thisCandidate.getGroup());
        startActivity(questionsIntent);

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
                Intent logoutIntent = new Intent(this, LoginActivity.class);
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


    public static class addQuestionCategory extends AsyncTask<QuestionCategory,Void, QuestionCategory>{

        @Override
        protected void onPostExecute(QuestionCategory questionCategory) {

            super.onPostExecute(questionCategory);
        }

        @Override
        protected QuestionCategory doInBackground(QuestionCategory... questionCategories) {
            QuestionCategory returnedQuestionCategory;

            synchronized (questionApiBackgroundTasks){

                questionApiBackgroundTasks.createQuestionCategory(questionCategories[0]);
                try {
                    questionApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedQuestionCategory = questionApiBackgroundTasks.getQuestionCategory();
            }


            return returnedQuestionCategory;
        }
    }

    public class getCandidateGroup extends AsyncTask<Integer,Void,Group>{

        @Override
        protected void onPostExecute(Group group) {

            if (group!=null){
                candidateGroup = group;
                ManageDisplay();
            }else {
                Toast.makeText(UserHomeActivity.this, "Failed to load user information, please consider logging in again", Toast.LENGTH_SHORT).show();
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


}
