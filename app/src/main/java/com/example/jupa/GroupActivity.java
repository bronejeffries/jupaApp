package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    public static final String GROUP_TAG = "group", SEARCH_LIST ="candidates";
    Group group;
    Rank rank;
    public static String groupName;
    final static String MASON_CATEGORY = "mason", PROFESSIONAL_CATEGORY = "professional";
    RecyclerView masonry_recycler_view;
    ImageButton Load_more;

    CardView masonry_card;

    GroupBackgroundApiTasks groupBackgroundApiTasks;
    ArrayList<Candidate> GroupCandidates;
    private CandidatesAdapter masonCandidatesAdapter;
    showProgressbar showprogress;
    ProgressBar progressBar;
    private GroupSearchActivity.searchObject searchObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        showprogress = new showProgressbar(this);
        progressBar = (ProgressBar)findViewById(R.id.search_progress_bar);

        Intent intent = getIntent();
        group = (Group)intent.getParcelableExtra(GROUP_TAG);
        GroupCandidates = intent.getParcelableArrayListExtra(SEARCH_LIST);
        rank = intent.getParcelableExtra(ProfileActivity.RANK_EXTRA);
        groupName = group.getGroup_name();

        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(GroupActivity.this);

        masonry_recycler_view = (RecyclerView)findViewById(R.id.masonry_recycler_view);

        Load_more = (ImageButton) findViewById(R.id.masonry);
        masonry_card = (CardView)findViewById(R.id.mansory_card_view);

        populateMasonCandidates(GroupCandidates);

        Load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        fetchGroupCandidates();

            }
        });

    }


    private void fetchGroupCandidates() {

        progressBar.setVisibility(View.VISIBLE);
        int last = masonCandidatesAdapter.getLastItemId();
        searchObject = GroupSearchActivity.activitySearchObject;
        searchObject.setLast(last);
        new getCandidates().execute(searchObject);

    }



    private void populateMasonCandidates(ArrayList<Candidate> masons) {
        masonCandidatesAdapter = new CandidatesAdapter(this,masons);
        masonCandidatesAdapter.setRank(rank);
        masonCandidatesAdapter.setGroup(group);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setReverseLayout(true);
        masonry_recycler_view.setLayoutManager(linearLayout);
        masonry_recycler_view.setAdapter(masonCandidatesAdapter);
    }


//    public static ArrayList<Candidate> filterByCategory(ArrayList<Candidate> candidateArrayList , String categoryName){
//
//        ArrayList<Candidate> filteredList = new ArrayList<>();
//
//        for (Candidate candidate : candidateArrayList ) {
//
//            if (candidate.getCategory() != null ){
//
//                if (candidate.getCategory().equals(categoryName)){
//
//                    filteredList.add(candidate);
//
//                }
//
//            }
//
//        }
//
//        return filteredList;
//    }

    public class getCandidates extends AsyncTask<GroupSearchActivity.searchObject,Void, ArrayList<Candidate>>{

        @Override
        protected void onPostExecute(ArrayList<Candidate> candidateArrayList) {

            if (candidateArrayList.size()>0){
                masonCandidatesAdapter.updateArrayList(candidateArrayList);
                masonry_recycler_view.scrollToPosition((searchObject.last-1));
            }else {

                Toast.makeText(GroupActivity.this, groupBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }
            progressBar.setVisibility(View.GONE);

        }

        @Override
        protected ArrayList<Candidate> doInBackground(GroupSearchActivity.searchObject... searchObjects) {

            ArrayList<Candidate> returnedArraylist;

            synchronized (groupBackgroundApiTasks){

                try {
                    groupBackgroundApiTasks.SearchGroupCandidates(searchObjects[0]);
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedArraylist = groupBackgroundApiTasks.getGroupCandidatesList();
            }

            return returnedArraylist;
        }
    }


}
