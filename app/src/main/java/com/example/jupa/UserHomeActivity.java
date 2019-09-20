package com.example.jupa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class UserHomeActivity extends AppCompatActivity {


    Candidate thisCandidate;
    CardView ViewMyProfileCard,ViewMyRequestsCard, ViewCandidatesCard, ViewGroupDetailsCard, ViewIncomingRequestsCard, ViewManageQuestionsCard;
    final static String CANDIDATE_ROLE = "Candidate", ASSESSOR_ROLE= "Assessor", GROUP_ADMIN_ROLE = "Group Admin", ADMINISTRATOR_ROLE = "Administrator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        ViewMyProfileCard = (CardView)findViewById(R.id.my_profile_view);
        ViewMyRequestsCard = (CardView)findViewById(R.id.my_requests_view);
        ViewCandidatesCard = (CardView)findViewById(R.id.assessor_candidates_view);
        ViewGroupDetailsCard = (CardView)findViewById(R.id.group_admin_group_details);
        ViewIncomingRequestsCard = (CardView)findViewById(R.id.group_admin_in_coming_requests);
        ViewManageQuestionsCard = (CardView)findViewById(R.id.group_admin_manage_group_questions);

        thisCandidate = LoggedInUser.getInstance().getLoggedInCandidate();
        ManageDisplay();

    }

///////////////////    check the user role and display the appropriate views //////////////////
    private void ManageDisplay() {

        switch (thisCandidate.getRole()){

            case ASSESSOR_ROLE:
                makeVisible(ViewCandidatesCard);
                break;

            case GROUP_ADMIN_ROLE:
                makeVisible(ViewGroupDetailsCard);
                makeVisible(ViewIncomingRequestsCard);
                makeVisible(ViewManageQuestionsCard);
                break;

            case ADMINISTRATOR_ROLE:
                Intent adminHomeIntent = new Intent(this,AdminHomeActivity.class);
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
        startActivity(intent);
    }

    public void ViewMyRequests(View view){

    }

    public void ViewCandidates(View view){

        Intent viewCandidatesIntent = new Intent(this,CandidatesDisplayActivity.class);
        startActivity(viewCandidatesIntent);

    }

    public void ViewGroupDetails(View view){

        Intent intent = new Intent(this,GroupSearchActivity.class);
        intent.putExtra(GroupActivity.GROUP_TAG,thisCandidate.getGroup());
        startActivity(intent);

    }

    public void ViewGroupIncomingRequests(View view){

    }


    public void ManageGroupQuestions(View view){

        AlertDialog.Builder manageQuestionsAlertDialog = new AlertDialog.Builder(this);
        final String[] manageQuestionsAlertDialogOptions = {"View Group Questions","Add New QuestionCategory", "Add New Question"};
        manageQuestionsAlertDialog.setItems(manageQuestionsAlertDialogOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){

                    case 0:
                        viewGroupQuestions();
                        break;
                    case 1:
                        AddNewQuestionCategory();
                        break;
                    case 2:
                        AddNewQuestion();
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

    private void AddNewQuestionCategory() {

        AlertDialog.Builder addNewCategoryDialog = new AlertDialog.Builder(this);
        LinearLayout addCategoryLinearLayout = (LinearLayout) LayoutInflater.from(UserHomeActivity.this).inflate(R.layout.addquestioncategory,null,false);
        addNewCategoryDialog.setView(addCategoryLinearLayout);
        addNewCategoryDialog.setTitle("Add New Question Category");
        addNewCategoryDialog.create().show();

    }

    private void viewGroupQuestions() {

        Intent questionsIntent = new Intent(this,GroupQuestionsActivity.class);
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
                Intent logoutIntent = new Intent(this,LoginActivity.class);
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



}
