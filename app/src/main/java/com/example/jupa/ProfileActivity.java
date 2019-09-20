package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    public static final String CANDIDATE_EXTRA ="candidate";
    ListView skillsListView;
    RecyclerView projectsRecyclerView;
    public static CandidateProjectsAdapter candidateProjectsAdapter;
    Button view_projects,view_skills;
    Intent intent;
    Candidate candidate;
    SkillsAdapter skillsAdapter;
    TextView profile_name, assessor, role, status, available, country, state, city, town, contact, email, date_of_birth, group_name, category,bottom_sheet_title;
    FloatingActionButton createNew;
    Integer candidate_id;
    showProgressbar showProgress;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        showProgress = new showProgressbar(this);
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        intent = getIntent();
        profile_name = (TextView)findViewById(R.id.profile_name);
        assessor = (TextView)findViewById(R.id.assessor);
        role = (TextView)findViewById(R.id.role_view);
        status = (TextView)findViewById(R.id.status_view);
        available = (TextView)findViewById(R.id.available_view);
        country = (TextView)findViewById(R.id.country_view);
        state = (TextView)findViewById(R.id.state_view);
        city = (TextView)findViewById(R.id.city_view);
        town = (TextView)findViewById(R.id.town_view);
        contact = (TextView)findViewById(R.id.contact_view);
        email = (TextView)findViewById(R.id.email_view);
        date_of_birth = (TextView)findViewById(R.id.dob_view);
        group_name = (TextView)findViewById(R.id.group_view);
        category = (TextView)findViewById(R.id.category_profile_view);
        candidate = intent.getParcelableExtra(CANDIDATE_EXTRA);
        candidate_id = candidate.getId();
        view_projects = (Button)findViewById(R.id.view_projects);
        view_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProjectsBottomSheet();
            }
        });
        view_skills = (Button)findViewById(R.id.view_skills);
        view_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSkillsBottomSheet();

            }
        });

        populateViews();
//        attachOnClickListenerToAttachAssessor();

    }

    private void showSkillsBottomSheet() {

        BottomSheetDialog skillsBottomSheetDialog = new BottomSheetDialog(this);
        RelativeLayout relativeLayout = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.bottomsheetlistview,null,false);
        skillsListView = (ListView) relativeLayout.findViewById(R.id.bottom_sheet_list_view);
        populateSkills();

        bottom_sheet_title = (TextView)relativeLayout.findViewById(R.id.bottom_sheet_title);
        bottom_sheet_title.setText(R.string.skills);
        createNew = (FloatingActionButton)relativeLayout.findViewById(R.id.bottom_sheet_fab);
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSkillsDialog();
            }
        });

        skillsBottomSheetDialog.setContentView(relativeLayout);
        skillsBottomSheetDialog.show();

    }

    private void showProjectsBottomSheet() {

        BottomSheetDialog projectsBottomSheetDialog = new BottomSheetDialog(this);
        RelativeLayout relativeLayout = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.recyclerviewbottomsheet,null,false);
        projectsRecyclerView = (RecyclerView)relativeLayout.findViewById(R.id.bottom_recycler_view);

        bottom_sheet_title = (TextView)relativeLayout.findViewById(R.id.bottom_sheet_title);
        bottom_sheet_title.setText(R.string.projects);

        createNew = (FloatingActionButton)relativeLayout.findViewById(R.id.bottom_sheet_fab);
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attachProject_intent = new Intent(ProfileActivity.this,CandidateProjectActivity.class);
                attachProject_intent.putExtra(CandidateProjectActivity.CANDIDATE_INTENT,candidate);
                startActivity(attachProject_intent);
            }
        });

        projectsBottomSheetDialog.setContentView(relativeLayout);
        projectsBottomSheetDialog.show();

        populateProjects();

    }


//    public void attachOnClickListenerToAttachAssessor() {
//
//        assessor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showAssessorsDialog();
//
//            }
//        });
//    }

//    private void showAssessorsDialog() {
//        BottomSheetDialog attachAssessorBottomSheet = new BottomSheetDialog(this);
//
//        RelativeLayout attachLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.attach_assessor_bottom_sheet,null,false);
//        final Spinner assessors_spinner = (Spinner)attachLayout.findViewById(R.id.assessor_spinner);
//        populateAssessorSpinner(assessors_spinner);
//        Button attach_button = (Button)attachLayout.findViewById(R.id.save_assessor_btn);
//        attach_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String assessor_text = assessors_spinner.getSelectedItem().toString();
//                Candidate assessorFromGroup = RegisteredCandidates.getInstance().getAssessorFromGroup(candidate.getCandidate_group(),assessor_text);
//                candidate.setCandidateAssessor(assessorFromGroup);
//                Toast.makeText(ProfileActivity.this, "Assessor Assigned", Toast.LENGTH_SHORT).show();
//                assessor.setText(candidate.getCandidateAssessor().getName());
//            }
//        });
//
//        attachAssessorBottomSheet.setContentView(attachLayout);
//        attachAssessorBottomSheet.show();
//
//    }

//    public void populateAssessorSpinner(Spinner spinner) {
//
//        List<String> spinnerArray = RegisteredCandidates.getInstance().filterGroupAssessorsList(candidate.getCandidate_group());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerArray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//
//    }

    private void populateViews() {

        profile_name.setText(candidate.getName());
//        if(candidate.getCandidateAssessor()!= null){
//            assessor.setText(candidate.getCandidateAssessor().getName());
//        }

        role.setText(candidate.getRole());
        status.setText(candidate.getStatus());
        available.setText(candidate.getAvailable());
        country.setText(candidate.getAddress());
        contact.setText(candidate.getMobile_number());
        email.setText(candidate.getEmail());
        date_of_birth.setText(candidate.getDate_of_birth());
        group_name.setText(GroupActivity.groupName);
//        category.setText(candidate.getCategory());

    }




    private void showSkillsDialog() {
        SkillsAdapter singleton = SkillsAdapter.getInstance(null, null);
        final ArrayList<Skill> availableSkills = (singleton != null)?singleton.getSkillArrayList(): null;

        final String[] skills = {"Candidate","Assessor","Group Admin","Super Admin", "Administrator"};
//        if (availableSkills !=null ){
//            for (int i = 0; i<availableSkills.size();i++ ){
//                skills[i] =  availableSkills.get(i).getName();
//            }
//        }


        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        builder.setTitle("Attach Skill").setItems(skills, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (availableSkills!=null){
                    candidate.getCandidateSkills().add(availableSkills.get(i));
                    skillsAdapter.setSkillArrayList(candidate.getCandidateSkills());
                    Toast.makeText(ProfileActivity.this,"Skill Added Successfully" + String.valueOf(candidate.getCandidateSkills().size()), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ProfileActivity.this, "No Skill now", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void populateSkills() {

        skillsAdapter = new SkillsAdapter(candidate.getCandidateSkills(),this);
        skillsListView.setAdapter(skillsAdapter);
//        Toast.makeText(this, String.valueOf(skillsAdapter.getCount()), Toast.LENGTH_SHORT).show();
    }

    private void populateProjects() {

        ArrayList<CandidateProject> candidateProjectArrayList = new ArrayList<>();
        projectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        candidateProjectsAdapter = new CandidateProjectsAdapter(candidateProjectArrayList,this);
        projectsRecyclerView.setAdapter(candidateProjectsAdapter);
        fetchProjects();

    }

    private void fetchProjects() {
        showProgress.setMessage("Loading projects");
        showProgress.show();
        new getProjects().execute(candidate_id);
    }

    public class getProjects extends AsyncTask<Integer,Void,ArrayList<CandidateProject>>{

        @Override
        protected void onPostExecute(ArrayList<CandidateProject> candidateProjects) {

            Toast.makeText(ProfileActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            candidateProjectsAdapter.setCandidateProjectArrayList(candidateProjects);
            showProgress.dismiss();

        }

        @Override
        protected ArrayList<CandidateProject> doInBackground(Integer... integers) {

            ArrayList<CandidateProject> returnedProjects;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.getCandidateProjects(integers[0]);
                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedProjects = candidateBackgroundApiTasks.getCandidateProjectArrayList();
            }

            return returnedProjects;
        }
    }

}
