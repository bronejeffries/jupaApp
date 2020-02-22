package com.example.jupa.Candidate.Api;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jupa.Assessment.Api.AssessmentApiData;
import com.example.jupa.Assessment.Api.AssessmentListApiData;
import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.example.jupa.Assessment.AssessmentGroup.Api.AssessmentGroupApiData;
import com.example.jupa.Assessment.AssessmentGroup.Api.AssessmentGroupListApiData;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Candidate.Project.CandidateProjectApiData;
import com.example.jupa.Candidate.Project.CandidateProjectListApiData;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Api.GroupCandidatesListApiData;
import com.example.jupa.Helpers.RetrofitSingleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.resolveSize;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class CandidateBackgroundApiTasks {


    Context context;
    Candidate candidate;
    CandidateProject candidateProject;
    ArrayList<Candidate> candidateArrayList;
    ArrayList<CandidateProject> candidateProjectArrayList = new ArrayList<>();
    AssessmentGroup assessmentGroup;
    ArrayList<AssessmentGroup> assessmentGroupArrayList;
    Assessment assessment;
    ArrayList<Assessment> assessmentArrayList;

    public String SUCCESS = "1", FAILED = "0",message;
    static CandidateBackgroundApiTasks Instance = null;

    static CandidateApi_Interface candidateApi_interface = RetrofitSingleton.getRetrofitInstance().create(CandidateApi_Interface.class);
    private Boolean sucessAssessment;

    public static CandidateBackgroundApiTasks getInstance(Context context) {

        if (Instance == null && context!=null ){

            Instance = new CandidateBackgroundApiTasks(context);

        }

        return Instance;
    }

    public CandidateBackgroundApiTasks(Context context) {
        this.context = context;
    }

    public void registerNewCandidate(Candidate newCandidate){

        Call<CandidateApiData> call = candidateApi_interface.addNewCandidate(newCandidate.getFirst_name(),newCandidate.getMobile_number(),
                newCandidate.getEmail(), newCandidate.getDate_of_birth(),newCandidate.getLast_name(),newCandidate.getFamily_name(),
                newCandidate.getOther_number(),newCandidate.getGender(), newCandidate.getCategory_id(),newCandidate.getCountry_id(),
                newCandidate.getState_id(),newCandidate.getCity_id(),newCandidate.getAddress(),newCandidate.getEducation(),
                newCandidate.getGroup(),newCandidate.getAssociation_name(),newCandidate.getAssociation_reg_date(),newCandidate.getAssociation_address());

        call.enqueue(new Callback<CandidateApiData>() {
            @Override
            public void onResponse(Call<CandidateApiData> call, Response<CandidateApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.toString() );


                    if (response.body().getSuccess().equals(SUCCESS)){
                        
                        setCandidate(response.body().getCandidate());

                    }

                    setMessage(response.body().getMessage());

                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setCandidate(null);
                    CandidateBackgroundApiTasks.this.notifyAll();


                }
            }
        });

    }

    public void updateCandidate(Candidate newCandidate){
        Log.e(TAG, "updateCandidate: "+newCandidate.getId()+" "+newCandidate.getEmail()+" "+newCandidate.getOther_number());
        Call<CandidateApiData> call = candidateApi_interface.updateCandidate(newCandidate.getId(),newCandidate.getFirst_name(),newCandidate.getMobile_number(),newCandidate.getEmail(), newCandidate.getDate_of_birth(),
                newCandidate.getLast_name(),newCandidate.getFamily_name(),newCandidate.getOther_number(),newCandidate.getGender(), newCandidate.getCategory_id(),newCandidate.getCountry_id(),
                newCandidate.getState_id(),newCandidate.getCity_id(),newCandidate.getAddress(),newCandidate.getEducation(),newCandidate.getGroup(),
                newCandidate.getAssociation_id(),newCandidate.getFile_body(),newCandidate.getAvailable(),newCandidate.getDate_available());
        call.enqueue(new Callback<CandidateApiData>() {
            @Override
            public void onResponse(Call<CandidateApiData> call, Response<CandidateApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidate(response.body().getCandidate());

                    }
                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();
                }


            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setCandidate(null);
                    setMessage(t.getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();


                }
            }
        });

    }

    public void getCandidateProjects(int candidate_id){

        Call<CandidateProjectListApiData> call = candidateApi_interface.getCandidate_projects(candidate_id);
        call.enqueue(new Callback<CandidateProjectListApiData>() {
            @Override
            public void onResponse(Call<CandidateProjectListApiData> call, Response<CandidateProjectListApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    Log.e(ContentValues.TAG, "projects onResponse: success "+response.body().getSuccess());

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidateProjectArrayList(response.body().getCandidateProjectArrayList());
                    }else{

                        setCandidateProjectArrayList(null);

                    }

                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();
                }
            }

            @Override
            public void onFailure(Call<CandidateProjectListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setCandidateProjectArrayList(null);
                    setMessage(t.getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }
        });
    }

    public void addCandidateProject(CandidateProject candidateProject){

        Call<CandidateProjectApiData> call = candidateApi_interface.addCandidateProject(candidateProject.getCandidate_id(),candidateProject.getTitle(),candidateProject.getLocation(),candidateProject.getDate_of_completion(),
                                                                    candidateProject.getClient_name(),candidateProject.getClient_contact(),candidateProject.getClient_email(),
                                                                    candidateProject.getClient_address(),
                                                                    candidateProject.getDescription(),candidateProject.getStatus(),candidateProject.getFile_body());
        call.enqueue(new Callback<CandidateProjectApiData>() {
            @Override
            public void onResponse(Call<CandidateProjectApiData> call, Response<CandidateProjectApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){
                    if (response.isSuccessful()){
                        if (response.body().getSuccess().equals(SUCCESS)){
                            setCandidateProject(response.body().getCandidateProject());
                        }
                        setMessage(response.body().getMessage());
                    }
                    CandidateBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<CandidateProjectApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    CandidateBackgroundApiTasks.this.notifyAll();

                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void loginInTask(String email, String phoneNumber){

        Call<CandidateApiData> call = candidateApi_interface.loginInUser(email,phoneNumber);

        call.enqueue(new Callback<CandidateApiData>() {
            @Override
            public void onResponse(Call<CandidateApiData> call, Response<CandidateApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().toString() );

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidate(response.body().getCandidate());

                    }else {

                        setCandidate(null);

                    }
                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setCandidate(null);
                    CandidateBackgroundApiTasks.this.notifyAll();

                }


            }
        });

    }

    public void GetCandidateByRegNo(String regNo){

        Call<CandidateApiData> call = candidateApi_interface.getCandidateProfileByRegNo(regNo);

        call.enqueue(new Callback<CandidateApiData>() {
            @Override
            public void onResponse(Call<CandidateApiData> call, Response<CandidateApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().toString() );

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setCandidate(response.body().getCandidate());
                    }else {

                        setCandidate(null);

                    }

                    setMessage(response.body().getMessage());

                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setCandidate(null);
                    setMessage("No internet connection  ");
                    CandidateBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();

                }


            }
        });

    }

    public void GetCandidateById(Integer candidate_id){

        Call<CandidateApiData> call = candidateApi_interface.getCandidateProfile(candidate_id);

        call.enqueue(new Callback<CandidateApiData>() {
            @Override
            public void onResponse(Call<CandidateApiData> call, Response<CandidateApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().toString() );

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setCandidate(response.body().getCandidate());
                    }else {

                        setCandidate(null);

                    }

                    setMessage(response.body().getMessage());

                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setCandidate(null);
                    setMessage("No internet connection  ");
                    CandidateBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();

                }


            }
        });

    }

    public void addAssessmentGroupToProject(AssessmentGroup assessmentGroup){

        Call<AssessmentGroupApiData> call = candidateApi_interface.addNewAssessmentGroupToProject(assessmentGroup.getName(), assessmentGroup.getProject_id(),
                                                                                                assessmentGroup.getAssessor_id(),
                                                                                                assessmentGroup.getCandidate_id());

        call.enqueue(new Callback<AssessmentGroupApiData>() {

            @Override
            public void onResponse(Call<AssessmentGroupApiData> call, Response<AssessmentGroupApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setAssessmentGroup(response.body().getAssessmentGroup());
                    }else {

                        setAssessmentGroup(null);

                    }

                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentGroupApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setAssessmentGroup(null);
                    CandidateBackgroundApiTasks.this.notifyAll();


                }

            }
        });

    }

    public void getProjectAssessmentGroups(int project_id){

        Call<AssessmentGroupListApiData> call = candidateApi_interface.getAllProjectAssessmentGroups(project_id);

        call.enqueue(new Callback<AssessmentGroupListApiData>() {

            @Override
            public void onResponse(Call<AssessmentGroupListApiData> call, Response<AssessmentGroupListApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setAssessmentGroupArrayList(response.body().getAssessmentGroupArrayList());

                    }else {

                        setAssessmentGroupArrayList(null);

                    }

                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentGroupListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setAssessmentGroupArrayList(null);
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }
        });

    }

    public void getCandidatesByMemberLevel(String memberLevel,String group_id){

        Integer group = Integer.parseInt(group_id);

        Call<CandidateListApiData> call = candidateApi_interface.getCandidatesbyMemberLevel(memberLevel,group);

        call.enqueue(new Callback<CandidateListApiData>() {

            @Override
            public void onResponse(Call<CandidateListApiData> call, Response<CandidateListApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidateArrayList(response.body().getCandidateArrayList());

                    }else {

                        setCandidateArrayList(null);

                    }

                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setCandidateArrayList(null);
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }
        });


    }

    public void getAssessmentsInAssessmentGroup(int assessment_group_id) {

        Call<AssessmentListApiData> call = candidateApi_interface.getAssessmentsInAssessmentGroup(assessment_group_id);

        call.enqueue(new Callback<AssessmentListApiData>() {

            @Override
            public void onResponse(Call<AssessmentListApiData> call, Response<AssessmentListApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setAssessmentArrayList(response.body().getAssessmentArrayList());
                        Log.e(TAG, "onResponse: "+response.body().getAssessmentArrayList().toString() );

                    }else {

                        setAssessmentArrayList(null);

                    }

                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){
                    setMessage(t.getMessage());
                    setAssessmentArrayList(null);
                    CandidateBackgroundApiTasks.this.notifyAll();
                }

            }
        });

    }

    public void getCandidateAssessment(int candidate_id) {

        Call<AssessmentListApiData> call = candidateApi_interface.getCandidateAssessments(candidate_id);

        call.enqueue(new Callback<AssessmentListApiData>() {

            @Override
            public void onResponse(Call<AssessmentListApiData> call, Response<AssessmentListApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    try {


                        if (response.body().getSuccess().equals(SUCCESS)){

                            setAssessmentArrayList(response.body().getAssessmentArrayList());
                            Log.e(TAG, "onResponse: "+response.body().getAssessmentArrayList().toString() );

                        }else {

                            setAssessmentArrayList(null);

                        }

                        setMessage(response.body().getMessage());


                    }catch (NullPointerException e){

                        setMessage("No data returned");
                        setAssessmentArrayList(null);

                    }
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){
                    setMessage(t.getMessage());
                    setAssessmentArrayList(null);
                    CandidateBackgroundApiTasks.this.notifyAll();
                }

            }
        });

    }

    public void getInstitutionAssessments(int institution_id) {

        Call<AssessmentListApiData> call = candidateApi_interface.getInstitutionAssessments(institution_id);

        call.enqueue(new Callback<AssessmentListApiData>() {

            @Override
            public void onResponse(Call<AssessmentListApiData> call, Response<AssessmentListApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    try {

                        if (response.body().getSuccess().equals(SUCCESS)){

                            setAssessmentArrayList(response.body().getAssessmentArrayList());
                            Log.e(TAG, "onResponse: "+response.body().getAssessmentArrayList().toString() );

                        }else {

                            setAssessmentArrayList(null);

                        }

                        setMessage(response.body().getMessage());


                    }catch (NullPointerException e){

                        setMessage("No data returned");
                        setAssessmentArrayList(null);

                    }
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){
                    setMessage(t.getMessage());
                    setAssessmentArrayList(null);
                    CandidateBackgroundApiTasks.this.notifyAll();
                }

            }
        });

    }

    public void addAssessment(Assessment assessment){


        Call<AssessmentApiData> call = candidateApi_interface
                                        .makeAssessment(assessment.getQuestion_id(),assessment.getCandidate_id(),
                                                        assessment.getGrade(),assessment.getOther_remarks(),
                                                        assessment.getInstitution_id(),assessment.getAssessor_id());

        call.enqueue(new Callback<AssessmentApiData>() {
            @Override
            public void onResponse(Call<AssessmentApiData> call, Response<AssessmentApiData> response) {

                Log.e(TAG, "onResponse: assessment "+response.body().getSuccess());
                Log.e(TAG, "onResponse: assessment "+response.body().getMessage());
                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(response.body().getMessage());
                    if (response.body().getSuccess().equals(SUCCESS)){

                        sucessAssessment = true;

                    }
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentApiData> call, Throwable t) {

                Log.e(TAG, "onResponse: assessment "+t.getMessage());

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }
        });

    }

    public void closeAssessmentGap(Assessment assessment){

        Call<AssessmentApiData> call = candidateApi_interface
                .closeAssessmentGap(assessment.getAssessment_id(),assessment.getInstitute_remarks());

        call.enqueue(new Callback<AssessmentApiData>() {
            @Override
            public void onResponse(Call<AssessmentApiData> call, Response<AssessmentApiData> response) {

//                Log.e(TAG, "onResponse: assessment "+response.body().getSuccess());
//                Log.e(TAG, "onResponse: assessment "+response.body().getMessage());
                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setMessage(response.body().getMessage());
                        setAssessment(response.body().getAssessment());

                    }
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<AssessmentApiData> call, Throwable t) {

                Log.e(TAG, "onResponse: assessment "+t.getMessage());

                synchronized (CandidateBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setAssessment(null);
                    CandidateBackgroundApiTasks.this.notifyAll();

                }

            }
        });

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Candidate getCandidate() {

        return candidate;

    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public ArrayList<Candidate> getCandidateArrayList() {
        return candidateArrayList;
    }

    public void setCandidateArrayList(ArrayList<Candidate> candidateArrayList) {
        this.candidateArrayList = candidateArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CandidateProject> getCandidateProjectArrayList() {
        return candidateProjectArrayList;
    }

    public void setCandidateProjectArrayList(ArrayList<CandidateProject> candidateProjectArrayList) {
        this.candidateProjectArrayList = candidateProjectArrayList;
    }


    public CandidateProject getCandidateProject() {
        return candidateProject;
    }

    public void setCandidateProject(CandidateProject candidateProject) {
        this.candidateProject = candidateProject;
    }

    public AssessmentGroup getAssessmentGroup() {
        return assessmentGroup;
    }

    public void setAssessmentGroup(AssessmentGroup assessmentGroup) {
        this.assessmentGroup = assessmentGroup;
    }

    public ArrayList<AssessmentGroup> getAssessmentGroupArrayList() {
        return assessmentGroupArrayList;
    }

    public void setAssessmentGroupArrayList(ArrayList<AssessmentGroup> assessmentGroupArrayList) {
        this.assessmentGroupArrayList = assessmentGroupArrayList;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public ArrayList<Assessment> getAssessmentArrayList() {
        return assessmentArrayList;
    }

    public void setAssessmentArrayList(ArrayList<Assessment> assessmentArrayList) {
        this.assessmentArrayList = assessmentArrayList;
    }

    public String getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(String SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public Boolean getSucessAssessment() {
        return sucessAssessment;
    }

    public void setSucessAssessment(Boolean sucessAssessment) {
        this.sucessAssessment = sucessAssessment;
    }
}
