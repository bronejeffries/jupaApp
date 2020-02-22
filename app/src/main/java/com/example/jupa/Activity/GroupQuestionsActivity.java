package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Question.Adapter.Categories.CategoriesAdapter;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;

import java.util.ArrayList;

public class GroupQuestionsActivity extends AppCompatActivity {

    public static final String GROUP_EXTRA ="group" ;
    QuestionApiBackgroundTasks questionApiBackgroundTasks;
    Intent incomingIntent;
    showProgressbar showProgress;
    CategoriesAdapter categoriesAdapter;
    RecyclerView questionCategoriesRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Integer group_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_questions);

        incomingIntent = getIntent();

        group_ID = incomingIntent.getIntExtra(GROUP_EXTRA,0);

        showProgress = new showProgressbar(this);

        questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(this);

        categoriesAdapter = new CategoriesAdapter(false,null,this,false);

        categoriesAdapter.setQuestionsView(true);

        linearLayoutManager = new LinearLayoutManager(this);

        questionCategoriesRecyclerView = (RecyclerView)findViewById(R.id.questions_view_recycler_view);

        questionCategoriesRecyclerView.setLayoutManager(linearLayoutManager);

        questionCategoriesRecyclerView.setAdapter(categoriesAdapter);

        showProgress.setMessage("Loading questions");
        showProgress.show();

        new fetchGroupQuestionCategories().execute(group_ID);

        
    }

    public class fetchGroupQuestionCategories extends AsyncTask<Integer,Void, ArrayList<QuestionCategory>> {

        @Override
        protected void onPostExecute(ArrayList<QuestionCategory> questionCategories) {


            if (questionCategories!=null){

                categoriesAdapter.setQuestionCategoryArrayList(questionCategories);

            }

            Toast.makeText(GroupQuestionsActivity.this, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void AddNewQuestionCategory(View view) {

        AlertDialog.Builder addNewCategoryDialog = new AlertDialog.Builder(this);
        LinearLayout addCategoryLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.addquestioncategory,null,false);
        final EditText category = (EditText)addCategoryLinearLayout.findViewById(R.id.category_input);
        Button save_btn = (Button) addCategoryLinearLayout.findViewById(R.id.save_category_btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProgress.setMessage("Adding category ...");
                showProgress.show();
                QuestionCategory questionCategory = new QuestionCategory(category.getText().toString(),group_ID);
                new addQuestionCategory().execute(questionCategory);

            }
        });

        addNewCategoryDialog.setView(addCategoryLinearLayout);
        addNewCategoryDialog.setTitle("Add New Question Category");
        addNewCategoryDialog.create().show();

    }


    public class addQuestionCategory extends AsyncTask<QuestionCategory,Void, QuestionCategory>{

        @Override
        protected void onPostExecute(QuestionCategory questionCategory) {

            if (questionCategory!=null){

                categoriesAdapter.addItem(questionCategory);

            }

            Toast.makeText(GroupQuestionsActivity.this, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
            showProgress.dismiss();

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

}
