package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupSearchActivity extends AppCompatActivity {


    Group group;
    public static String groupName;
    Intent intent;
    Spinner rankSpinner, statusSpinner;
    RankBackgroundApiTasks rankBackgroundApiTasks;
    showProgressbar showProgress;
    GroupsList groupsList;
    ArrayList<Rank> rankArrayList;
    Button search_btn;
    ProgressBar progressBar;
    GroupBackgroundApiTasks groupBackgroundApiTasks;
    public static GroupSearchActivity.searchObject activitySearchObject;
    final static int LIMIT = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);

        showProgress = new showProgressbar(this);
        intent = getIntent();
        group = intent.getParcelableExtra(GroupActivity.GROUP_TAG);

        rankBackgroundApiTasks = RankBackgroundApiTasks.getInstance(this);
        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(this);

        rankSpinner = (Spinner)findViewById(R.id.rank_spinner_search);
        statusSpinner = (Spinner)findViewById(R.id.status_spinner_search);
        search_btn = (Button)findViewById(R.id.search_btn);
        progressBar = (ProgressBar)findViewById(R.id.search_progress_bar);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rank rank = groupsList.findRankByName(rankSpinner.getSelectedItem().toString());
                String status = statusSpinner.getSelectedItem().toString();
                activitySearchObject = new searchObject(rank.getId(),group.getId(),0,LIMIT,status);
                initiateSearch(activitySearchObject);
            }
        });
        loadRanks();

    }

    private void initiateSearch(searchObject newSearch) {

        progressBar.setVisibility(View.VISIBLE);
        new searchGroup().execute(newSearch);

    }

    private void loadRanks() {

        showProgress.setMessage("Loading");
        showProgress.show();
        groupsList = new GroupsList(this);
        new getRanks().execute();

    }

    public void populateRanksSpinner(){

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,groupsList.getRanksSpinnerArray());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            rankSpinner.setAdapter(adapter);
            showProgress.dismiss();
    }


    public class getRanks extends AsyncTask<Void,Void, ArrayList<Rank>>{

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


    public class searchGroup extends AsyncTask<searchObject,Void,ArrayList<Candidate>>{

        @Override
        protected void onPostExecute(ArrayList<Candidate> candidateArrayList) {

            progressBar.setVisibility(View.GONE);
            if (candidateArrayList.size()>0 ){

            showResult(candidateArrayList);

            }else {
                Toast.makeText(GroupSearchActivity.this, groupBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected ArrayList<Candidate> doInBackground(searchObject... searchObjects) {
            ArrayList<Candidate> returnedArrayList;

            synchronized (groupBackgroundApiTasks){

                groupBackgroundApiTasks.SearchGroupCandidates(searchObjects[0]);
                try {
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedArrayList = groupBackgroundApiTasks.getGroupCandidatesList();
            }

            return returnedArrayList;
        }
    }

    private void showResult(ArrayList<Candidate> candidateArrayList) {

        Intent groupActivityIntent = new Intent(this,GroupActivity.class);
        groupActivityIntent.putExtra(GroupActivity.SEARCH_LIST,candidateArrayList);
        groupActivityIntent.putExtra(GroupActivity.GROUP_TAG, group);
        startActivity(groupActivityIntent);

    }


    public static class searchObject{

        Integer rank_id, group_id, last, limit;
        String status;


        public searchObject(Integer rank_id, Integer group_id, Integer last, Integer limit, String status) {
            this.rank_id = rank_id;
            this.group_id = group_id;
            this.last = last;
            this.limit = limit;
            this.status = status;
        }

        public Integer getRank_id() {
            return rank_id;
        }

        public void setRank_id(Integer rank_id) {
            this.rank_id = rank_id;
        }

        public Integer getGroup_id() {
            return group_id;
        }

        public void setGroup_id(Integer group_id) {
            this.group_id = group_id;
        }

        public Integer getLast() {
            return last;
        }

        public void setLast(Integer last) {
            this.last = last;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
