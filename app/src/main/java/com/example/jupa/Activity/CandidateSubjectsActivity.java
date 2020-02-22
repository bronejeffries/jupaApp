package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.R;

public class CandidateSubjectsActivity extends AppCompatActivity {

    Candidate candidate;
    TextView no_subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_subjects);

        candidate = getIntent().getParcelableExtra(RegisterSubjectsActivity.CANDIDATE_EXTRA);
        no_subjects = (TextView)findViewById(R.id.no_subject_notice);

        no_subjects.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.candidate_subjects_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.register_for_subjects:
                Intent register_intent = new Intent(this, RegisterSubjectsActivity.class);
                register_intent.putExtra(RegisterSubjectsActivity.CANDIDATE_EXTRA,candidate.getId());
                register_intent.putExtra(RegisterSubjectsActivity.INSTITUTION_EXTRA,candidate.getInstitution_id());
                startActivity(register_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


}
