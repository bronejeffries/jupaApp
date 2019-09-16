package com.example.jupa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    CardView groups, skills, ranks, institutions;
    ImageButton groups_icon, skills_icon;
    AlertDialog groupsDialog;
    final static String GROUPS_SHOW = "group", SKILLS_SHOW = "skill", RANKS_SHOW = "rank" ;
    private ArrayList<Skill> skillArrayList;
    private SkillsAdapter skillsAdapter;
    private RanksAdapter ranksAdapter;
    private ArrayList<Rank> rankArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }


    public void GroupClicked(View view){
        showActivity(GROUPS_SHOW);
    }

    public void SkillClicked(View view){
        showActivity(SKILLS_SHOW);
    }

    public void RankClicked(View view){
        showActivity(RANKS_SHOW);
    }

    private void showActivity(String clicked) {

        switch (clicked){

            case GROUPS_SHOW:
                LaunchGroupsActivity();
                break;

            case SKILLS_SHOW:
                LaunchSkillsBottomSheetDialog();
                break;

            case RANKS_SHOW:
                LaunchRanksBottomSheetDialog();
                break;
//
            default:
                Toast.makeText(this, "Wrong Option", Toast.LENGTH_SHORT).show();
        }

    }

    private void LaunchRanksBottomSheetDialog() {


        BottomSheetDialog rankBottomSheetDialog = new BottomSheetDialog(this);
        RelativeLayout sheetView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.bottomsheetlistview,null,false);
        manageRankView(sheetView, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popAddRankDialog(AdminHomeActivity.this);

            }
        }, "Ranks");
        rankBottomSheetDialog.setContentView(sheetView);
        rankBottomSheetDialog.show();


    }

    private void manageRankView(RelativeLayout sheetView, View.OnClickListener onClickListener, String title) {

        ListView listView = (ListView) sheetView.findViewById(R.id.bottom_sheet_list_view);
        ranksAdapter = RanksAdapter.getInstance(rankArrayList,getApplicationContext());
        listView.setAdapter(ranksAdapter);
        TextView sheet_title = (TextView)sheetView.findViewById(R.id.bottom_sheet_title);
        sheet_title.setText(title);
        FloatingActionButton floatingActionButton = (FloatingActionButton)sheetView.findViewById(R.id.bottom_sheet_fab);
        floatingActionButton.setOnClickListener(onClickListener);


    }

    private void popAddRankDialog(Context applicationContext) {

        CardView cardView = (CardView)LayoutInflater.from(applicationContext).inflate(R.layout.add_rank_view,null,true);
        final EditText rankNameInput = (EditText)cardView.findViewById(R.id.rank_name_input);
        Button save_button = (Button)cardView.findViewById(R.id.save_group_btn);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = rankNameInput.getText().toString();
                Rank newRank = new Rank(name);
                if (rankArrayList == null){
                    rankArrayList = new ArrayList<>();
                }
                rankArrayList.add(newRank);
                ranksAdapter.setRankArrayList(rankArrayList);
            }
        });
        BottomSheetDialog addSkillBottomSheet = new BottomSheetDialog(applicationContext);
        addSkillBottomSheet.setContentView(cardView);
        addSkillBottomSheet.show();
    }

    private void LaunchSkillsBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        RelativeLayout sheetView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.bottomsheetlistview,null,false);
        manageSkillView(sheetView, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popAddSkillDialog(AdminHomeActivity.this);

            }
        }, "Skills");
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

    }

    private void popAddSkillDialog(Context applicationContext) {

        CardView cardView = (CardView)LayoutInflater.from(applicationContext).inflate(R.layout.add_skill_view,null,true);
        final EditText skillNameInput = (EditText)cardView.findViewById(R.id.skill_name_input);
        Button save_button = (Button)cardView.findViewById(R.id.save_group_btn);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = skillNameInput.getText().toString();
                Skill newSkill = new Skill(name);
                if (skillArrayList == null){
                    skillArrayList = new ArrayList<>();
                }
                skillArrayList.add(newSkill);
                skillsAdapter.setSkillArrayList(skillArrayList);
            }
        });
        BottomSheetDialog addSkillBottomSheet = new BottomSheetDialog(applicationContext);
        addSkillBottomSheet.setContentView(cardView);
        addSkillBottomSheet.show();



    }

    private void manageSkillView(View sheetView, View.OnClickListener onClickListener, String title) {

        ListView listView = (ListView) sheetView.findViewById(R.id.bottom_sheet_list_view);
        skillsAdapter = SkillsAdapter.getInstance(skillArrayList,getApplicationContext());
        listView.setAdapter(skillsAdapter);
        TextView sheet_title = (TextView)sheetView.findViewById(R.id.bottom_sheet_title);
        sheet_title.setText(title);
        FloatingActionButton floatingActionButton = (FloatingActionButton)sheetView.findViewById(R.id.bottom_sheet_fab);
        floatingActionButton.setOnClickListener(onClickListener);

    }

    private void LaunchGroupsActivity() {

        Intent intent = new Intent(this,GroupsActivity.class);
        intent.putExtra(GroupsActivity.INTENT_EXTRA,GROUPS_SHOW);
        startActivity(intent);

    }

//    private void showOptionsDialog(String dialog) {
//
//        switch (dialog){
//
//            case GROUPS_SHOW:
////
//            default:
//                Toast.makeText(this, "Wrong Option", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.adminhomemenu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.register_link:
                Intent intent = new Intent(this,RegistrationActivity.class);
                startActivity(intent);
                return true;

            case R.id.logout_link:
                Intent logoutIntent = new Intent(this,LoginActivity.class);
                startActivity(logoutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

    }




}