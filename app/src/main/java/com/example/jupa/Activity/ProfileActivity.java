package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Candidate.Adapters.CandidateProjectsAdapter;
import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Candidate.Category.Api.CandidateCategoryBackgroundApiTasks;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Helpers.LoadImageToView;
import com.example.jupa.Helpers.LoggedInInstitution;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.R;
import com.example.jupa.Rank.Rank;
import com.example.jupa.Rank.Api.RankBackgroundApiTasks;
import com.example.jupa.Skill.Skill;
import com.example.jupa.Skill.SkillsAdapter;
import com.example.jupa.Helpers.showProgressbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static final String CANDIDATE_EXTRA ="candidate", RANK_EXTRA = "rank", CATEGORY_EXTRA = "category";
    public static final int EDIT_REQUEST =200 ;
    ListView skillsListView;
    RecyclerView projectsRecyclerView;
    public static CandidateProjectsAdapter candidateProjectsAdapter;
    Button view_projects,view_skills,view_subjects, assess_btn;
    Intent intent;
    public static Candidate candidate;
    SkillsAdapter skillsAdapter;
    TextView profile_name, rankView, role, status, available, date_available ,address ,country, state, city, contact, email, date_of_birth, group_name, category,bottom_sheet_title;
    ProgressBar rankProgressLoader, categoryProgressLoader,groupProgressLoader;
    FloatingActionButton createNew,showReportbtn, Viewmore, viewAssessorCandidate;
    CardView moreCardView;
    Integer candidate_id;
    showProgressbar showProgress;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    CandidateCategoryBackgroundApiTasks candidateCategoryBackgroundApiTasks;
    RankBackgroundApiTasks rankBackgroundApiTasks;
    GroupBackgroundApiTasks groupBackgroundApiTasks;
    public Candidate loggedInCandidate =  LoggedInUser.getInstance().getLoggedInCandidate();
    Boolean ProfileOwner = false, groupAdmin = false, expanded_more = false;
    private Rank candidateRank;
    private CandidateCategory candidateCategory;
    private Group group;
    public boolean candidates_view;
    public static boolean profile_edited =false;
    LinearLayout viewCandidatesLayout;
    CircleImageView profile_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        showProgress = new showProgressbar(this);

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);
        candidateCategoryBackgroundApiTasks = CandidateCategoryBackgroundApiTasks.getInstance(this);
        rankBackgroundApiTasks = RankBackgroundApiTasks.getInstance(this);
        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(this);

        intent = getIntent();
        candidate = intent.getParcelableExtra(CANDIDATE_EXTRA);
        candidateRank = intent.getParcelableExtra(RANK_EXTRA);
        candidateCategory = intent.getParcelableExtra(CATEGORY_EXTRA);
        group = intent.getParcelableExtra(GroupActivity.GROUP_TAG);

        candidate_id = candidate.getId();

        profile_image_view = (CircleImageView) findViewById(R.id.profile_image);
        profile_name = (TextView)findViewById(R.id.profile_name);

        rankView = (TextView)findViewById(R.id.rank_view);
        rankProgressLoader = (ProgressBar)findViewById(R.id.rank_progress);

        role = (TextView)findViewById(R.id.role_view);
        status = (TextView)findViewById(R.id.status_view);
        available = (TextView)findViewById(R.id.available_view);
        date_available = (TextView)findViewById(R.id.available_date_view);
        address = (TextView)findViewById(R.id.address);
        country = (TextView)findViewById(R.id.country_view);
        state = (TextView)findViewById(R.id.state_view);
        city = (TextView)findViewById(R.id.city_view);
        contact = (TextView)findViewById(R.id.contact_view);
        email = (TextView)findViewById(R.id.email_view);
        date_of_birth = (TextView)findViewById(R.id.dob_view);

        group_name = (TextView)findViewById(R.id.group_view);
        groupProgressLoader = (ProgressBar)findViewById(R.id.group_progress);

        category = (TextView)findViewById(R.id.category_profile_view);
        categoryProgressLoader = (ProgressBar)findViewById(R.id.category_progress);


        view_projects = (Button)findViewById(R.id.view_projects);
        view_subjects = (Button)findViewById(R.id.view_candidate_subjects);
        assess_btn = (Button)findViewById(R.id.assess_candidate);

        Viewmore = (FloatingActionButton)findViewById(R.id.view_more);

        viewCandidatesLayout = (LinearLayout)findViewById(R.id.view_assessor_candidates);

        view_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProjectsBottomSheet();
            }
        });

        view_subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this,CandidateSubjectsActivity.class);
                intent.putExtra(RegisterSubjectsActivity.CANDIDATE_EXTRA,candidate);
                startActivity(intent);

            }
        });

        assess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAssessment();
            }
        });

//        view_skills = (Button)findViewById(R.id.view_skills);
//        view_skills.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showSkillsBottomSheet();
//
//            }
//        });

        showReportbtn = (FloatingActionButton)findViewById(R.id.view_assessment_report);

        moreCardView = (CardView)findViewById(R.id.more_card);
        viewAssessorCandidate = (FloatingActionButton)findViewById(R.id.view_candidates);
        Viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getExpanded_more()){

                    moreCardView.setVisibility(View.VISIBLE);
                    setExpanded_more(true);

                }else {

                    moreCardView.setVisibility(View.GONE);
                    setExpanded_more(false);

                }
            }
        });

        populateViews();
//        attachOnClickListenerToAttachAssessor();

    }

    public void makeAssessment(){
        Intent assessmentIntent = new Intent(this, AssessmentActivity.class);
        assessmentIntent.putExtra(RankRequestActivity.CANDIDATE_EXTRA, candidate);
        startActivity(assessmentIntent);

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
                Intent attachProject_intent = new Intent(ProfileActivity.this, CandidateProjectActivity.class);
                attachProject_intent.putExtra(CandidateProjectActivity.CANDIDATE_INTENT,candidate);
                startActivity(attachProject_intent);
            }
        });

        projectsBottomSheetDialog.setContentView(relativeLayout);
        projectsBottomSheetDialog.show();

        populateProjects();

    }

    public void viewCandidatesClicked(View view){

        candidates_view = true;
        Intent candidates_intent = new Intent(ProfileActivity.this,GroupActivity.class);
        candidates_intent.putExtra(GroupActivity.ASSESSOR,candidate);
        candidates_intent.putExtra(GroupActivity.CANDIDATES_VIEW,candidates_view);
        candidates_intent.putExtra(GroupActivity.INSTITUTION,LoggedInInstitution.getInstance().getLoggedInInstitution());
        startActivity(candidates_intent);

    }

    public void showReportClicked(View view){

        Intent intent = new Intent(ProfileActivity.this,Assessment_reportActivity.class);
        startActivity(intent);

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
//        RelativeLayout attachLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.attach_comment_bottom_sheet,null,false);
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

        LoadImageToView.loadImage(this,candidate.getPhoto_url(),profile_image_view);
        profile_name.setText(candidate.getName());
        role.setText(candidate.getRole());
        status.setText(candidate.getStatus());
        available.setText(candidate.getAvailable());
        date_available.setText(candidate.getDate_available());
        address.setText(candidate.getAddress());
        country.setText(String.valueOf(candidate.getCountry_id()));
        state.setText(String.valueOf(candidate.getState_id()));
        city.setText(String.valueOf(candidate.getCity_id()));
        contact.setText(String.valueOf(candidate.getMobile_number()));
        email.setText(candidate.getEmail());
        date_of_birth.setText(candidate.getDate_of_birth());


        if (loggedInCandidate!=null) {

            findViewById(R.id.more_holder).setVisibility(View.VISIBLE);
            ProfileOwner = loggedInCandidate.getId().equals(candidate_id);


            Log.e("profile", "populateViews: "+ProfileOwner );

            if(ProfileOwner && UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ASSESSOR_ROLE)){

                viewCandidatesLayout.setVisibility(View.VISIBLE);

            }

            if(!ProfileOwner && UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ASSESSOR_ROLE)){

                assess_btn.setVisibility(View.VISIBLE);

            }else {

                assess_btn.setVisibility(View.GONE);

            }



            if (!UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.GROUP_ADMIN_ROLE)){

                status.setCompoundDrawables(null,null,null,null);

            }else{

//  TODO: attach status click listener

            }

        }else {

            status.setCompoundDrawables(null,null,null,null);
            ProfileOwner = false;

        }


////        fetch the candidate's rank
        if (candidateRank == null){
            rankProgressLoader.setVisibility(View.VISIBLE);
            new getCandidateRank().execute(candidate.getRank_id());
        }else {

            rankView.setText(candidateRank.getName());

        }
//
////        fetch the candidate's category
        if (candidateCategory == null){
            categoryProgressLoader.setVisibility(View.VISIBLE);
            new getCandidateCategory().execute(candidate.getCategory_id());
        }else {
            category.setText(candidateCategory.getName());
        }

//        fetch the candidate's group
        if (group == null){
            groupProgressLoader.setVisibility(View.VISIBLE);
            new getCandidateGroup().execute(candidate.getGroup());
        }else {
            group_name.setText(group.getGroup_name());
        }

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

            if (candidateProjects!=null){

                candidateProjectsAdapter.setCandidateProjectArrayList(candidateProjects);

            }else {
                Toast.makeText(ProfileActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }
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

    public class getCandidateCategory extends AsyncTask<Integer,Void,CandidateCategory>{

        @Override
        protected void onPostExecute(CandidateCategory returnedcategory) {

            if (returnedcategory!=null){
                candidateCategory = returnedcategory;
                category.setText(candidateCategory.getName());
            }else {
                Toast.makeText(ProfileActivity.this, candidateCategoryBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

            categoryProgressLoader.setVisibility(View.GONE);

        }

        @Override
        protected CandidateCategory doInBackground(Integer... integers) {

            CandidateCategory returnedCandidateCategory;

            synchronized (candidateCategoryBackgroundApiTasks){

                candidateCategoryBackgroundApiTasks.getCategoryById(integers[0]);
                try {
                    candidateCategoryBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                returnedCandidateCategory = candidateCategoryBackgroundApiTasks.getCandidateCategory();
            }

            return returnedCandidateCategory;
        }
    }

    public class updateCandidate extends AsyncTask<Candidate,Void,Candidate>{

        @Override
        protected void onPostExecute(Candidate returnedCandidate) {

            if (returnedCandidate!=null){

                candidate = returnedCandidate;

            }else {

                Toast.makeText(ProfileActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Candidate doInBackground(Candidate... candidates) {

            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){


                candidateBackgroundApiTasks.updateCandidate(candidates[0]);

                try {


                    candidateBackgroundApiTasks.wait();


                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                returnedCandidate = candidateBackgroundApiTasks.getCandidate();

            }

            return returnedCandidate;

        }

    }

    public class getCandidateGroup extends AsyncTask<Integer,Void,Group>{

        @Override
        protected void onPostExecute(Group returnedgroup) {

            if (returnedgroup!=null){
                group = returnedgroup;
                group_name.setText(group.getGroup_name());
            }else {
                Toast.makeText(ProfileActivity.this, groupBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }
            groupProgressLoader.setVisibility(View.GONE);

        }

        @Override
        protected Group doInBackground(Integer... integers) {
            Group returnedGroup;

            synchronized (groupBackgroundApiTasks){

                groupBackgroundApiTasks.getGroupById(integers[0]);

                try {
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedGroup = groupBackgroundApiTasks.getGroup();

            }

            return returnedGroup;
        }
    }

    public class getCandidateRank extends  AsyncTask<Integer, Void, Rank>{


        @Override
        protected void onPostExecute(Rank rank) {

            if (rank!=null){
                candidateRank = rank;
                rankView.setText(rank.getName());
            }else {
                Toast.makeText(ProfileActivity.this, rankBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
            }

            rankProgressLoader.setVisibility(View.GONE);

        }

        @Override
        protected Rank doInBackground(Integer... integers) {
            Rank returnedRank;

            synchronized (rankBackgroundApiTasks){

                rankBackgroundApiTasks.getRankById(integers[0]);
                try {
                    rankBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedRank = rankBackgroundApiTasks.getRank();

            }

            return returnedRank;

        }
    }

    public Boolean getExpanded_more() {
        return expanded_more;
    }

    public void setExpanded_more(Boolean expanded_more) {
        this.expanded_more = expanded_more;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (loggedInCandidate!=null && loggedInCandidate.getId().equals(candidate_id)) {

                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.profile_menu,menu);
                return true;

        }else {

            return super.onCreateOptionsMenu(menu);

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.edit_profile:
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                intent.putExtra(EditProfileActivity.CANDIDATE_EXTRA,candidate);
                startActivityForResult(intent,EDIT_REQUEST);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==EDIT_REQUEST){

            if (data!=null){

                candidate = data.getParcelableExtra(RankRequestActivity.CANDIDATE_EXTRA);
                if (ProfileOwner){
                    LoggedInUser.getInstance().LoginUser(candidate);
                }

            }

            handleResult(candidate);

        }
    }

    private void handleResult(Candidate candidate) {

        if (candidate!=null){

            Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show();
            populateViews();
        }

    }

}
