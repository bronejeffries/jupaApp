package com.example.jupa.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.jupa.R;



public class PermissionsActivity {

    public static final int PERMISSIONS_GRANTED = 0;
    public static final int PERMISSIONS_DENIED = 1;

    public static final int PERMISSION_REQUEST_CODE = 0;

    private static final String EXTRA_PERMISSIONS = "com.koops.sales.EXTRA_PERMISSIONS";
    private static final String PACKAGE_URL_SCHEME = "package:";

    private static PermissionsChecker checker;
    private boolean requiresCheck;

    public static void runPermissions(String[] permissions, Context context){

        checker = new PermissionsChecker(context);

        if (checker.lacksPermissions(permissions)) {

            requestPermissions(permissions,context);

        }

    }


    private static void requestPermissions(@NonNull String[] permissions,Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissions.length > 0){

                ((AppCompatActivity)context).requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }

        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
//
//            requiresCheck = true;
//
//        } else {
//
//            requiresCheck = false;
////            showMissingPermissionDialog();
//        }
//    }

    public static boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {

        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static void showMissingPermissionDialog(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(R.string.string_permission_help);
        dialogBuilder.setMessage(R.string.string_permission_help_text);
        dialogBuilder.setNegativeButton(R.string.string_permission_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
//                finish();
            }
        });
        dialogBuilder.setPositiveButton(R.string.string_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings(context);
            }
        });
        dialogBuilder.show();
    }

    public static void startAppSettings(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME +  context.getPackageName()));
        context.startActivity(intent);
    }

}