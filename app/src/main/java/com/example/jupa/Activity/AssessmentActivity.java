package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Question.Adapter.Categories.CategoriesAdapter;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AssessmentActivity extends AppCompatActivity {


    public static Candidate assessedCandidate;
    Intent intent;
    TextView assessment_group_name, assessed_project;
    RecyclerView questionCategoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;
    LinearLayoutManager linearLayoutManager;
    QuestionApiBackgroundTasks questionApiBackgroundTasks;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;

    showProgressbar showProgress;
    public static LinkedHashMap<Integer, Assessment> assessmentLinkedHashMap;
    public static HashMap<String,Integer> gradesMap;
    public static ArrayList<Assessment> archivedAssessment = new ArrayList<>();
    public static String[] grade_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessement);

        showProgress = new showProgressbar(this);

        makeGrades();

        intent = getIntent();

        questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(this);
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        assessedCandidate = intent.getParcelableExtra(RankRequestActivity.CANDIDATE_EXTRA);

        categoriesAdapter = new CategoriesAdapter(true,null,this,false);
        linearLayoutManager = new LinearLayoutManager(this);

        questionCategoriesRecyclerView = (RecyclerView)findViewById(R.id.questions_recycler_view);
        questionCategoriesRecyclerView.setLayoutManager(linearLayoutManager);
        questionCategoriesRecyclerView.setAdapter(categoriesAdapter);

        assessment_group_name = (TextView)findViewById(R.id.assessment_name);
//        assessed_project = (TextView)findViewById(R.id.assessed_project_name);

        assessment_group_name.setText(assessedCandidate.getName());
//        assessed_project.setText(candidateProject.getTitle());

        showProgress.setMessage("Loading questions");
        showProgress.show();

        new fetchAssessments().execute(assessedCandidate.getId());

    }

    private void makeGrades() {

        grade_array = getResources().getStringArray(R.array.grades_array_values);
        gradesMap = new HashMap<>();

        for (int i = 0; i< grade_array.length; i++){

            gradesMap.put(grade_array[i],i);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean create;

        if (UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ASSESSOR_ROLE)){

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.assessment_activity_menu,menu);
            create = true;

        }else{

           create = super.onCreateOptionsMenu(menu);

        }

        return create;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.assessment_submit:
                submitAssessments();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void submitAssessments() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Assessments Save");
        alert.setMessage("Only Archived Assessments will be saved");
        alert.setPositiveButton(getResources().getString(R.string.add_group), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgress.setMessage("Saving..");
                showProgress.show();
                new makeAssessments().execute(archivedAssessment);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    private void makeHashMapForAssessments(ArrayList<Assessment> assessments){

        assessmentLinkedHashMap = new LinkedHashMap<>();

        for (Assessment assessment : assessments) {

            if (assessmentLinkedHashMap.containsKey(assessment.getQuestion_id())){

            }else{
                assessmentLinkedHashMap.put(assessment.getQuestion_id(),assessment);
            }

        }

    }


    public class fetchAssessments extends AsyncTask<Integer,Void,ArrayList<Assessment>>{

        @Override
        protected void onPostExecute(ArrayList<Assessment> assessments) {

            if (assessments!=null){

                makeHashMapForAssessments(assessments);
                new fetchGroupQuestionCategories().execute(assessedCandidate.getGroup());

            }else {

                Toast.makeText(AssessmentActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress.dismiss();

            }

        }

        @Override
        protected ArrayList<Assessment> doInBackground(Integer... integers) {

            ArrayList<Assessment> returnedAssessmentArrayList;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.getCandidateAssessment(integers[0]);
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

    public class makeAssessments extends  AsyncTask<ArrayList<Assessment>,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {

            showProgress.dismiss();
            archivedAssessment.clear();
            Toast.makeText(AssessmentActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            onBackPressed();

        }

        @Override
        protected Void doInBackground(ArrayList<Assessment>... arrayLists) {

            for (Assessment assessment:arrayLists[0]){

                synchronized (candidateBackgroundApiTasks){

                    candidateBackgroundApiTasks.addAssessment(assessment);
                    try {
                        candidateBackgroundApiTasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            return null;
        }

    }

    public class fetchGroupQuestionCategories extends AsyncTask<Integer,Void, ArrayList<QuestionCategory>>{

        @Override
        protected void onPostExecute(ArrayList<QuestionCategory> questionCategories) {


            if (questionCategories!=null){

                categoriesAdapter.setQuestionCategoryArrayList(questionCategories);

            }else {

                Toast.makeText(AssessmentActivity.this, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

            showProgress.dismiss();

        }

        @Override
        protected ArrayList<QuestionCategory> doInBackground(Integer... integers) {

            ArrayList<QuestionCategory> returnedQuestionCategoryArrayList;

            synchronized (questionApiBackgroundTasks){

                questionApiBackgroundTasks.getGroupQuestionCategories(integers[0]);
                try {
                    questionApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedQuestionCategoryArrayList = questionApiBackgroundTasks.getQuestionCategoryArrayList();

            }

            return returnedQuestionCategoryArrayList;
        }

    }


}
