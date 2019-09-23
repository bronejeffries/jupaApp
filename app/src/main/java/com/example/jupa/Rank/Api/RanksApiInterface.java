package com.example.jupa.Rank.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RanksApiInterface {


    @FormUrlEncoded
    @POST("add_rank.php")
    Call<RankApiData> addNewRank(@Field("rank_name") String rank_name, @Field("rank_code") String rank_code);

    @GET("fetch_all_ranks.php")
    Call<RanksLIstApiData> getAllRanks();

    @GET("get_rank_byID.php")
    Call<RankApiData> getRankByID(@Query("rank_id") int rank_id);



}
