package com.example.jupa.Rank.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jupa.Rank.Rank;
import com.example.jupa.Helpers.RetrofitSingleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RankBackgroundApiTasks {

    Context context;
    Rank rank;
    ArrayList<Rank> rankArrayList;
   static RankBackgroundApiTasks Instance = null;
    public String SUCCESS = "1", FAILED = "0",message;
    static RanksApiInterface ranksApiInterface = RetrofitSingleton.getRetrofitInstance().create(RanksApiInterface.class);


    public RankBackgroundApiTasks(Context context) {
        this.context = context;
    }

    public static RankBackgroundApiTasks getInstance(Context context) {

        if (Instance == null && context!=null){
            Instance = new RankBackgroundApiTasks(context);
        }

        return Instance;
    }

    public void createRank(Rank rank){

        Call<RankApiData> call = ranksApiInterface.addNewRank(rank.getName(),rank.getCode());
        call.enqueue(new Callback<RankApiData>() {
            @Override
            public void onResponse(Call<RankApiData> call, Response<RankApiData> response) {

                synchronized (RankBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setRank(response.body().getRank());

                    }
                    setMessage(response.body().getMessage());
                    RankBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<RankApiData> call, Throwable t) {

                synchronized (RankBackgroundApiTasks.this){

                    RankBackgroundApiTasks.this.notifyAll();
                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void getRankById(int rank_id){

        Call<RankApiData> call = ranksApiInterface.getRankByID(rank_id);
        call.enqueue(new Callback<RankApiData>() {
            @Override
            public void onResponse(Call<RankApiData> call, Response<RankApiData> response) {

                synchronized (RankBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setRank(response.body().getRank());
                    }else {

                        setRank(null);

                    }
                    setMessage(response.body().getMessage());
                    RankBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<RankApiData> call, Throwable t) {

                synchronized (RankBackgroundApiTasks.this){

                    setRank(null);

                    setMessage(t.getMessage());

                    RankBackgroundApiTasks.this.notifyAll();

                }

            }
        });

    }

    public void getAllRanks(){

        Call<RanksLIstApiData> call = ranksApiInterface.getAllRanks();
        call.enqueue(new Callback<RanksLIstApiData>() {
            @Override
            public void onResponse(Call<RanksLIstApiData> call, Response<RanksLIstApiData> response) {


                synchronized (RankBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getSuccess() );

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setMessage(response.body().getMessage());
                        setRankArrayList(response.body().getRankArrayList());

                    }
                    RankBackgroundApiTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<RanksLIstApiData> call, Throwable t) {

                synchronized (RankBackgroundApiTasks.this){

                    RankBackgroundApiTasks.this.notifyAll();
                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public ArrayList<Rank> getRankArrayList() {
        return rankArrayList;
    }

    public void setRankArrayList(ArrayList<Rank> rankArrayList) {
        this.rankArrayList = rankArrayList;
    }
}
