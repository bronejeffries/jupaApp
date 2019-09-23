package com.example.jupa.Candidate.Api;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroupApiData;
import com.example.jupa.Assessment.AssessmentGroup.Api.AssessmentGroupListApiData;
import com.example.jupa.Candidate.Project.CandidateProjectApiData;
import com.example.jupa.Candidate.Project.CandidateProjectListApiData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CandidateApi_Interface {


    @FormUrlEncoded
    @POST("get_login.php")
    Call<CandidateApiData> loginInUser(@Field("username") String email, @Field("password") String phoneNumber);


    @FormUrlEncoded
    @POST("add_candidate.php")
    Call<CandidateApiData> addNewCandidate(@Field("firstname") String firstname, @Field("mobilenumber") String mobilenumber,
                                           @Field("candidateemail") String email, @Field("dob") String dateOfBirth, @Field("lastname") String lastname,
                                           @Field("familyname") String familyname, @Field("othernumber") String OtherNumber, @Field("gender") String gender,
                                           @Field("category_id") Integer category_id, @Field("country_id") Integer country_id, @Field("state_id") Integer state_id,
                                           @Field("city_id") Integer city_id, @Field("address") String address,@Field("education") String education );


    @GET("get_candidate_details.php")
    Call<CandidateApiData> getCandidateProfile(@Query("candidate_id") int candidate_Id);

    @GET("get_candidates_byregno.php")
    Call<CandidateApiData> getCandidateProfileByRegNo(@Query("candidate_reg_no") String  candidate_regNo);


    @GET("get_candidate_projects.php")
    Call<CandidateProjectListApiData> getCandidate_projects(@Query("candidate_id") int candidate_id);

    @FormUrlEncoded
    @POST("add_project.php")
    Call<CandidateProjectApiData> addCandidateProject(@Field("candidate_id") Integer candidate_id, @Field("title") String title, @Field("location") String loaction, @Field("publication_date") String publication_date,
                                                      @Field("client_name") String client_name, @Field("client_contact") String client_contact,
                                                      @Field("client_email") String client_email, @Field("client_address") String client_address,
                                                      @Field("descrp") String descrp, @Field("project_status") Integer project_status);


    @GET("/")
    Call<AssessmentGroupListApiData> getAllProjectAssessmentGroups(@Query("project_id") int project_id);

    @GET("/")
    Call<AssessmentGroupApiData> getAssessmentGroupDetails(@Query("assessment_group_id") int assessment_group_id);

    @FormUrlEncoded
    @POST("/")
    Call<AssessmentGroupApiData> addNewAssessmentGroupToProject(@Field("assessment_group_name") String assessment_group_name, @Field("project_id") int project_id, @Field("assessor_id") int assessor_id );




}
