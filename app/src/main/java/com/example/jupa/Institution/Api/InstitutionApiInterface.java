package com.example.jupa.Institution.Api;

import com.example.jupa.Institution.Institution;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InstitutionApiInterface {



//    Call<UploadObject> uploadFile(@Path("id") String id, @Part MultipartBody.Part file,@Part("user")RequestBody user);

//    @Multipart
//    @POST("add_registered_institution.php")
//    Call<InstitutionApiData> add_new_institution(@Part("center_number") RequestBody center_number, @Part("registered_name") RequestBody registered_name,
//                                                 @Part("contact_person") RequestBody contact_person, @Part("telephone_number") RequestBody telephone_number,
//                                                 @Part("email_address") RequestBody email_address, @Part("physical_address") RequestBody physical_address,
//                                                 @Part("about_institution") RequestBody about_institution, @Part("website") RequestBody website,
//                                                 @Part("facebook") RequestBody facebook, @Part MultipartBody.Part file);

//    @Multipart
//    @POST("add_registered_institution.php")
//    Call<InstitutionApiData> add_new_institution(@Part("center_number") String center_number, @Part("registered_name") String registered_name,
//                                                 @Part("contact_person") String contact_person, @Part("telephone_number") String telephone_number,
//                                                 @Part("email_address") String email_address, @Part("physical_address") String physical_address,
//                                                 @Part("about_institution") String about_institution,@Part("website") String website, @Part("facebook") String facebook,
//                                                 @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("add_registered_institution.php")
    Call<InstitutionApiData> add_new_institution(@Field("center_number") String center_number, @Field("registered_name") String registered_name,
                                                 @Field("contact_person") String contact_person, @Field("telephone_number") String telephone_number,
                                                 @Field("email_address") String email_address, @Field("physical_address") String physical_address,
                                                 @Field("about_institution") String about_institution,@Field("website") String website, @Field("facebook") String facebook,
                                                 @Field("badge") String file);

    @GET("fetch_all_institutions.php")
    Call<InstitutionListApiData> getAllInstitutions();

    @FormUrlEncoded
    @POST("get_login_institution.php")
    Call<InstitutionApiData> loginInstitution(@Field("email") String email, @Field("secret_number") String Secret_number);

    @GET("get_institution_byid.php")
    Call<InstitutionApiData> getInstitutionById(@Query("institution_id") int institution_id);

    @FormUrlEncoded
    @POST("payments.php")
    Call<ResponseBody> payApplication(@Field("institution_id") Integer inst_id,@Field("amount") Integer amount,@Field("type") String type);

    @FormUrlEncoded
    @POST("verify_institution.php")
    Call<ResponseBody> verifyInstitution(@Field("institution_id") Integer inst_id,@Field("status") Integer status);


}
