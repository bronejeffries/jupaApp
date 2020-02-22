package com.example.jupa.Institution.Api;

import android.content.Context;
import android.util.Log;

import com.example.jupa.Helpers.RetrofitSingleton;
import com.example.jupa.Institution.Institution;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InstitutionApiBackgroundTasks {

    Context context;
    Institution institution;
    ArrayList<Institution> institutionArrayList = new ArrayList<>();
    public String SUCCESS = "1", FAILED = "0", message;

    public static InstitutionApiBackgroundTasks Instance = null;
    public final static InstitutionApiInterface institutionApiInterface = RetrofitSingleton.getRetrofitInstance().create(InstitutionApiInterface.class);

    public static InstitutionApiBackgroundTasks getInstance(Context context) {

        if (Instance == null && context!=null ){

            Instance = new InstitutionApiBackgroundTasks(context);

        }

        return Instance;
    }

    public InstitutionApiBackgroundTasks(Context context) {
        this.context = context;
    }



    public void LoginInstitution(String email, String secret_number){

        Log.e(TAG, "LoginInstitution: login "+email+" "+secret_number);
        Call<InstitutionApiData> call = institutionApiInterface.loginInstitution(email,secret_number);

        call.enqueue(new Callback<InstitutionApiData>() {
            @Override
            public void onResponse(Call<InstitutionApiData> call, Response<InstitutionApiData> response) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    Log.e(TAG, "onResponse: login "+response.body().getSuccess());
                    Log.e(TAG, "onResponse: login "+response.body().getMessage());

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setInstitution(response.body().getInstitution());

                    }else {

                        setInstitution(null);

                    }

                    setMessage(response.body().getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<InstitutionApiData> call, Throwable t) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    t.printStackTrace();
                    setInstitution(null);
                    setMessage(t.getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }

            }
        });

    }


    public void add_institutionTask(Institution institution){

//        RequestBody center_no = RequestBody.create(MediaType.parse("text/plain"), institution.getCenter_No()),
//                name = RequestBody.create(MediaType.parse("text/plain"),institution.getName()),
//                contact_person = RequestBody.create(MediaType.parse("text/plain"),institution.getContactPerson()),
//                telephone = RequestBody.create(MediaType.parse("text/plain"),institution.getTelephone_number()),
//                email = RequestBody.create(MediaType.parse("text/plain"),institution.getEmail_address()),
//                physical_address = RequestBody.create(MediaType.parse("text/plain"),institution.getPhysical_address()),
//                about = RequestBody.create(MediaType.parse("text/plain"),institution.getAbout_institution()),
//                website = RequestBody.create(MediaType.parse("text/plain"),institution.getWebsite()),
//                facebook = RequestBody.create(MediaType.parse("text/plain"),institution.getFacebook());


        Call<InstitutionApiData> call = institutionApiInterface.add_new_institution(institution.getCenter_No(),institution.getName(),
                                        institution.getContactPerson(),institution.getTelephone_number(),institution.getEmail_address(),
                                            institution.getPhysical_address(),institution.getAbout_institution(),institution.getWebsite(),
                                            institution.getFacebook(),institution.getFile_body());

        call.enqueue(new Callback<InstitutionApiData>() {
            @Override
            public void onResponse(Call<InstitutionApiData> call, Response<InstitutionApiData> response) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setInstitution(response.body().getInstitution());

                    }else {

                        setInstitution(null);

                    }

                    setMessage(response.body().getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<InstitutionApiData> call, Throwable t) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    setInstitution(null);
                    setMessage(t.getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }
            }
        });

    }

    public void get_institutionById(Integer institution_id){
        Log.e(TAG, "get_institutionById: "+String.valueOf(institution_id));

        Call<InstitutionApiData> call = institutionApiInterface.getInstitutionById(institution_id);

        call.enqueue(new Callback<InstitutionApiData>() {
            @Override
            public void onResponse(Call<InstitutionApiData> call, Response<InstitutionApiData> response) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setInstitution(response.body().getInstitution());

                    }else {

                        setInstitution(null);

                    }

                    setMessage(response.body().getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<InstitutionApiData> call, Throwable t) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    setInstitution(null);
                    setMessage(t.getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }
            }
        });

    }

    public void getAllInstitutions(){

        Call<InstitutionListApiData> call = institutionApiInterface.getAllInstitutions();

        call.enqueue(new Callback<InstitutionListApiData>() {
            @Override
            public void onResponse(Call<InstitutionListApiData> call, Response<InstitutionListApiData> response) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setInstitutionArrayList(response.body().getInstitutionArrayList());
                    }else {
                        setInstitutionArrayList(null);
                    }
                    setMessage(response.body().getMessage());

                    InstitutionApiBackgroundTasks.this.notifyAll();

                }
            }

            @Override
            public void onFailure(Call<InstitutionListApiData> call, Throwable t) {

                synchronized (InstitutionApiBackgroundTasks.this){

                    setMessage(t.getMessage());
                    setInstitutionArrayList(null);
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }

            }
        });

    }

    public void payRequest(Institution institution,Integer ammount,String category){

        Call<ResponseBody> call = institutionApiInterface.payApplication(institution.getInstitution_id(),ammount,category);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                synchronized (InstitutionApiBackgroundTasks.this){
                    if (response.isSuccessful()){
                        setMessage("success");
                    }else {
                        setMessage(null);
                    }
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


                synchronized (InstitutionApiBackgroundTasks.this){

                    setInstitution(null);
                    setMessage(t.getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }


            }
        });
    }

    public void verifyInstitution(Institution institution){

        Call<ResponseBody> call = institutionApiInterface.verifyInstitution(institution.getInstitution_id(),institution.getStatus());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                synchronized (InstitutionApiBackgroundTasks.this){
                    if (response.isSuccessful()){
                        setMessage("success");
                    }else {
                        setMessage(null);
                    }
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


                synchronized (InstitutionApiBackgroundTasks.this){

                    setInstitution(null);
                    setMessage(t.getMessage());
                    InstitutionApiBackgroundTasks.this.notifyAll();

                }


            }
        });
    }



    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public ArrayList<Institution> getInstitutionArrayList() {
        return institutionArrayList;
    }

    public void setInstitutionArrayList(ArrayList<Institution> institutionArrayList) {
        this.institutionArrayList = institutionArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
