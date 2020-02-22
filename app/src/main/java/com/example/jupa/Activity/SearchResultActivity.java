package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jupa.Candidate.Adapters.CandidatesAdapter;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.R;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    Boolean name_type_search,scrollDone,reloading;
    Intent intent;

    public static final String NAME_TYPE_SEARCH="name_type";

    GroupBackgroundApiTasks groupBackgroundApiTasks;
    private GroupSearchActivity.searchObject searchObject;
    RecyclerView masonry_recycler_view;
    ArrayList<Candidate> GroupCandidates;
    private CandidatesAdapter masonCandidatesAdapter;
    ProgressBar progressBar;
    int last;
    Integer waitTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        intent = getIntent();
        name_type_search = intent.getBooleanExtra(NAME_TYPE_SEARCH,false);

        progressBar = (ProgressBar)findViewById(R.id.search_progress_bar);

        Intent intent = getIntent();
        GroupCandidates = intent.getParcelableArrayListExtra(GroupActivity.SEARCH_LIST);
        masonry_recycler_view = (RecyclerView)findViewById(R.id.result_recycler_view);

        masonry_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (scrollDone && !getReloading()){

                    if (waitTimes.equals(0)){

                        reload();

                    }else {

                        waitTimes -= 1;
                    }

                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(-1)){

                    if (!getReloading()){

                        scrollDone = true;

                        waitTimes = 4;

                    }

                }else {

                    scrollDone = false;

                }

            }

        });

        searchObject = intent.getParcelableExtra(GroupActivity.SEARCH_OBJECT_EXTRA);
        last = searchObject.getLimit();

        populateMasonCandidates(GroupCandidates);

    }

    private void populateMasonCandidates(ArrayList<Candidate> masons) {

        masonCandidatesAdapter = new CandidatesAdapter(this,masons);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setReverseLayout(true);
        masonry_recycler_view.setLayoutManager(linearLayout);
        masonry_recycler_view.setAdapter(masonCandidatesAdapter);
    }


    public void reload(){

        setReloading(true);
        searchObject.setLast(last);
        progressBar.setVisibility(View.VISIBLE);
        new getCandidates().execute(searchObject);

    }

    public void stopReload(){

        setReloading(false);
        progressBar.setVisibility(View.VISIBLE);

    }

    public Boolean getReloading() {
        return reloading;
    }

    public void setReloading(Boolean reloading) {
        this.reloading = reloading;
    }

    public class getCandidates extends AsyncTask<GroupSearchActivity.searchObject,Void, ArrayList<Candidate>> {

        @Override
        protected void onPostExecute(ArrayList<Candidate> candidateArrayList) {


            if (candidateArrayList!=null){

                last = last+searchObject.getLimit()+1;

                if (candidateArrayList.size()>0){

                    masonCandidatesAdapter.updateArrayList(candidateArrayList);
                    masonry_recycler_view.scrollToPosition((searchObject.getLast()-1));

                }

            }else {

                Toast.makeText(SearchResultActivity.this, groupBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }
            stopReload();
        }

        @Override
        protected ArrayList<Candidate> doInBackground(GroupSearchActivity.searchObject... searchObjects) {

            ArrayList<Candidate> returnedArraylist;

            synchronized (groupBackgroundApiTasks){

                try {

                    Log.e("do in background", "doInBackground: running this");
                    if(name_type_search){
                        groupBackgroundApiTasks.GetCandidatesByName(searchObjects[0]);
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
