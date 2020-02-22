package com.example.jupa.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.jupa.R;
import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;


public class RaveHelper {


    final String publicKey = "FLWPUBK-09622c7a4b948dd3fb98aeb75e1aba50-X"; //Get your public key from your account
    final String encryptionKey = "95f1d0841a46b0cd049470db"; //Get your encryption key from your account

    String message;

    public void makePayment(Context context,PaymentDetails paymentDetails){

//        email +" "+

        /*
        Create instance of RavePayManager
         */

        new RavePayManager((Activity) context).setAmount(paymentDetails.getAmount())
                .setCountry(paymentDetails.getCountry())
                .setCurrency(paymentDetails.getCurrency())
                .setEmail(paymentDetails.getEmail())
                .setfName(paymentDetails.getfName())
                .setlName(paymentDetails.getlName())
                .setNarration(paymentDetails.getNarration())
                .setPublicKey(publicKey)
                .setEncryptionKey(encryptionKey)
                .setTxRef(paymentDetails.getTxRef())
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .acceptGHMobileMoneyPayments(false)
                .acceptUgMobileMoneyPayments(true)
                .acceptAccountPayments(true)
                .onStagingEnv(true)
                .allowSaveCardFeature(true)
                .withTheme(R.style.DefaultPayTheme)
                .initialize();
    }

    public boolean resultHandler(int requestCode, int resultCode, Intent data){

        Boolean validResult = false;

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                setMessage("SUCCESS ");
                validResult = true;
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {

                setMessage("ERROR ");
                validResult = false;

            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {

                setMessage("CANCELLED ");
                validResult = false;

            }
        }
        return validResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
