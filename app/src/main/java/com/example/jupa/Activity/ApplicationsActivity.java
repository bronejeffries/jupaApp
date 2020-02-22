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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;
import com.example.jupa.Request.Adapter.RequestApplicationAdapter;
import com.example.jupa.Request.Api.RequestApiBackgroundTasks;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

public class ApplicationsActivity extends AppCompatActivity {

    public static String ADMIN_VIEW = "admin_view", GROUP_ADMIN_VIEW = "group_admin_view", GROUP_ID="group_id";
    Boolean admin_view, group_admin_view,reloading,scrollDone;
    LinearLayout loaderView;
    RecyclerView applications_recycler_view;
    LinearLayoutManager linearLayoutManager;
    Intent intent;
    RequestApiBackgroundTasks requestApiBackgroundTasks;
    public static RequestApplicationAdapter requestApplicationAdapter;
    ArrayList<RequestApplicationObject> requestApplicationObjectArrayList;
    showProgressbar showProgress;
    private GroupSearchActivity.searchObject searchObject;
    Integer group_id,waitTimes,last=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);


        intent = getIntent();
        admin_view = intent.getBooleanExtra(ADMIN_VIEW,false);

        group_admin_view = intent.getBooleanExtra(GROUP_ADMIN_VIEW,false);
        group_id = intent.getIntExtra(GROUP_ID,0);

        requestApiBackgroundTasks = RequestApiBackgroundTasks.getInstance(this);
        requestApplicationObjectArrayList = new ArrayList<>();
        requestApplicationAdapter = new RequestApplicationAdapter(this,requestApplicationObjectArrayList,RequestApplicationAdapter.APPLICATION_VIEW);

        showProgress = new showProgressbar(this);
        searchObject = new GroupSearchActivity.searchObject(null,null,last,GroupSearchActivity.LIMIT,null);

        setReloading(false);
        loaderView = (LinearLayout)findViewById(R.id.spacer);
        applications_recycler_view = (RecyclerView)findViewById(R.id.applications_recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        applications_recycler_view.setLayoutManager(linearLayoutManager);

        applications_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

        applications_recycler_view.setAdapter(requestApplicationAdapter);
        populate_adapter();

    }


    public void populate_adapter(){

        if (group_admin_view){

            searchObject.setGroup_id(group_id);
            searchObject.setRequestType(RequestApplicationViewActivity.RANK_REQUEST_TYPE);

        }

        showProgress.setMessage("loading");
        showProgress.show();

        new fetchApplications().execute(searchObject);


    }

    public void reload(){

        loaderView.setVisibility(View.VISIBLE);
        setReloading(true);
        searchObject.setLast(last);

        new fetchApplications().execute(searchObject);

    }

    public void stopReload(){

        setReloading(false);
        loaderView.setVisibility(View.GONE);

    }


    public Boolean getReloading() {
        return reloading;
    }

    public void setReloading(Boolean reloading) {
        this.reloading = reloading;
    }

    public class fetchApplications extends AsyncTask<GroupSearchActivity.searchObject,Void,ArrayList<RequestApplicationObject>>{

        @Override
        protected void onPostExecute(ArrayList<RequestApplicationObject> requestApplicationObjects) {

            if (requestApplicationObjects!=null){

                if (requestApplicationObjects.size()>0){

                    requestApplicationAdapter.addItems(requestApplicationObjects);
                    Log.e("Applications", "onPostExecute: "+requestApplicationObjects.size());

                }
                last += searchObject.getLimit();

            }else {

                Toast.makeText(ApplicationsActivity.this, requestApiBackgroundTasks.getMessage(), Toast.LENGTH_LONG).show();

            }
            stopReload();

            // loading the first times the activity runs
            if (searchObject.getLast().equals(0)){

                showProgress.dismiss();

            }


        }

        @Override
        protected ArrayList<RequestApplicationObject> doInBackground(GroupSearchActivity.searchObject... searchObjects) {

            ArrayList<RequestApplicationObject> requestApplicationObjects;

            synchronized (requestApiBackgroundTasks){
                if (group_admin_view){

                    requestApiBackgroundTasks.getAllGroupApplicationsByType(searchObjects[0]);

                }else {

                    requestApiBackgroundTasks.getAllApplications(searchObjects[0]);

                }
                try {
                    requestApiBackgroundTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                requestApplicationObjects = requestApiBackgroundTasks.getRequestApplicationObjectArrayList();

            }

            return requestApplicationObjects;

        }

    }

}
