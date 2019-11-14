package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;
import com.example.jupa.Request.Adapter.RequestApplicationAdapter;
import com.example.jupa.Request.Api.RequestApiBackgroundTasks;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

public class MyRequestsActivity extends AppCompatActivity {

    showProgressbar showProgress;
    RequestApiBackgroundTasks requestApiBackgroundTasks;
    Button file_request,assessor_choice, rank_choice;
    RecyclerView requestsRecyclerView;
    LinearLayoutManager linearLayoutManager;
    public static RequestApplicationAdapter requestApplicationAdapter;
    private AlertDialog requestalertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        showProgress = new showProgressbar(this);
        requestApiBackgroundTasks = RequestApiBackgroundTasks.getInstance(this);

        file_request = (Button)findViewById(R.id.file_new_request);
        file_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestDialog();
            }
        });

        requestsRecyclerView = (RecyclerView)findViewById(R.id.requests_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        requestsRecyclerView.setLayoutManager(linearLayoutManager);

        requestApplicationAdapter = new RequestApplicationAdapter(this,null,RequestApplicationAdapter.PERSONAL_VIEW);
        requestsRecyclerView.setAdapter(requestApplicationAdapter);

        loadApplications();

    }

    private void loadApplications() {

        showProgress.setMessage("Loading...");
        showProgress.show();

        new fetchCandidateApplications().execute(UserHomeActivity.thisCandidate.getId());


    }

    private void showRequestDialog() {

        AlertDialog.Builder requestTypeDialog = new AlertDialog.Builder(this);
        CardView cardViewDialog = (CardView) LayoutInflater.from(this).inflate(R.layout.requests_dialog,null,false);
//        assessor_choice = (Button)cardViewDialog.findViewById(R.id.assessor_request_btn);
        rank_choice = (Button)cardViewDialog.findViewById(R.id.rank_request_btn);

//            assessor_choice.setEnabled(true);
//            assessor_choice.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    requestalertDialog.dismiss();
//                    showAssessorRequestActivity();
//                }
//            });

            rank_choice.setEnabled(true);
            rank_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestalertDialog.dismiss();
                    showRankRequestActivity();

                }
            });

        requestTypeDialog.setView(cardViewDialog);
        requestalertDialog = requestTypeDialog.create();
        requestalertDialog.show();

    }

    private void showAssessorRequestActivity() {

        Intent assessorRequestIntent = new Intent(this,AssessorRequestActivity.class);
        startActivity(assessorRequestIntent);

    }

    private void showRankRequestActivity() {

        Intent rankRequestIntent = new Intent(this,RankRequestActivity.class);
        startActivity(rankRequestIntent);

    }

    private class  fetchCandidateApplications extends AsyncTask<Integer,Void, ArrayList<RequestApplicationObject>>{

        @Override
        protected void onPostExecute(ArrayList<RequestApplicationObject> requestApplicationObjects) {

            if (requestApplicationObjects!=null){

                requestApplicationAdapter.setRequestApplicationObjectArrayList(requestApplicationObjects);

            }else {

                Toast.makeText(MyRequestsActivity.this, requestApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

            showProgress.dismiss();

        }

        @Override
        protected ArrayList<RequestApplicationObject> doInBackground(Integer... integers) {

            ArrayList<RequestApplicationObject> requestApplicationObjects;

            synchronized (requestApiBackgroundTasks){

                requestApiBackgroundTasks.getCandidateApplications(integers[0]);

                try {

                    requestApiBackgroundTasks.wait();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                requestApplicationObjects = requestApiBackgroundTasks.getRequestApplicationObjectArrayList();

            }

            return requestApplicationObjects;

        }

    }

}
