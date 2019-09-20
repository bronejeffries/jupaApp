package com.example.jupa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GroupApi_Interface {

    @GET("fetch_all_groups.php")
    Call<GrouplistApiData> getAllGroups();

    @FormUrlEncoded
    @POST("add_group.php")
    Call<GroupApiData> addGroup(@Field("group_name") String groupName, @Field("group_code")String groupCode);

    @GET("get_candidates_ingroup.php")
    Call<GroupCandidatesListApiData> getCandidatesInAGroup(@Query("group_id") int group_ID);

    @GET("get_candidates_ingroup_byrole.php")
    Call<GroupCandidatesListApiData> getCandidatesWithRole(@Query("group_id") int group_Id, @Query("member_level") String member_level);

    @GET("get_candidates_ingroup_rank_status.php")
    Call<GroupCandidatesListApiData> searchCandidatesInAgroup(@Query("group_id") Integer group_id, @Query("rank_id") Integer rank_id,
                                                              @Query("enroll_status") String status, @Query("last") Integer last,
                                                              @Query("limit") Integer limit );



}