package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.PopulateRegistrationSpinners;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;

public class SelectAssociationActivity extends AppCompatActivity {

    String member_level;
    Integer association_selected_id;
    Boolean restart=false;
    Candidate candidate;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showprogress;
    PopulateRegistrationSpinners populateRegistrationSpinners;
    Button save;
    Spinner associationsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_association);

        member_level = getIntent().getStringExtra(RegistrationActivity.LEVEL_EXTRA);
        candidate = getIntent().getParcelableExtra(RankRequestActivity.CANDIDATE_EXTRA);


        associationsSpinner = (Spinner)findViewById(R.id.association_spinner);
        save = (Button)findViewById(R.id.update_candidate);

        showprogress = new showProgressbar(this);
        showprogress.setMessage("Loading Associations");
        showprogress.show();

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        populateRegistrationSpinners = new PopulateRegistrationSpinners(this,null,null,null);
        populateRegistrationSpinners.setShowProgress(showprogress);
        populateRegistrationSpinners.setButton(save);
        populateRegistrationSpinners.populateAssociations(associationsSpinner,candidate.getGroup());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (save.getText().toString().equals(getResources().getString(R.string.reload))){

                    startActivity(getIntent());

                }else {

                    String associationText = associationsSpinner.getSelectedItem()!=null?associationsSpinner.getSelectedItem().toString():null;
                    Candidate association = populateRegistrationSpinners.groupsList.findAssociationByName(associationText);
                    association_selected_id = (association!=null)?association.getId():null;
                    candidate.setAssociation_id(association_selected_id);
                    showprogress.setMessage("Updating your information, a moment...");
                    showprogress.show();
                    new updateCandidate().execute(candidate);

                }

            }
        });



    }




    public void showRequestApplication(){

        Log.e("Show application", "showRequestApplication: "+candidate.toString());

        if (member_level!=null){

            if (member_level.equals(UserHomeActivity.CANDIDATE_ROLE)){

                Intent rankIntent = new Intent(this, RankRequestActivity.class);

                rankIntent.putExtra(RankRequestActivity.INITIAL_RANK,true);

                rankIntent.putExtra(RankRequestActivity.CANDIDATE_EXTRA,candidate);

                startActivity(rankIntent);
            }

        }

    }



    public class updateCandidate extends AsyncTask<Candidate,Void,Candidate> {

        @Override
        protected void onPostExecute(Candidate returnedCandidate) {

            showprogress.dismiss();
            Toast.makeText(SelectAssociationActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            if (returnedCandidate!=null){

                candidate = returnedCandidate;
                showRequestApplication();

            }

        }

        @Override
        protected Candidate doInBackground(Candidate... candidates) {

            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){


                candidateBackgroundApiTasks.updateCandidate(candidates[0]);

                try {


                    candidateBackgroundApiTasks.wait();


                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                returnedCandidate = candidateBackgroundApiTasks.getCandidate();

            }

            return returnedCandidate;

        }

    }


    @Override
    public void onBackPressed() {
        if (restart){
            super.onBackPressed();
        }else{
            moveTaskToBack(true);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restart = true;
        onBackPressed();
    }
}
