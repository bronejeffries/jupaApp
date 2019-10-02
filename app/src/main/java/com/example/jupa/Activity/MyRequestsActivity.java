package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.jupa.R;

public class MyRequestsActivity extends AppCompatActivity {

    Button file_request,assessor_choice, rank_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        file_request = (Button)findViewById(R.id.file_new_request);
        file_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestDialog();
            }
        });

    }

    private void showRequestDialog() {

        AlertDialog.Builder requestTypeDialog = new AlertDialog.Builder(this);
        requestTypeDialog.setTitle("New Request");

        CardView cardViewDialog = (CardView) LayoutInflater.from(this).inflate(R.layout.requests_dialog,null,false);
        assessor_choice = (Button)cardViewDialog.findViewById(R.id.assessor_request_btn);
        rank_choice = (Button)cardViewDialog.findViewById(R.id.rank_request_btn);

        if (UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.CANDIDATE_ROLE)){

            rank_choice.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            assessor_choice.setEnabled(true);
            assessor_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAssessorRequestActivity();
                }
            });

        }else {
            assessor_choice.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            rank_choice.setEnabled(true);
            rank_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showRankRequestActivity();

                }
            });

        }

        requestTypeDialog.setView(cardViewDialog);
        AlertDialog alertDialog = requestTypeDialog.create();
        alertDialog.show();
    }

    private void showAssessorRequestActivity() {

        Intent assessorRequestIntent = new Intent(this,AssessorRequestActivity.class);
        startActivity(assessorRequestIntent);
    }

    private void showRankRequestActivity() {

        Intent rankRequestIntent = new Intent(this,RankRequestActivity.class);
        startActivity(rankRequestIntent);

    }


}
