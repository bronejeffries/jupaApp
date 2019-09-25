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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Question.Adapter.Categories.CategoriesAdapter;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;

import java.util.ArrayList;

public class AssessementActivity extends AppCompatActivity {


    Candidate assessedCandidate;
    CandidateProject candidateProject;
    Intent intent;
    public final static String ASSESSMENT_GROUP_EXTRA = "assessment_group";
    public static AssessmentGroup assessmentGroup;
    TextView assessment_group_name, assessed_project;
    RecyclerView questionCategoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;
    LinearLayoutManager linearLayoutManager;
    QuestionApiBackgroundTasks questionApiBackgroundTasks;
    showProgressbar showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessement);

        showProgress = new showProgressbar(this);

        intent = getIntent();
        assessmentGroup = intent.getParcelableExtra(ASSESSMENT_GROUP_EXTRA);
        candidateProject = ProjectAssessmentsActivity.candidateProject;

        questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(this);

        assessedCandidate = ProfileActivity.candidate;

        categoriesAdapter = new CategoriesAdapter(true,null,this);
        linearLayoutManager = new LinearLayoutManager(this);

        questionCategoriesRecyclerView = (RecyclerView)findViewById(R.id.questions_recycler_view);
        questionCategoriesRecyclerView.setLayoutManager(linearLayoutManager);
        questionCategoriesRecyclerView.setAdapter(categoriesAdapter);

        assessment_group_name = (TextView)findViewById(R.id.assessment_name);
        assessed_project = (TextView)findViewById(R.id.assessed_project_name);

        assessment_group_name.setText(assessmentGroup.getName());
        assessed_project.setText(candidateProject.getTitle());

        showProgress.setMessage("Loading questions");
        showProgress.show();
        new fetchGroupQuestionCategories().execute(assessedCandidate.getGroup());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_activity_menu,menu);
        return true;
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

    }

    public class fetchGroupQuestionCategories extends AsyncTask<Integer,Void, ArrayList<QuestionCategory>>{

        @Override
        protected void onPostExecute(ArrayList<QuestionCategory> questionCategories) {


            if (questionCategories!=null){
                categoriesAdapter.setQuestionCategoryArrayList(questionCategories);
            }else {

                Toast.makeText(AssessementActivity.this, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

            showProgress.dismiss();

        }

        @Override
        protected ArrayList<QuestionCategory> doInBackground(Integer... integers) {
            ArrayList<QuestionCategory> returnedquestionCategoryArrayList;

            synchronized (questionApiBackgroundTasks){

                questionApiBackgroundTasks.getGroupQuestionCategories(integers[0]);
                try {
                    questionApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedquestionCategoryArrayList = questionApiBackgroundTasks.getQuestionCategoryArrayList();

            }

            return returnedquestionCategoryArrayList;
        }

    }


}
