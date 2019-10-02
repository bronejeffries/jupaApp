package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.GroupsList;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;
import com.example.jupa.Rank.Api.RankBackgroundApiTasks;
import com.example.jupa.Rank.Rank;

import java.util.ArrayList;

public class RankRequestActivity extends AppCompatActivity {

    showProgressbar showProgress;
    RankBackgroundApiTasks rankBackgroundApiTasks;
    GroupsList groupsList;
    ArrayList<Rank> rankArrayList;
    Candidate ApplyingCandidate;

    String regNoText, experience_text, qualificationText, reasonText;
    Rank rank;

    EditText regNoInput, years_of_experience, present_qualification, reason;
    Spinner rank_appliedFor;
    Button submitRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_request);

        showProgress = new showProgressbar(this);
        rankBackgroundApiTasks = RankBackgroundApiTasks.getInstance(this);

        ApplyingCandidate = LoggedInUser.getInstance().getLoggedInCandidate();

        regNoInput = (EditText)findViewById(R.id.regno__input);
        regNoInput.setText(ApplyingCandidate.getRegistration_no());

        years_of_experience = (EditText)findViewById(R.id.experience_input);
        present_qualification = (EditText)findViewById(R.id.qualifications_input);
        reason = (EditText)findViewById(R.id.reason_for_application_input);
        rank_appliedFor = (Spinner)findViewById(R.id.rank_applied_input);
        submitRequest = (Button)findViewById(R.id.request_submission_btn);


        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rank = groupsList.findRankByName(rank_appliedFor.getSelectedItem().toString());
                regNoText = regNoInput.getText().toString();
                experience_text = years_of_experience.getText().toString();
                qualificationText = present_qualification.getText().toString();
                reasonText = reason.getText().toString();

            }
        });

        loadRanks();

    }


    private void loadRanks() {

        showProgress.setMessage("loading Information");
        showProgress.show();
        groupsList = new GroupsList(this);
        new getRanks().execute();

    }


    public void populateRanksSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,groupsList.getRanksSpinnerArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rank_appliedFor.setAdapter(adapter);
        showProgress.dismiss();
    }


    public class getRanks extends AsyncTask<Void,Void, ArrayList<Rank>> {

        @Override
        protected void onPostExecute(ArrayList<Rank> ranks) {

            rankArrayList = ranks;
            groupsList.makeRankListFrom(rankArrayList);
            populateRanksSpinner();

        }

        @Override
        protected ArrayList<Rank> doInBackground(Void... voids) {

            ArrayList<Rank> returnedArrayList;
            synchronized (rankBackgroundApiTasks){

                rankBackgroundApiTasks.getAllRanks();
                try {
                    rankBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedArrayList = rankBackgroundApiTasks.getRankArrayList();

            }

            return returnedArrayList;
        }
    }


}
