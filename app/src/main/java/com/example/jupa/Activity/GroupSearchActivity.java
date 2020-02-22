package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Helpers.GroupsList;
import com.example.jupa.R;
import com.example.jupa.Rank.Api.RankBackgroundApiTasks;
import com.example.jupa.Rank.Rank;
import com.example.jupa.Helpers.showProgressbar;

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
    public GroupSearchActivity.searchObject activitySearchObject;
    final static int LIMIT = 20;
    private Rank rank;


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
                rank = groupsList.findRankByName(rankSpinner.getSelectedItem().toString());
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
        groupActivityIntent.putExtra(ProfileActivity.RANK_EXTRA,rank);
        groupActivityIntent.putExtra(GroupActivity.SEARCH_OBJECT_EXTRA,activitySearchObject);
        startActivity(groupActivityIntent);

    }

    public static class searchObject implements Parcelable {

        Integer rank_id, group_id, last, limit,institution_id,assessor_id;
        String status,member_level,requestType,name;


        public searchObject(Integer rank_id, Integer group_id, Integer last, Integer limit, String status) {
            this.rank_id = rank_id;
            this.group_id = group_id;
            this.last = last;
            this.limit = limit;
            this.status = status;
        }

        protected searchObject(Parcel in) {
            if (in.readByte() == 0) {
                rank_id = null;
            } else {
                rank_id = in.readInt();
            }
            if (in.readByte() == 0) {
                group_id = null;
            } else {
                group_id = in.readInt();
            }
            if (in.readByte() == 0) {
                last = null;
            } else {
                last = in.readInt();
            }
            if (in.readByte() == 0) {
                limit = null;
            } else {
                limit = in.readInt();
            }
            if (in.readByte() == 0) {
                institution_id = null;
            } else {
                institution_id = in.readInt();
            }
            if (in.readByte() == 0) {
                assessor_id = null;
            } else {
                assessor_id = in.readInt();
            }
            status = in.readString();
            member_level = in.readString();
            requestType = in.readString();
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (rank_id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(rank_id);
            }
            if (group_id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(group_id);
            }
            if (last == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(last);
            }
            if (limit == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(limit);
            }
            if (institution_id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(institution_id);
            }
            if (assessor_id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(assessor_id);
            }
            dest.writeString(status);
            dest.writeString(member_level);
            dest.writeString(requestType);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<searchObject> CREATOR = new Creator<searchObject>() {
            @Override
            public searchObject createFromParcel(Parcel in) {
                return new searchObject(in);
            }

            @Override
            public searchObject[] newArray(int size) {
                return new searchObject[size];
            }
        };

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }

        public Integer getAssessor_id() {
            return assessor_id;
        }

        public void setAssessor_id(Integer assessor_id) {
            this.assessor_id = assessor_id;
        }

        public Integer getInstitution_id() {
            return institution_id;
        }

        public void setInstitution_id(Integer institution_id) {
            this.institution_id = institution_id;
        }

        public String getMember_level() {
            return member_level;
        }

        public void setMember_level(String member_level) {
            this.member_level = member_level;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}

