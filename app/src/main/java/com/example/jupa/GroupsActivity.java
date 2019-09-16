package com.example.jupa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);



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


    }


    private void showAddGroupDialog() {

        final EditText groupName_input;
        Button save_group_btn;
        CardView addGroupCard = (CardView)LayoutInflater.from(this).inflate(R.layout.add_group_view,null,false);
        groupName_input = (EditText)addGroupCard.findViewById(R.id.group_name_input);
        save_group_btn = (Button)addGroupCard.findViewById(R.id.save_group_btn);
        save_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName;
                groupName = groupName_input.getText().toString().toLowerCase();

                save_group(groupName);

            }
        });

        addGroupDialog = new BottomSheetDialog(this);
        addGroupDialog.setContentView(addGroupCard);
        addGroupDialog.show();

    }

    private void save_group(String groupName) {

        Integer group_id = adapter.getItemCount() + 1;
        Group newGroup = new Group(groupName,group_id,null,null,null);
        if (groupArrayList == null ){
            groupArrayList = new ArrayList<>();
        }
        groupArrayList.add(newGroup);
        adapter.setGroupArrayList(groupArrayList);
    }



}
