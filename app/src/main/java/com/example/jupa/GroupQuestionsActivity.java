package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GroupQuestionsActivity extends AppCompatActivity {

    public static final String GROUP_EXTRA ="group" ;
    Intent incomingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_questions);
        incomingIntent = getIntent();
        
    }
}
