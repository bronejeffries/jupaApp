package com.example.jupa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.PaymentDetails;
import com.example.jupa.Helpers.RaveHelper;
import com.example.jupa.R;

public class PaymentActivity extends AppCompatActivity {

    RaveHelper raveHelper;
    PaymentDetails paymentDetails;
    public static String PAYMENT_DETAILS_EXTRA = "payment",RESULT_INTENT_DATA = "result";
    public static Integer RESULT_CODE = 200;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentDetails = getIntent().getParcelableExtra(PAYMENT_DETAILS_EXTRA);
        raveHelper = new RaveHelper();
        startPayment();
    }

    public void startPayment(){

        raveHelper.makePayment(this,paymentDetails);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Boolean result= raveHelper.resultHandler(requestCode,resultCode,data);

        if (result){

            Toast.makeText(this, "Paid", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }

        returnResult(result);
    }

    public void returnResult(Boolean result){

        if(result){

            handleResult(result);

        }else{

            showPaymentStoppedDialog();

        }

    }

    public void handleResult(Boolean result){

        Intent intent = new Intent();
        intent.putExtra(RESULT_INTENT_DATA,result);
        setResult(RESULT_CODE,intent);
        finish();

    }

    public void showPaymentStoppedDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Payment");
        alertDialogBuilder.setMessage("Attempt to cancel the payment process.\n Application won't be valid until payment");
        alertDialogBuilder.setPositiveButton("Stop payment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (LoggedInUser.getInstance().getLoggedInCandidate()!=null){

                    handleResult(false);

                }else{

                    Intent outIntent = new Intent(PaymentActivity.this,MainActivity.class);
                    startActivity(outIntent);

                }

            }
        });

        alertDialogBuilder.setNegativeButton("Continue with Payment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                startPayment();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Choose option to continue", Toast.LENGTH_SHORT).show();
//        showPaymentStoppedDialog();
    }
}
