package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.example.jupa.Assessment.AssessmentGroup.Adapter.AssessmentGroupsAdapter;
import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.R;
import com.example.jupa.Helpers.showProgressbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ProjectAssessmentsActivity extends AppCompatActivity {

    public static final String CANDIDATE_PROJECT_EXTRA ="candidate_project" ;

    public static CandidateProject candidateProject;
    RecyclerView assessmentRecyclerView;
    Button add_new_assessment_group;
    Intent intent;
    ArrayList<AssessmentGroup> assessmentGroupArrayList;
    LinearLayoutManager linearLayoutManager;
    public static AssessmentGroupsAdapter assessmentGroupsAdapter;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_assessments);

        intent = getIntent();

        candidateProject = intent.getParcelableExtra(CANDIDATE_PROJECT_EXTRA);
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        assessmentRecyclerView = (RecyclerView)findViewById(R.id.assessments_recycler_view);

        add_new_assessment_group = (Button)findViewById(R.id.start_new_assessment_group);

        if (UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ASSESSOR_ROLE)){

            add_new_assessment_group.setVisibility(View.VISIBLE);
            add_new_assessment_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showStartNewAssessmentBottomSheet();

                }
            });

        }


        linearLayoutManager = new LinearLayoutManager(this);
        assessmentRecyclerView.setLayoutManager(linearLayoutManager);

        assessmentGroupsAdapter = new AssessmentGroupsAdapter(assessmentGroupArrayList,this);
        assessmentRecyclerView.setAdapter(assessmentGroupsAdapter);

        showProgress = new showProgressbar(this);
        showProgress.setMessage("Loading Previous Assessments..");
        showProgress.show();
        new getAssessmentGroups().execute(candidateProject.getProject_id());





    }

    private void showStartNewAssessmentBottomSheet() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.start_new_assessment_group,null,false);
        Button save_btn = linearLayout.findViewById(R.id.save_assessment_group_btn);
        final EditText nameInput = linearLayout.findViewById(R.id.assessment_group_input);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int assessor_id = LoggedInUser.getInstance().getLoggedInCandidate().getId();
                String name = nameInput.getText().toString();
                AssessmentGroup newAssessorGroup = new AssessmentGroup(candidateProject.getProject_id(),name,assessor_id);
                saveAssessmentGroup(newAssessorGroup);
            }
        });

        bottomSheetDialog.setContentView(linearLayout);

    }

    private void saveAssessmentGroup(AssessmentGroup assessorGroup) {

        showProgress.setMessage("A moment....");
        showProgress.show();
        new AddNewAssessmentGroup().execute(assessorGroup);

    }



    public class getAssessmentGroups extends AsyncTask<Integer,Void,ArrayList<AssessmentGroup>>{


        @Override
        protected void onPostExecute(ArrayList<AssessmentGroup> assessmentGroups) {

            if (assessmentGroups!=null){
                assessmentGroupsAdapter.addArrayList(assessmentGroups);
            }else {
                Toast.makeText(ProjectAssessmentsActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_LONG).show();
            }

            showProgress.dismiss();

        }

        @Override
        protected ArrayList<AssessmentGroup> doInBackground(Integer... integers) {
            ArrayList<AssessmentGroup> returnedAssessmentGroups;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.getProjectAssessmentGroups(integers[0]);

                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedAssessmentGroups = candidateBackgroundApiTasks.getAssessmentGroupArrayList();

            }


            return returnedAssessmentGroups;
        }
    }

    public class AddNewAssessmentGroup extends AsyncTask<AssessmentGroup,Void,AssessmentGroup>{

        @Override
        protected void onPostExecute(AssessmentGroup assessmentGroup) {

            if (assessmentGroup!=null){

                assessmentGroupsAdapter.addItem(assessmentGroup);

            }

            Toast.makeText(ProjectAssessmentsActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_LONG).show();
            showProgress.dismiss();

        }

        @Override
        protected AssessmentGroup doInBackground(AssessmentGroup... assessmentGroups) {
            AssessmentGroup returnedAssessmentGroup;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.addAssessmentGroupToProject(assessmentGroups[0]);

                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedAssessmentGroup = candidateBackgroundApiTasks.getAssessmentGroup();
            }

            return returnedAssessmentGroup;
        }
    }


}
