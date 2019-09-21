package com.example.jupa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CandidateCategoryApiInterface {

    @GET("fetch_all_categories.php")
    Call<CandidateCategoryListApiData> getAllCategories();

    @GET("get_category_byID.php")
    Call<CandidateCategoryApiData> getCandidateCategory(@Query("category_id") int candidate_id);



}
