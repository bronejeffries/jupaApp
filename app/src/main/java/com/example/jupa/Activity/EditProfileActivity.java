package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.LoadImageToView;
import com.example.jupa.Helpers.SelectFileHelper;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity {

    public static final String CANDIDATE_EXTRA="candidate";
    Candidate candidate;
    Intent intent;
    Bitmap mBitmap;
    ImageView profile_pic_View;
    EditText mobile_no, other_no, email, address, date_available;
    Spinner available_spinner,education;
    String mobile_noText, other_noText, emailText,addressText, educationText, date_available_text,available_status_text;
    Button save_btn;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    showProgressbar showProgress;
    SelectFileHelper selectFileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        intent = getIntent();
        candidate = intent.getParcelableExtra(CANDIDATE_EXTRA);

        showProgress = new showProgressbar(this);

        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        selectFileHelper = SelectFileHelper.getInstance();

        email = (EditText)findViewById(R.id.email_input);
        email.setText(candidate.getEmail());

        mobile_no = (EditText)findViewById(R.id.mobile_no_input);
        mobile_no.setText(candidate.getMobile_number());

        other_no = (EditText)findViewById(R.id.other_no_input);
        other_no.setText(candidate.getOther_number());

        address = (EditText)findViewById(R.id.address_input);
        address.setText(candidate.getAddress());

        education = (Spinner)findViewById(R.id.education_input);
        String[] levels = getResources().getStringArray(R.array.education_levels);
        ArrayList<String> levels_arrayList = new ArrayList<>();
//        levels_arrayList.addAll(levels);
        for (String level : levels){
            levels_arrayList.add(level);
        }
        Integer position = levels_arrayList.indexOf(candidate.getEducation());
        if (position!=null){
            education.setSelection(position,true);
        }
//        education.setText(candidate.getEducation());

        date_available = (EditText)findViewById(R.id.available_date_view);
        date_available.setText(candidate.getDate_available());

        available_spinner = (Spinner)findViewById(R.id.available_view);


        profile_pic_View = (ImageView)findViewById(R.id.profile_image);
        LoadImageToView.loadImage(this,candidate.getPhoto_url(),profile_pic_View);

        save_btn = (Button)findViewById(R.id.register_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProgress.setMessage("updating profile..");
                showProgress.show();

                emailText = email.getText().toString();
                mobile_noText = mobile_no.getText().toString();
                other_noText = other_no.getText().toString();
                addressText = address.getText().toString();
                educationText = education.getSelectedItem().toString();
                date_available_text = date_available.getText().toString();
                available_status_text = available_spinner.getSelectedItem().toString();

                candidate.setEmail(emailText);
                candidate.setMobile_number(mobile_noText);
                candidate.setOther_number(other_noText);
                candidate.setAddress(addressText);
                candidate.setEducation(educationText);
                candidate.setDate_available(date_available_text);
                candidate.setAvailable(available_status_text);

                if (mBitmap==null){
                    BitmapDrawable drawable = (BitmapDrawable) profile_pic_View.getDrawable();
                    mBitmap = drawable.getBitmap();
                }
                String image = selectFileHelper.getStringImage(mBitmap);
                candidate.setFile_body(image);

                new updateCandidate().execute(candidate);

            }
        });

    }

    public void selectProfile(View view){

        SelectFileHelper.askPermissions(EditProfileActivity.this);
        startActivityForResult(SelectFileHelper.getPickImageChooserIntent(EditProfileActivity.this), SelectFileHelper.IMAGE_RESULT);

    }

    public void returnResult(Candidate candidate){

        Intent intent = new Intent();
        intent.putExtra(RankRequestActivity.CANDIDATE_EXTRA,candidate);
        setResult(ProfileActivity.EDIT_REQUEST,intent);
        finish();
    }

    public class updateCandidate extends AsyncTask<Candidate,Void,Candidate> {

        @Override
        protected void onPostExecute(Candidate returnedCandidate) {

            showProgress.dismiss();

            Toast.makeText(EditProfileActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            if (returnedCandidate!=null){

                candidate = returnedCandidate;
                returnResult(candidate);
            }

        }

        @Override
        protected Candidate doInBackground(Candidate... candidates) {

            Candidate returnedCandidate;

            synchronized (candidateBackgroundApiTasks){


                candidateBackgroundApiTasks.updateCandidate(candidates[0]);

                try {


                    candidateBackgroundApiTasks.wait();


                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                returnedCandidate = candidateBackgroundApiTasks.getCandidate();

            }

            return returnedCandidate;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == SelectFileHelper.IMAGE_RESULT) {


                String filePath = SelectFileHelper.getImageFilePath(data,EditProfileActivity.this);

                if (filePath != null) {
                    Log.e("image retrieved", "onActivityResult: "+filePath );
                    mBitmap = BitmapFactory.decodeFile(filePath);
                    profile_pic_View.setImageBitmap(mBitmap);

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

                    if (!SelectFileHelper.hasPermission(perms,EditProfileActivity.this)) {

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

}
