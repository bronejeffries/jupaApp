package com.example.jupa;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

public class showProgressbar {

    public Context context;
    public ProgressDialog progressDialog;
    public String message;
    public static showProgressbar Instance = null;


    public static showProgressbar getInstance(Context context) {

        if (Instance == null && context!=null){

            Instance = new showProgressbar(context);
        }

        return Instance;
    }



    public showProgressbar(Context context) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context.getApplicationContext());

    }

    public void show(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(R.style.Widget_AppCompat_ProgressBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.show();

    }

    public void dismiss(){

        progressDialog.dismiss();

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
