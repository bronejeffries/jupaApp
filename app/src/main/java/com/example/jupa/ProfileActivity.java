package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    public static final String CANDIDATE_EXTRA ="candidate" ;
    ListView skillsListView;
    Button attachSkill;
    Intent intent;
    Candidate candidate;
    SkillsAdapter skillsAdapter;
    TextView profile_name, assessor, role, status, available, country, state, city, town, contact, email, date_of_birth, group_name, category;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        skillsListView = (ListView)findViewById(R.id.profile_skills_list);
        candidate = intent.getParcelableExtra(CANDIDATE_EXTRA);
        attachSkill = (Button)findViewById(R.id.attach_skill);

        populateViews();
        populateSkills();
        attachOnClickListenerToAttachSkill();
        attachOnClickListenerToAttachAssessor();

    }

    public void attachOnClickListenerToAttachAssessor() {

        assessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAssessorsDialog();

            }
        });
    }

    private void showAssessorsDialog() {
        BottomSheetDialog attachAssessorBottomSheet = new BottomSheetDialog(this);

        RelativeLayout attachLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.attach_assessor_bottom_sheet,null,false);
        final Spinner assessors_spinner = (Spinner)attachLayout.findViewById(R.id.assessor_spinner);
        populateAssessorSpinner(assessors_spinner);
        Button attach_button = (Button)attachLayout.findViewById(R.id.save_assessor_btn);
        attach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String assessor_text = assessors_spinner.getSelectedItem().toString();
                Candidate assessorFromGroup = RegisteredCandidates.getInstance().getAssessorFromGroup(candidate.getGroup(),assessor_text);
                candidate.setCandidateAssessor(assessorFromGroup);
                Toast.makeText(ProfileActivity.this, "Assessor Assigned", Toast.LENGTH_SHORT).show();
                assessor.setText(candidate.getCandidateAssessor().getName());
            }
        });

        attachAssessorBottomSheet.setContentView(attachLayout);
        attachAssessorBottomSheet.show();

    }

    public void populateAssessorSpinner(Spinner spinner) {

        List<String> spinnerArray = RegisteredCandidates.getInstance().filterGroupAssessorsList(candidate.getGroup());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    private void populateViews() {

        profile_name.setText(candidate.getName());
        if(candidate.getCandidateAssessor()!= null){
            assessor.setText(candidate.getCandidateAssessor().getName());
        }
        role.setText(candidate.getRole());
        status.setText(candidate.getStatus());
        available.setText(candidate.getAvailable());
        country.setText(candidate.getCountry());
        state.setText(candidate.getState());
        city.setText(candidate.getCity());
        town.setText(candidate.getTown());
        contact.setText(candidate.getContact());
        email.setText(candidate.getEmail());
        date_of_birth.setText(candidate.getDate_of_birth());
        group_name.setText(candidate.getGroup().getGroup_name());
        category.setText(candidate.getCategory());

    }

    private void attachOnClickListenerToAttachSkill() {

        attachSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSkillsDialog();
            }
        });

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


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Attach Skill").setItems(skills, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (availableSkills!=null){
                    candidate.getSkills().add(availableSkills.get(i));
                    skillsAdapter.setSkillArrayList(candidate.getSkills());
                    Toast.makeText(ProfileActivity.this,"Skill Added Successfully" + String.valueOf(candidate.getSkills().size()), Toast.LENGTH_SHORT).show();
                    
                }else {
                    Toast.makeText(ProfileActivity.this, "No Skill now", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void populateSkills() {

        skillsAdapter = new SkillsAdapter(candidate.getSkills(),this);
        skillsListView.setAdapter(skillsAdapter);
        Toast.makeText(this, String.valueOf(skillsAdapter.getCount()), Toast.LENGTH_SHORT).show();

    }





}
