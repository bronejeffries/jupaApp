package com.example.jupa.Candidate.Category.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Helpers.RetrofitSingleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CandidateCategoryBackgroundApiTasks {

    CandidateCategory candidateCategory;
    ArrayList<CandidateCategory> candidateCategoryArrayList;
    Context context;
    public String FAILED = "0",message;
    Integer SUCCESS = 1;
    public static CandidateCategoryBackgroundApiTasks Instance = null;
    CandidateCategoryApiInterface candidateCategoryApiInterface = RetrofitSingleton.getRetrofitInstance().create(CandidateCategoryApiInterface.class);

    public CandidateCategoryBackgroundApiTasks(Context context) {
        this.context = context;
    }

    public static CandidateCategoryBackgroundApiTasks getInstance(Context context) {

        if (Instance == null){
            Instance = new CandidateCategoryBackgroundApiTasks(context);
        }
        return Instance;
    }

    public void fetchCategories(){

        Call<CandidateCategoryListApiData> call = candidateCategoryApiInterface.getAllCategories();
        call.enqueue(new Callback<CandidateCategoryListApiData>() {
            @Override
            public void onResponse(Call<CandidateCategoryListApiData> call, Response<CandidateCategoryListApiData> response) {

                synchronized (CandidateCategoryBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse categories: "+ (response.body() != null ? response.body().getCandidateCategoryArrayList().toString() : null));
                    Log.e(TAG, "onResponse: "+response.body().getSuccess().equals(SUCCESS));
                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidateCategoryArrayList(response.body().getCandidateCategoryArrayList());

                    }

                    setMessage(response.body().getMessage());
                    CandidateCategoryBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateCategoryListApiData> call, Throwable t) {

                synchronized (CandidateCategoryBackgroundApiTasks.this){
                    t.printStackTrace();
                    CandidateCategoryBackgroundApiTasks.this.notifyAll();
                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    public void getCategoryById(Integer category_id){

        Call<CandidateCategoryApiData> call = candidateCategoryApiInterface.getCandidateCategory(category_id);
        call.enqueue(new Callback<CandidateCategoryApiData>() {
            @Override
            public void onResponse(Call<CandidateCategoryApiData> call, Response<CandidateCategoryApiData> response) {

                synchronized (CandidateCategoryBackgroundApiTasks.this){

//                    Log.e(TAG, "onResponse categories: "+ (response.body() != null ? response.body().getCandidateCategoryArrayList().toString() : null));
                    Log.e(TAG, "onResponse: "+response.body().getSuccess().equals(SUCCESS));
                    if (response.body().getSuccess().equals(SUCCESS)){

                        setCandidateCategory(response.body().getCandidateCategory());

                    }else {

                        setCandidateCategory(null);

                    }

                    setMessage(response.body().getMessage());
                    CandidateCategoryBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateCategoryApiData> call, Throwable t) {

                synchronized (CandidateCategoryBackgroundApiTasks.this){
                    t.printStackTrace();
                    setMessage(t.getMessage());
                    setCandidateCategory(null);
                    CandidateCategoryBackgroundApiTasks.this.notifyAll();
                }

            }
        });

    }


    public CandidateCategory getCandidateCategory() {
        return candidateCategory;
    }

    public void setCandidateCategory(CandidateCategory candidateCategory) {
        this.candidateCategory = candidateCategory;
    }

    public ArrayList<CandidateCategory> getCandidateCategoryArrayList() {
        return candidateCategoryArrayList;
    }

    public void setCandidateCategoryArrayList(ArrayList<CandidateCategory> candidateCategoryArrayList) {
        this.candidateCategoryArrayList = candidateCategoryArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
