package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Assessment_reportActivity extends AppCompatActivity {


    public static Candidate assessedCandidate;
    RecyclerView questionCategoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;
    LinearLayoutManager linearLayoutManager;
    QuestionApiBackgroundTasks questionApiBackgroundTasks;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;
    TextView candidateName;

    public static LinkedHashMap<Integer, Assessment> assessmentLinkedHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_report);

        showProgress = new showProgressbar(this);
        candidateName = (TextView)findViewById(R.id.assessed_candidate_name);

        assessedCandidate = ProfileActivity.candidate;
        candidateName.setText(assessedCandidate.getName());
        categoriesAdapter = new CategoriesAdapter(false,null,this,true);
        linearLayoutManager = new LinearLayoutManager(this);

        questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(this);
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        questionCategoriesRecyclerView = (RecyclerView)findViewById(R.id.questions_recycler_view);
        questionCategoriesRecyclerView.setLayoutManager(linearLayoutManager);
        questionCategoriesRecyclerView.setAdapter(categoriesAdapter);

        showProgress.setMessage("Loading questions");
        showProgress.show();

        new fetchAssessments().execute(assessedCandidate.getId());

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

    public class fetchAssessments extends AsyncTask<Integer,Void,ArrayList<Assessment>> {

        @Override
        protected void onPostExecute(ArrayList<Assessment> assessments) {

            if (assessments!=null){

                makeHashMapForAssessments(assessments);
                new fetchGroupQuestionCategories().execute(assessedCandidate.getGroup());

            }else {

                Toast.makeText(Assessment_reportActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
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

    public class fetchGroupQuestionCategories extends AsyncTask<Integer,Void, ArrayList<QuestionCategory>>{

        @Override
        protected void onPostExecute(ArrayList<QuestionCategory> questionCategories) {


            if (questionCategories!=null){

                categoriesAdapter.setQuestionCategoryArrayList(questionCategories);

            }else {

                Toast.makeText(Assessment_reportActivity.this, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
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
