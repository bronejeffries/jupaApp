package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jupa.Assessment.Adapter.AssessmentAdapter;
import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

import java.util.ArrayList;

public class InstitutionResultsActivity extends AppCompatActivity {

    public static String Institution_Extra = "institution";
    public static ArrayList<Assessment> assessmentArrayList;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;
    RecyclerView resultsRecyclerView;
    Institution institution;
    Intent intent;
    public static AssessmentAdapter assessmentAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_results);

        intent = getIntent();

        resultsRecyclerView = (RecyclerView)findViewById(R.id.results_assessments);
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);
        showProgress = new showProgressbar(this);

        institution = intent.getParcelableExtra(Institution_Extra);

        assessmentArrayList = new ArrayList<>();
        assessmentAdapter = new AssessmentAdapter(assessmentArrayList,this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        resultsRecyclerView.setLayoutManager(linearLayoutManager);
        resultsRecyclerView.setAdapter(assessmentAdapter);

        showProgress.setMessage("loading");
        showProgress.show();

        new fetchAssessments().execute(institution.getInstitution_id());

    }

    public void runSearchActivity(){

        Intent intent = new Intent(this, ResultSearchActivity.class);
        startActivity(intent);

    }

    public class fetchAssessments extends AsyncTask<Integer,Void,ArrayList<Assessment>> {

        @Override
        protected void onPostExecute(ArrayList<Assessment> assessments) {

            if (assessments!=null){

                assessmentArrayList = assessments;
                assessmentAdapter.setAssessmentArrayList(assessmentArrayList);

            }else {

                Toast.makeText(InstitutionResultsActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }

            showProgress.dismiss();

        }

        @Override
        protected ArrayList<Assessment> doInBackground(Integer... integers) {

            ArrayList<Assessment> returnedAssessmentArrayList;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.getInstitutionAssessments(integers[0]);
                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedAssessmentArrayList = candidateBackgroundApiTasks.getAssessmentArrayList();
            }

            return returnedAssessmentArrayList;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.search_btn:
                runSearchActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
