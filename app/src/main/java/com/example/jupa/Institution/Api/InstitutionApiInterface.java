package com.example.jupa.Institution.Api;

import com.example.jupa.Institution.Institution;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InstitutionApiInterface {



    @FormUrlEncoded
    @POST("add_registered_institution.php")
    Call<InstitutionApiData> add_new_institution(@Field("center_number") String center_number, @Field("registered_name") String registered_name,
                                                 @Field("contact_person") String contact_person, @Field("telephone_number") String telephone_number,
                                                 @Field("email_address") String email_address, @Field("physical_address") String physical_address,
                                                 @Field("about_institution") String about_institution,@Field("website") String website, @Field("facebook") String facebook);

    @GET("fetch_all_institutions.php")
    Call<InstitutionListApiData> getAllInstitutions();

    @FormUrlEncoded
    @POST("get_login_institution.php")
    Call<InstitutionApiData> loginInstitution(@Field("email") String email, @Field("secret_number") String Secret_number);

    @GET("get_institution_byid.php")
    Call<InstitutionApiData> getInstitutionById(@Query("institution_id") int institution_id);

}
