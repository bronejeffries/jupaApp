package com.example.jupa.Request.Api;

import com.example.jupa.Candidate.Candidate;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestsApiInterface {


    @FormUrlEncoded
    @POST("add_application.php")
    Call<RequestApiData> makeRequest(@Field("request_type") String request_type, @Field("candidate_id") int candidate_id,
                                     @Field("reg_no") String reg_no, @Field("experience") String experience,
                                     @Field("awards") String awards, @Field("reason") String reason, @Field("group_id") int group_id,
                                     @Field("rank_id") Integer rank_id, @Field("institution_id") Integer institution_id);

    @FormUrlEncoded
    @POST("approve_application_request.php")
    Call<RequestApiData> update_candidate_application(@Field("application_id") int application_id, @Field("approval_status") int approval_status,
                                                      @Field("comment") String comment, @Field("user_id") Integer user_id );

    @GET("fetch_all_applications.php")
    Call<RequestListApiData> getAllApplications(@Query("limit") int limit, @Query("last") int last);

    @GET("get_applications_bycandidateid.php")
    Call<RequestListApiData> getCandidateApplication(@Query("candidate_id") int candidate_id);

    @GET("get_applications_ingroup_byrequesttype.php")
    Call<RequestListApiData> getGroupApplicationsByType(@Query("group_id") int group_id,@Query("request_type") String request_type,@Query("limit") int limit, @Query("last") int last);


}
