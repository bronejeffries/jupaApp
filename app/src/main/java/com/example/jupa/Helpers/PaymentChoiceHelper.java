package com.example.jupa.Helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jupa.Activity.MyRequestsActivity;
import com.example.jupa.Activity.NewInstitutionActivity;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Institution.Institution;
import com.example.jupa.Activity.PaymentActivity;
import com.example.jupa.R;
import com.example.jupa.Request.RequestApplicationObject;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PaymentChoiceHelper {

    public static RequestApplicationObject createdRequestApplicationObject;

    public static void showApplicationPaymentChoice(final Context context, final Candidate candidate){

        AlertDialog.Builder choice = new AlertDialog.Builder(context);
        choice.setMessage("Application has been successfully submitted\n Make Application Payment.");
        choice.setTitle("Application Payment");
        choice.setPositiveButtonIcon(context.getResources().getDrawable(R.drawable.ic_check_black_24dp));
        choice.setPositiveButton("Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                createdRequestApplicationObject.setPaid(0);
                MyRequestsActivity.requestApplicationAdapter.addItem(createdRequestApplicationObject);
                startCandidatePaymentIntent(candidate,context);

            }
        });


        choice.setNegativeButtonIcon(context.getResources().getDrawable(R.drawable.ic_close_black_24dp));
        choice.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                createdRequestApplicationObject.setPaid(0);
                if (MyRequestsActivity.requestApplicationAdapter!=null){

                    MyRequestsActivity.requestApplicationAdapter.addItem(createdRequestApplicationObject);

                }
                ((AppCompatActivity)context).finish();

            }
        });

        choice.show();

    }

    private static void startCandidatePaymentIntent(Candidate candidate,Context context) {

        PaymentDetails paymentDetails = new PaymentDetails(candidate.getEmail(),candidate.getFirst_name(),candidate.getLast_name(),"Payment for Application",
                String.valueOf(candidate.getId()),context.getResources().getString(R.string.country_payment),context.getResources().getString(R.string.payment_currency),0);

        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(PaymentActivity.PAYMENT_DETAILS_EXTRA,paymentDetails);
        context.startActivity(intent);
    }

    public static void startPaymentIntent(Institution institution,Context context) {

        PaymentDetails paymentDetails = new PaymentDetails(institution.getEmail_address(),institution.getName(),institution.getCenter_No(),"Payment for Registration",
                institution.getCenter_No(),"UG","UGX",0);
        Log.e(TAG, "startPaymentIntent: instit" );
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(PaymentActivity.PAYMENT_DETAILS_EXTRA,paymentDetails);
        context.startActivity(intent);

    }

    public static void showInstitutionPaymentChoice(final Context context, final Institution institution ){

        AlertDialog.Builder choice = new AlertDialog.Builder(context);
        choice.setMessage("Application has been successfully submitted\n Make Application Payment.");
        choice.setTitle("Application Payment");
        choice.setPositiveButtonIcon(context.getResources().getDrawable(R.drawable.ic_check_black_24dp));
        choice.setPositiveButton("Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                NewInstitutionActivity.payment_initiated = true;
                startPaymentIntent(institution,context);

            }
        });


        choice.setNegativeButtonIcon(context.getResources().getDrawable(R.drawable.ic_close_black_24dp));
        choice.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ((AppCompatActivity)context).finish();

            }
        });

        AlertDialog alertDialog = choice.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void showCandidatePaymentChoice(final Context context, final Candidate candidate ){

        AlertDialog.Builder choice = new AlertDialog.Builder(context);
        choice.setMessage("Application has been successfully submitted\n Make Application Payment.");
        choice.setTitle("Application Payment");
        choice.setPositiveButtonIcon(context.getResources().getDrawable(R.drawable.ic_check_black_24dp));
        choice.setPositiveButton("Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startCandidatePaymentIntent(candidate,context);

            }
        });


        choice.setNegativeButtonIcon(context.getResources().getDrawable(R.drawable.ic_close_black_24dp));
        choice.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ((AppCompatActivity)context).finish();

            }
        });

        AlertDialog alertDialog = choice.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

}
