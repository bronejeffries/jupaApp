package com.example.jupa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity {


    public static final String INTENT_EXTRA = "intent_extra" ;
    FloatingActionButton floatingActionButton;
    BottomSheetDialog addGroupDialog;
    RecyclerView recyclerView;
    GroupsAdapter adapter;
    ArrayList<Group> groupArrayList;
    showProgressbar showprogress;
    private GroupBackgroundApiTasks groupBackgroundApiTasks;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(GroupsActivity.this);
        showprogress = new showProgressbar(GroupsActivity.this);
        showprogress.setMessage("Loading..");
        showprogress.show();

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddGroupDialog();
            }
        });

        adapter = GroupsAdapter.getInstance(groupArrayList,this);
        groupArrayList = adapter.getGroupArrayList();
        recyclerView = (RecyclerView)findViewById(R.id.groups_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchAllGroups();

    }

    private void fetchAllGroups() {

            new fetchgroups().execute();

    }

    private class fetchgroups extends AsyncTask<Void,Void,ArrayList<Group>>{

        @Override
        protected void onPostExecute(ArrayList<Group> arrayList) {

            adapter.setGroupArrayList(arrayList);
            showprogress.dismiss();

        }

        @Override
        protected ArrayList<Group> doInBackground(Void... voids) {

            ArrayList<Group> groupArrayListReturned;

            synchronized (groupBackgroundApiTasks){

                try {
                    groupBackgroundApiTasks.fetchGroups();
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                groupArrayListReturned = groupBackgroundApiTasks.getGroupArrayList();

            }
            return groupArrayListReturned;

        }
    }

    public class addNewGroup extends AsyncTask<Group,Void, Group>{

        @Override
        protected void onPostExecute(Group group) {

            if (group!=null) {
                adapter.addItemToList(group);
            }
            showprogress.dismiss();
            Toast.makeText(GroupsActivity.this, message, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Group doInBackground(Group... groups) {

            Group returnedGroup;

            synchronized (groupBackgroundApiTasks){

                try {
                    groupBackgroundApiTasks.addNewGroup(groups[0]);
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedGroup = groupBackgroundApiTasks.getGroup();
                message = groupBackgroundApiTasks.getMessage();

            }

            return returnedGroup;
        }
    }


    private void showAddGroupDialog() {

        final EditText groupName_input, groupCode_input;
        Button save_group_btn;
        CardView addGroupCard = (CardView)LayoutInflater.from(this).inflate(R.layout.add_group_view,null,false);
        groupName_input = (EditText)addGroupCard.findViewById(R.id.group_name_input);
        groupCode_input = (EditText)addGroupCard.findViewById(R.id.group_code_input);
        save_group_btn = (Button)addGroupCard.findViewById(R.id.save_group_btn);
        save_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName, groupCode;
                groupName = groupName_input.getText().toString().toLowerCase();
                groupCode = groupCode_input.getText().toString().toLowerCase();
                save_group(groupName,groupCode);
            }
        });

        addGroupDialog = new BottomSheetDialog(this);
        addGroupDialog.setContentView(addGroupCard);
        addGroupDialog.show();

    }

    private void save_group(String groupName, String groupCode) {

        showprogress.show();
        Group newGroup = new Group(groupName,groupCode);
        new addNewGroup().execute(newGroup);

    }


}
