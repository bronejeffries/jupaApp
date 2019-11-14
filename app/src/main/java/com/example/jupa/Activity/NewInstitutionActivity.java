package com.example.jupa.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jupa.Helpers.SelectFileHelper;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Institution.Api.InstitutionApiBackgroundTasks;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NewInstitutionActivity extends AppCompatActivity {


    Bitmap mBitmap;

    showProgressbar showProgress;
    ImageView badgeView;
    EditText name_input, center_no_input,contact_person_input,telephone_number_input, physical_address,email_input, about_input, website_input, facebook_input;
    Button save_institution;
    String nameText, centerNoText, contactPersonText, telText, physical_addressText ,emailText, aboutText, websiteText, facebookText;
    InstitutionApiBackgroundTasks institutionApiBackgroundTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_institution);

        showProgress = new showProgressbar(this);
        institutionApiBackgroundTasks = InstitutionApiBackgroundTasks.getInstance(this);

        name_input = (EditText) findViewById(R.id.institutions_name_input);
        center_no_input = (EditText) findViewById(R.id.institutions_center_no_input);
        contact_person_input = (EditText) findViewById(R.id.institutions_contact_person_input);
        telephone_number_input = (EditText)findViewById(R.id.institutions_tel_number_input);
        physical_address = (EditText)findViewById(R.id.institutions_physical_address_input);
        email_input = (EditText)findViewById(R.id.institutions_email_input);
        about_input = (EditText)findViewById(R.id.institutions_about_input);
        website_input = (EditText)findViewById(R.id.institutions_website_input);
        facebook_input = (EditText)findViewById(R.id.institutions_facebook_input);
        save_institution = (Button)findViewById(R.id.save_institution);
        badgeView = (ImageView)findViewById(R.id.institution_image_view);


        save_institution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInstitution();
            }
        });

    }

    private void saveInstitution() {

        nameText = name_input.getText().toString();
        centerNoText = center_no_input.getText().toString();
        contactPersonText = contact_person_input.getText().toString();
        telText = telephone_number_input.getText().toString();
        physical_addressText = physical_address.getText().toString();
        emailText = email_input.getText().toString();
        aboutText = about_input.getText().toString();
        websiteText = website_input.getText().toString();
        facebookText = facebook_input.getText().toString();

        if (inputValid()){

            Institution newInstitution = new Institution(centerNoText,nameText,null,contactPersonText,telText,emailText,physical_addressText,
                    aboutText,websiteText,facebookText);
            showProgress.setMessage("Saving...");
            showProgress.show();
            new saveInstitutions().execute(newInstitution);


        }else {

            return;

        }
    }

    private boolean inputValid() {

        boolean valid = true;
        if (nameText.isEmpty()){
            valid = false;
            name_input.setError("Name is required!");
        }

        if (centerNoText.isEmpty()){
            valid = false;
            center_no_input.setError("Center number is required!");
        }

        if (contactPersonText.isEmpty()){

            valid = false;
            contact_person_input.setError("This is required");

        }

        if (telText.isEmpty()){

            valid = false;
            telephone_number_input.setError("This is required");

        }

        if (physical_addressText.isEmpty()){

            valid = false;
            physical_address.setError("This is required");
        }

        if (aboutText.isEmpty()){

            valid = false;
            about_input.setError("This is required");

        }

        if (emailText.isEmpty()){

            valid = false;
            email_input.setError("Email is required");

        }


        return valid;
    }


    public class saveInstitutions extends AsyncTask<Institution,Void,Institution>{


        @Override
        protected void onPostExecute(Institution institution) {


            if (institution!=null){

                if (InstitutionsActivity.institutionsAdapter!=null){
                    InstitutionsActivity.institutionsAdapter.addItem(institution);
                    showProgress.dismiss();
                    onBackPressed();
                }else {
                    showProgress.dismiss();
                    buildNotificationDialog(institutionApiBackgroundTasks.getMessage());
                }

            }else {

                Toast.makeText(NewInstitutionActivity.this, institutionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress.dismiss();

            }

        }

        @Override
        protected Institution doInBackground(Institution... institutions) {


            Institution returnedInstitution;

            synchronized (institutionApiBackgroundTasks){

                institutionApiBackgroundTasks.add_institutionTask(institutions[0]);

                try {

                    institutionApiBackgroundTasks.wait();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }
                returnedInstitution = institutionApiBackgroundTasks.getInstitution();

            }

            return returnedInstitution;

        }
    }

    private void buildNotificationDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_help_outline_black_24dp);
        builder.setTitle("Registration Complete");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                onBackPressed();

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void selectBadge(View view){

        SelectFileHelper.askPermissions(NewInstitutionActivity.this);
        startActivityForResult(SelectFileHelper.getPickImageChooserIntent(NewInstitutionActivity.this), SelectFileHelper.IMAGE_RESULT);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == SelectFileHelper.IMAGE_RESULT) {


                String filePath = SelectFileHelper.getImageFilePath(data,NewInstitutionActivity.this);
                if (filePath != null) {
                    mBitmap = BitmapFactory.decodeFile(filePath);
                    badgeView.setImageBitmap(mBitmap);
                }
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case SelectFileHelper.ALL_PERMISSIONS_RESULT:

                for (String perms : SelectFileHelper.permissionsToRequest) {

                    if (!SelectFileHelper.hasPermission(perms,NewInstitutionActivity.this)) {

                        SelectFileHelper.permissionsRejected.add(perms);

                    }
                }

                if (SelectFileHelper.permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(SelectFileHelper.permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(SelectFileHelper.permissionsRejected.toArray(new String[SelectFileHelper.permissionsRejected.size()]), SelectFileHelper.ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new android.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }


    private void multipartImageUpload() {

        try {
            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

//            Call<ResponseBody> req = .postImage(body, name);
//
//            req.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    if (response.code() == 200) {
//
//                    }
//
//                    Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                }
//            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
