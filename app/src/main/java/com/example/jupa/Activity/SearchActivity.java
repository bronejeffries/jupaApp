package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.R;

import java.util.ArrayList;

import static com.example.jupa.Activity.GroupSearchActivity.LIMIT;

public class SearchActivity extends AppCompatActivity {

    Boolean byName, byCountry, byState, byCity, byRank, byGroup, byInstitution;

    public static String BY_NAME_EXTRA="by_name", BY_COUNTRY_EXTRA = "byCountry",
                         BY_STATE_EXTRA = "byState", BY_CITY_EXTRA="byCity",BY_RANK_EXTRA = "by_rank",
                        BY_GROUP_EXTRA = "by_group", BY_INSTITUTION_EXTRA="byInstitution";

    public GroupSearchActivity.searchObject activitySearchObject;
    GroupBackgroundApiTasks groupBackgroundApiTasks;

    Intent intent,searchResultIntent;
    LinearLayout name_layout, country_layout, state_layout, city_layout, rank_layout, group_layout, institution_layout;
    Button search_button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResultIntent = new Intent(this,SearchResultActivity.class);

        intent = getIntent();
        byName = intent.getBooleanExtra(BY_NAME_EXTRA,false);
        byCountry = intent.getBooleanExtra(BY_COUNTRY_EXTRA,false);
        byState = intent.getBooleanExtra(BY_STATE_EXTRA,false);
        byCity = intent.getBooleanExtra(BY_CITY_EXTRA,false);
//        byRank = intent.getBooleanExtra(BY_RANK_EXTRA,false);
        byGroup = intent.getBooleanExtra(BY_GROUP_EXTRA,false);
        byInstitution = intent.getBooleanExtra(BY_INSTITUTION_EXTRA,false);

        name_layout = (LinearLayout)findViewById(R.id.by_name_layout);
        country_layout = (LinearLayout)findViewById(R.id.by_country_layout);
        state_layout = (LinearLayout)findViewById(R.id.by_state_layout);
        city_layout = (LinearLayout)findViewById(R.id.by_city_layout);
        rank_layout = (LinearLayout)findViewById(R.id.by_rank_layout);
        group_layout = (LinearLayout)findViewById(R.id.by_group_layout);
        institution_layout = (LinearLayout)findViewById(R.id.by_institution_layout);

        search_button = (Button)findViewById(R.id.search_btn);
        progressBar = (ProgressBar)findViewById(R.id.search_progress_bar);

        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(this);

        setLayout();

    }

    public void setLayout(){

        if (byName){

            showLayout(name_layout);
            search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText name_edit_text = name_layout.findViewById(R.id.name_input);
                    searchForCandidatesByName(name_edit_text.getText().toString());
                }
            });

        }else if(byCountry){

            showLayout(country_layout);

        }else if(byState){

            showLayout(country_layout);
            showLayout(state_layout);

        }else if(byCity){

            showLayout(country_layout);
            showLayout(state_layout);
            showLayout(city_layout);

        }
//        else if(byRank){
//
//            showLayout(rank_layout);
//
//        }
        else if(byGroup){

            Intent group_search_intent = new Intent(this,GroupsActivity.class);
            group_search_intent.putExtra(InstitutionsActivity.SEARCH_PURPOSE,true);
            startActivity(group_search_intent);

        }else if(byInstitution){

            Intent institution_search_intent = new Intent(this,InstitutionsActivity.class);
            institution_search_intent.putExtra(InstitutionsActivity.SEARCH_PURPOSE,true);
            startActivity(institution_search_intent);

        }

    }

    private void searchForCandidatesByName(String name) {

        if (!name.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
            activitySearchObject = new GroupSearchActivity.searchObject(null,null,0,LIMIT,null);
            initiateSearch(activitySearchObject);
        }

    }

    private void initiateSearch(GroupSearchActivity.searchObject newSearch) {

        progressBar.setVisibility(View.VISIBLE);
        new nameSearch().execute(newSearch);

    }

    private void showResult(ArrayList<Candidate> candidateArrayList) {

        searchResultIntent.putExtra(GroupActivity.SEARCH_LIST,candidateArrayList);
        searchResultIntent.putExtra(GroupActivity.SEARCH_OBJECT_EXTRA,activitySearchObject);
        startActivity(searchResultIntent);

    }

    public class nameSearch extends AsyncTask<GroupSearchActivity.searchObject,Void, ArrayList<Candidate>> {

        @Override
        protected void onPostExecute(ArrayList<Candidate> candidateArrayList) {

            progressBar.setVisibility(View.GONE);

            if (candidateArrayList!=null&&candidateArrayList.size()>0){

                searchResultIntent.putExtra(SearchResultActivity.NAME_TYPE_SEARCH,true);
                showResult(candidateArrayList);

            }else {

                Toast.makeText(SearchActivity.this, groupBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected ArrayList<Candidate> doInBackground(GroupSearchActivity.searchObject... searchObjects) {
            ArrayList<Candidate> returnedArrayList;

            synchronized (groupBackgroundApiTasks){

                groupBackgroundApiTasks.GetCandidatesByName(searchObjects[0]);
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


    public void showLayout(LinearLayout linearLayout){

        linearLayout.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart", "onRestart: "+intent.getBooleanExtra(InstitutionsActivity.SEARCH_PURPOSE,false));

        if(intent.getBooleanExtra(BY_GROUP_EXTRA,false)|| intent.getBooleanExtra(BY_INSTITUTION_EXTRA,false)){
            finish();
        }
    }


}
