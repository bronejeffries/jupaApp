package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jupa.Assessment.Adapter.AssessmentAdapter;
import com.example.jupa.Assessment.Assessment;
import com.example.jupa.R;

import java.util.ArrayList;

public class ResultSearchActivity extends AppCompatActivity {

    EditText editText;
    RecyclerView recyclerView;
    AssessmentAdapter assessmentAdapter;
    LinearLayoutManager linearLayoutManager;
    public static ArrayList<Assessment> assessmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        editText = (EditText)findViewById(R.id.search_area);
        recyclerView = (RecyclerView)findViewById(R.id.filtered_display);
        assessmentArrayList = new ArrayList<>();
        assessmentArrayList.addAll(InstitutionResultsActivity.assessmentArrayList);
        assessmentAdapter = new AssessmentAdapter(assessmentArrayList,this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(assessmentAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                showSearchResults(editable.toString());

            }

        });

    }

    public void showSearchResults(String searchString){

        ArrayList<Assessment> returned_assessments = new ArrayList<>();

        for (int i = 0; i< assessmentArrayList.size();i++){

            Assessment checkedAssessment = assessmentArrayList.get(i);
            if (checkedAssessment.getOther_remarks().contains(searchString)
               || checkedAssessment.getCandidate().getRegistration_no().contains(searchString)
               || checkedAssessment.getGrade().contains(searchString)
               || checkedAssessment.getQuestion().getTitle().contains(searchString)){

                checkedAssessment.setObject_position(i);
                returned_assessments.add(checkedAssessment);

            }

        }

        assessmentAdapter.setAssessmentArrayList(returned_assessments);

    }





}
