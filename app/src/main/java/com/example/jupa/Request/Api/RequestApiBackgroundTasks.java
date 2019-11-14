package com.example.jupa.Request.Api;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jupa.Activity.GroupSearchActivity;
import com.example.jupa.Helpers.RetrofitSingleton;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RequestApiBackgroundTasks {

    Context context;
    RequestApplicationObject requestApplicationObject;
    ArrayList<RequestApplicationObject> requestApplicationObjectArrayList;

    public String SUCCESS = "1", message;

    public static RequestApiBackgroundTasks instance = null;

    public final static RequestsApiInterface requestsApiInterface = RetrofitSingleton.getRetrofitInstance().create(RequestsApiInterface.class);

    public static RequestApiBackgroundTasks getInstance(Context context) {

        if (instance == null && context!=null ){

            instance = new RequestApiBackgroundTasks(context);

        }

        return instance;
    }

    public RequestApiBackgroundTasks(Context context) {

        this.context = context;

    }


    public void getCandidateApplications(int candidate_id){

        Call<RequestListApiData> call = requestsApiInterface.getCandidateApplication(candidate_id);
        call.enqueue(new Callback<RequestListApiData>() {
            @Override
            public void onResponse(Call<RequestListApiData> call, Response<RequestListApiData> response) {

                synchronized (RequestApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setRequestApplicationObjectArrayList(response.body().getRequestApplicationsArrayList());

                    }else {

                        setRequestApplicationObjectArrayList(null);

                    }

                    setMessage(response.body().getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<RequestListApiData> call, Throwable t) {

                synchronized (RequestApiBackgroundTasks.this){

                    setRequestApplicationObjectArrayList(null);
                    setMessage(t.getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();

                }

            }
        });

    }

    public void getAllApplications(GroupSearchActivity.searchObject searchObject){

        Log.e(TAG, "getAllApplications: "+searchObject.getLast()+" "+searchObject.getLimit());

        Call<RequestListApiData> call = requestsApiInterface.getAllApplications(searchObject.getLimit(),searchObject.getLast());

        call.enqueue(new Callback<RequestListApiData>() {
            @Override
            public void onResponse(Call<RequestListApiData> call, Response<RequestListApiData> response) {

                synchronized (RequestApiBackgroundTasks.this){

                    try {

                        if (response.body().getSuccess().equals(SUCCESS)){
                            setRequestApplicationObjectArrayList(response.body().getRequestApplicationsArrayList());
                            Log.e(TAG, "onResponse: "+response.body().requestApplicationsArrayList.size() );

                        }else {

                            setRequestApplicationObjectArrayList(null);

                        }

                        setMessage(response.body().getMessage());

                    }catch (NullPointerException e){

                        setRequestApplicationObjectArrayList(null);

                    }

                    RequestApiBackgroundTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<RequestListApiData> call, Throwable t) {

                synchronized (RequestApiBackgroundTasks.this){

                    setRequestApplicationObjectArrayList(null);
                    setMessage(t.getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();

                }

            }
        });

    }

    public void getAllGroupApplicationsByType(GroupSearchActivity.searchObject searchObject){

        Log.e(ContentValues.TAG, "getAllGroupApplicationsByType: "+searchObject.getGroup_id()+" "+searchObject.getStatus()+" "+searchObject.getLast() );


        Call<RequestListApiData> call = requestsApiInterface.getGroupApplicationsByType(searchObject.getGroup_id(),searchObject.getRequestType(),searchObject.getLimit(),searchObject.getLast());
        call.enqueue(new Callback<RequestListApiData>() {
            @Override
            public void onResponse(Call<RequestListApiData> call, Response<RequestListApiData> response) {

                synchronized (RequestApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setRequestApplicationObjectArrayList(response.body().getRequestApplicationsArrayList());

                    }else {

                        setRequestApplicationObjectArrayList(null);

                    }

                    setMessage(response.body().getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<RequestListApiData> call, Throwable t) {

                synchronized (RequestApiBackgroundTasks.this){

                    setRequestApplicationObjectArrayList(null);
                    setMessage(t.getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();

                }

            }
        });

    }

//    public void getApplicationsByType(String request_Type){
//
//        Call<RequestListApiData> call = requestsApiInterface.getApplicationsByType(request_Type);
//        call.enqueue(new Callback<RequestListApiData>() {
//            @Override
//            public void onResponse(Call<RequestListApiData> call, Response<RequestListApiData> response) {
//
//                synchronized (RequestApiBackgroundTasks.this){
//
//                    if (response.body().getSuccess().equals(SUCCESS)){
//
//                        setRequestApplicationObjectArrayList(response.body().getRequestApplicationsArrayList());
//
//                    }else {
//
//                        setRequestApplicationObjectArrayList(null);
//
//                    }
//
//                    setMessage(response.body().getMessage());
//                    RequestApiBackgroundTasks.this.notifyAll();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<RequestListApiData> call, Throwable t) {
//
//                synchronized (RequestApiBackgroundTasks.this){
//
//                    setRequestApplicationObjectArrayList(null);
//                    setMessage(t.getMessage());
//                    RequestApiBackgroundTasks.this.notifyAll();
//
//                }
//
//            }
//        });
//
//
//    }

    public void submitRequest(RequestApplicationObject requestApplicationObject){

        Log.e(TAG, "submitRequest: "+String.valueOf(requestApplicationObject.getInstitution_id()) );

        Call<RequestApiData> call = requestsApiInterface.makeRequest(requestApplicationObject.getRequest_type(),
                requestApplicationObject.getCandidate_id(),requestApplicationObject.getRegNo(),requestApplicationObject.getExperience(),
                requestApplicationObject.getQualification(),requestApplicationObject.getReason(),requestApplicationObject.getGroup_id(),requestApplicationObject.getRank_id(),
                requestApplicationObject.getInstitution_id());

        call.enqueue(new Callback<RequestApiData>() {
            @Override
            public void onResponse(Call<RequestApiData> call, Response<RequestApiData> response) {

                synchronized (RequestApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setRequestApplicationObject(response.body().getRequestApplication());
                    }else {

                        setRequestApplicationObject(null);

                    }

                    setMessage(response.body().getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();
                }


            }

            @Override
            public void onFailure(Call<RequestApiData> call, Throwable t) {


                synchronized (RequestApiBackgroundTasks.this){

                    setRequestApplicationObject(null);
                    setMessage(t.getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();

                }


            }
        });
    }


    public void approveRequest(RequestApplicationObject requestApplicationObject){

        Call<RequestApiData> call = requestsApiInterface.update_candidate_application(requestApplicationObject.getRequest_id(), requestApplicationObject.getStatus(),
                                                                                    requestApplicationObject.getComment(), requestApplicationObject.getUser_id());

        call.enqueue(new Callback<RequestApiData>() {
            @Override
            public void onResponse(Call<RequestApiData> call, Response<RequestApiData> response) {

                synchronized (RequestApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setRequestApplicationObject(response.body().getRequestApplication());
                    }else {

                        setRequestApplicationObject(null);

                    }

                    setMessage(response.body().getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<RequestApiData> call, Throwable t) {


                synchronized (RequestApiBackgroundTasks.this){

                    setRequestApplicationObject(null);
                    setMessage(t.getMessage());
                    RequestApiBackgroundTasks.this.notifyAll();

                }


            }
        });
    }


    public RequestApplicationObject getRequestApplicationObject() {
        return requestApplicationObject;
    }

    public void setRequestApplicationObject(RequestApplicationObject requestApplicationObject) {
        this.requestApplicationObject = requestApplicationObject;
    }

    public ArrayList<RequestApplicationObject> getRequestApplicationObjectArrayList() {
        return requestApplicationObjectArrayList;
    }

    public void setRequestApplicationObjectArrayList(ArrayList<RequestApplicationObject> requestApplicationObjectArrayList) {
        this.requestApplicationObjectArrayList = requestApplicationObjectArrayList;
    }

    public String getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(String SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
