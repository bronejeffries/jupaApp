package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Adapter.InstitutionsAdapter;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class InstitutionsActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Institution> institutionArrayList;
    public static InstitutionsAdapter institutionsAdapter;
    showProgressbar showProgress;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;
    public static final String SEARCH_PURPOSE = "search_purpose";
    public static Boolean search_purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institutions);

        search_purpose = getIntent().getBooleanExtra(SEARCH_PURPOSE,false);

        showProgress = new showProgressbar(this);

        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView)findViewById(R.id.institutions_recycler_view);
        institutionsAdapter = new InstitutionsAdapter(institutionArrayList,this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(institutionsAdapter);

        showProgress.setMessage("loading");
        showProgress.show();
        floatingActionButton =(FloatingActionButton)findViewById(R.id.new_institution_btn);

        if(!search_purpose){

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNewInstitutionActivity();
                }
            });

        }

        new fetchInstitutions().execute();
    }

    private void addNewInstitutionActivity() {

        Intent intent = new Intent(this,NewInstitutionActivity.class);
        startActivity(intent);

    }

    public class fetchInstitutions extends AsyncTask<Void,Void, ArrayList<Institution>>{

        @Override
        protected void onPostExecute(ArrayList<Institution> institutions) {

            if (institutions!=null){

                institutionArrayList = institutions;
                institutionsAdapter.setInstitutionArrayList(institutionArrayList);

            }else {

                Toast.makeText(InstitutionsActivity.this, "No registered institutions ", Toast.LENGTH_SHORT).show();

            }

            showProgress.dismiss();

        }

        @Override
        protected ArrayList<Institution> doInBackground(Void... voids) {

            ArrayList<Institution> returnedArrayList;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.getAllInstitutions();
                try {
                    institutionApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedArrayList = institutionApiBackgroundTasks.getInstitutionArrayList();

            }

            return returnedArrayList;
        }
    }


}
