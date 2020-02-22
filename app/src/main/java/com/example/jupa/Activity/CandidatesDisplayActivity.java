package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Adapters.CandidatesAdapter;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.R;

import java.util.ArrayList;

public class CandidatesDisplayActivity extends AppCompatActivity {

    EditText searchArea;
    TextView search_warning;
    ProgressBar progressBar;
    Button searchButton;
    RecyclerView recyclerView;
    CandidatesAdapter candidatesAdapter;
    ArrayList<Candidate> candidateArrayList;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    Boolean warningCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_display);

        searchArea = (EditText)findViewById(R.id.search_candidate);
        recyclerView = (RecyclerView)findViewById(R.id.candidate_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar)findViewById(R.id.search_progress_bar);
        searchButton = (Button) findViewById(R.id.search_btn);
        search_warning = (TextView)findViewById(R.id.search_warning);

        warningCheck = LoggedInUser.getInstance().getLoggedInCandidate() != null;

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        candidatesAdapter = new CandidatesAdapter(this,candidateArrayList);
        recyclerView.setAdapter(candidatesAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchArea.getText().toString().isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    runSearch(searchArea.getText().toString());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.candidate_display_menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void runSearch(String toString) {

        dismissWarning();
        new fetchCandidateProfile().execute(toString);

    }

    private void dismissWarning(){

        search_warning.setVisibility(View.GONE);

    }

    public void showWarning(Candidate candidate){

        if (warningCheck){

            if (!UserHomeActivity.thisCandidate.getGroup().equals(candidate.getGroup())){

                search_warning.setText(getResources().getString(R.string.assessor_warning));
                search_warning.setVisibility(View.VISIBLE);

            }

        }


    }

    public class fetchCandidateProfile extends AsyncTask<String,Void, Candidate>{

        @Override
        protected void onPostExecute(Candidate candidate) {

            if (candidate!=null){

                candidatesAdapter.setArrayList(new ArrayList<Candidate>());
                candidatesAdapter.getArrayList().add(candidate);
                candidatesAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);

                showWarning(candidate);

            }else {

                Toast.makeText(CandidatesDisplayActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }

            progressBar.setVisibility(View.GONE);

        }

        @Override
        protected Candidate doInBackground(String... strings) {


            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.GetCandidateByRegNo(strings[0]);
                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedCandidate = candidateBackgroundApiTasks.getCandidate();
            }

            return  returnedCandidate;
        }

    }

}
