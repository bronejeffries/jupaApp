package com.example.jupa;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CandidateCategoryApiInterface {

    @GET("fetch_all_categories.php")
    Call<CandidateCategoryListApiData> getAllCategories();

}
