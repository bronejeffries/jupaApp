package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CandidatesDisplayActivity extends AppCompatActivity {

    public static final String ASSESSOR_EXTRA ="assessor";
    Candidate Assessor;
    Intent intent;
    RecyclerView candidatesRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CandidatesAdapter candidatesAdapter;
    ArrayList<Candidate> candidateArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_display);

        intent = getIntent();
        Assessor = intent.getParcelableExtra(ASSESSOR_EXTRA);
        candidatesRecyclerView = (RecyclerView)findViewById(R.id.candidates_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        candidateArrayList = RegisteredCandidates.getInstance().filterListByAssesor(Assessor);
        candidatesAdapter = new CandidatesAdapter(this,candidateArrayList);

        candidatesRecyclerView.setLayoutManager(linearLayoutManager);
        candidatesRecyclerView.setAdapter(candidatesAdapter);

    }
}
