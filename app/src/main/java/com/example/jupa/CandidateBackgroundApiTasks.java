package com.example.jupa;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CandidateBackgroundApiTasks {


    Context context;
    Candidate candidate;
    CandidateProject candidateProject;
    ArrayList<Candidate> candidateArrayList;
    ArrayList<CandidateProject> candidateProjectArrayList = new ArrayList<>();

    public String SUCCESS = "1", FAILED = "0",message;
    static CandidateBackgroundApiTasks Instance = null;

    static CandidateApi_Interface candidateApi_interface = RetrofitSingleton.getRetrofitInstance().create(CandidateApi_Interface.class);

    public static CandidateBackgroundApiTasks getInstance(Context context) {

        if (Instance == null && context!=null ){

            Instance = new CandidateBackgroundApiTasks(context);

        }

        return Instance;
    }

    public CandidateBackgroundApiTasks(Context context) {
        this.context = context;
    }


    public void registerNewCandidate(Candidate newcandidate){

        Call<CandidateApiData> call = candidateApi_interface.addNewCandidate(newcandidate.getFirst_name(),newcandidate.getMobile_number(),newcandidate.getEmail(), newcandidate.getDate_of_birth(),
                newcandidate.getLast_name(),newcandidate.getFamily_name(),newcandidate.getOther_number(),newcandidate.getGender(), newcandidate.getCategory_id(),newcandidate.getCountry_id(),
                newcandidate.getState_id(),newcandidate.getCity_id(),newcandidate.getAddress(),newcandidate.getEducation());
        call.enqueue(new Callback<CandidateApiData>() {
            @Override
            public void onResponse(Call<CandidateApiData> call, Response<CandidateApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                    }
                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();
                }


            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    CandidateBackgroundApiTasks.this.notifyAll();

                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();

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

                    Log.e(ContentValues.TAG, "onResponse: "+response.body().getCandidateProjectArrayList().toString());

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidateProjectArrayList(response.body().getCandidateProjectArrayList());
                    }
                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();
                }
            }

            @Override
            public void onFailure(Call<CandidateProjectListApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    CandidateBackgroundApiTasks.this.notifyAll();

                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    public void addCandidateProject(CandidateProject candidateProject){

        Call<CandidateProjectApiData> call = candidateApi_interface.addCandidateProject(candidateProject.getCandidate_id(),candidateProject.getTitle(),candidateProject.getLocation(),candidateProject.getDate_of_completion(),
                                                                    candidateProject.getClient_name(),candidateProject.getClient_contact(),candidateProject.getClient_email(),
                                                                    candidateProject.getClient_address(),
                                                                    candidateProject.getDescription(),candidateProject.getStatus());
        call.enqueue(new Callback<CandidateProjectApiData>() {
            @Override
            public void onResponse(Call<CandidateProjectApiData> call, Response<CandidateProjectApiData> response) {

                synchronized (CandidateBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setCandidateProject(response.body().getCandidateProject());
                    }
                    setMessage(response.body().getMessage());
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

                    }
                    setMessage(response.body().getMessage());
                    CandidateBackgroundApiTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<CandidateApiData> call, Throwable t) {

                synchronized (CandidateBackgroundApiTasks.this){

                    CandidateBackgroundApiTasks.this.notifyAll();

                    t.printStackTrace();
                    setMessage(t.getMessage());

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
}
