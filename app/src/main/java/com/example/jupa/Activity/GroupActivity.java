package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Adapters.CandidatesAdapter;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;
import com.example.jupa.Rank.Rank;
import com.example.jupa.Helpers.showProgressbar;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    public static final String GROUP_TAG = "group", SEARCH_LIST ="candidates"
            ,ASSESSOR="assessor",CANDIDATES_VIEW="candidate_view",INSTITUTION="institution"
            ,INSTITUTION_CANDIDATE_ASSESSOR_VIEW = "no_group_institution_view"
            ,MEMBER_LEVEL="member_level",SEARCH_OBJECT_EXTRA="search object";

    Group group;
    Candidate assessor;
    Institution institution;
    Boolean assessor_candidates_view,institution_candidate_assessor_view;
    Rank rank;
    public static String groupName;
    String member_level;
    RecyclerView masonry_recycler_view;
    ImageButton Load_more;

    CardView masonry_card;

    GroupBackgroundApiTasks groupBackgroundApiTasks;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    ArrayList<Candidate> GroupCandidates;
    private CandidatesAdapter masonCandidatesAdapter;
    showProgressbar showprogress;
    ProgressBar progressBar;
    int last;
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

        assessor_candidates_view = intent.getBooleanExtra(CANDIDATES_VIEW,false);
        institution_candidate_assessor_view = intent.getBooleanExtra(INSTITUTION_CANDIDATE_ASSESSOR_VIEW,false);

        groupName = group!=null?group.getGroup_name():"";
        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(this);


        masonry_recycler_view = (RecyclerView)findViewById(R.id.masonry_recycler_view);
        Load_more = (ImageButton) findViewById(R.id.masonry);
        masonry_card = (CardView)findViewById(R.id.mansory_card_view);

        populateMasonCandidates(GroupCandidates);

        if (GroupsActivity.institution_view && !assessor_candidates_view && !institution_candidate_assessor_view ){

            searchObject = new GroupSearchActivity.searchObject(null,group.getId(),0,GroupSearchActivity.LIMIT,null);
            last = searchObject.getLast();
            searchObject.setMember_level(GroupsActivity.viewPurpose);
            searchObject.setInstitution_id(GroupsActivity.institution.getInstitution_id());
            fetchCandidates();

        }else if (assessor_candidates_view){

            assessor = intent.getParcelableExtra(ASSESSOR);

            institution = intent.getParcelableExtra(INSTITUTION);

            candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);
            searchObject = new GroupSearchActivity.searchObject(null,null,0,GroupSearchActivity.LIMIT,null);
            last = searchObject.getLast();
            searchObject.setInstitution_id(institution!=null?institution.getInstitution_id():null);
            searchObject.setAssessor_id(assessor.getId());
            fetchCandidates();


        }else if (institution_candidate_assessor_view){

            institution = intent.getParcelableExtra(INSTITUTION);
            member_level = intent.getStringExtra(MEMBER_LEVEL);
            searchObject = new GroupSearchActivity.searchObject(null,null,0,GroupSearchActivity.LIMIT,null);
            searchObject.setMember_level(member_level);
            searchObject.setInstitution_id(institution.getInstitution_id());
            last = searchObject.getLast();
            fetchCandidates();

        }else {

            searchObject = intent.getParcelableExtra(SEARCH_OBJECT_EXTRA);
            last = searchObject.getLimit();

        }

        Load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchObject.setLast(last);
                fetchCandidates();

            }
        });

    }



    private void fetchCandidates() {

        progressBar.setVisibility(View.VISIBLE);
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


    public class getCandidates extends AsyncTask<GroupSearchActivity.searchObject,Void, ArrayList<Candidate>>{

        @Override
        protected void onPostExecute(ArrayList<Candidate> candidateArrayList) {

            if (candidateArrayList!=null){

                last = last+searchObject.getLimit()+1;

                if (candidateArrayList.size()>0){

                    masonCandidatesAdapter.updateArrayList(candidateArrayList);
                    masonry_recycler_view.scrollToPosition((searchObject.getLast()-1));

                }

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
                    if (GroupsActivity.institution_view && !assessor_candidates_view && !institution_candidate_assessor_view){

                        groupBackgroundApiTasks.SearchGroupInstituionCandidates(searchObjects[0]);

                    }else if(assessor_candidates_view){

                        groupBackgroundApiTasks.fetchAssessorCandidates(searchObjects[0]);

                    }else if (institution_candidate_assessor_view){

                        groupBackgroundApiTasks.SearchInstituionCandidatesByLevel(searchObjects[0]);

                    }else {
                        Log.e("do in background", "doInBackground: running this");
                        groupBackgroundApiTasks.SearchGroupCandidates(searchObjects[0]);

                    }
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
