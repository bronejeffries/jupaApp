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
import android.widget.Toast;

import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Project.CandidateProject;
import com.example.jupa.Helpers.SelectFileHelper;
import com.example.jupa.R;
import com.example.jupa.Helpers.showProgressbar;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class CandidateProjectActivity extends AppCompatActivity {

    EditText location, date_of_completion, client_names, client_contact, client_mail, description, client_address, title;
    ImageView project_photo;
    String locationText, date_of_completion_text, client_names_text, client_contact_text, client_mail_text, photo_url, descriptionText, client_addressText, titleText;
    Button attach_image_btn, save_project;
    Integer candidate_id;
    Candidate candidate;
    Intent intent;
    CandidateProject newProject;
    showProgressbar showProgress;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    final static String CANDIDATE_INTENT = "candidate_id";
    Bitmap mBitmap;
    SelectFileHelper selectFileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_candidate_project);
        intent = getIntent();
        candidate = intent.getParcelableExtra(CANDIDATE_INTENT);
        showProgress = new showProgressbar(this);
        candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(this);

        location = (EditText)findViewById(R.id.project_location_input);
        date_of_completion = (EditText)findViewById(R.id.project_date_completion_input);
        client_names = (EditText)findViewById(R.id.project_client_name_input);
        client_contact = (EditText)findViewById(R.id.project_client_contact_input);
        client_mail = (EditText)findViewById(R.id.project_client_email_input);
        project_photo = (ImageView)findViewById(R.id.project_image_view);
        description = (EditText)findViewById(R.id.project_description_input);
        client_address = (EditText)findViewById(R.id.project_client_address_input);
        attach_image_btn = (Button)findViewById(R.id.attach_image);
        save_project = (Button)findViewById(R.id.save_project);
        title = (EditText)findViewById(R.id.project_title_input);
        selectFileHelper = SelectFileHelper.getInstance();
        save_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_project();
            }
        });

    }


    public void save_project(){

        locationText = location.getText().toString();
        date_of_completion_text = location.getText().toString();
        client_names_text = client_names.getText().toString();
        client_contact_text = client_contact.getText().toString();
        client_mail_text = client_mail.getText().toString();
        descriptionText = description.getText().toString();
        client_addressText = client_address.getText().toString();
        titleText = title.getText().toString();

        if (validateInput()){

            newProject = new CandidateProject(null,candidate.getId(),titleText,locationText,date_of_completion_text,client_names_text,
                                            client_contact_text,client_mail_text,client_addressText,descriptionText,
                                        0,null);
            if (mBitmap==null){
                BitmapDrawable drawable = (BitmapDrawable) project_photo.getDrawable();
                mBitmap = drawable.getBitmap();
            }
            String image = selectFileHelper.getStringImage(mBitmap);
            newProject.setFile_body(image);

            addNewProject();
            //update the profile view recycler adapter

        }else {
            Toast.makeText(this, "Please Check the information provided", Toast.LENGTH_SHORT).show();
            return;

        }

    }

    private boolean validateInput() {

        boolean valid = true;

        if (locationText.length() <= 0){

            valid = false;
            location.setError("Project location is required!");

        }
        if (date_of_completion_text.length() <= 0){

            valid = false;
            date_of_completion.setError("Project date of completion is required!");

        }
        if (client_names_text.length() <= 0){

            valid = false;
            client_names.setError("Project client names are required!");

        }

        if (client_contact_text.length() <= 0){

            valid = false;
            client_contact.setError("Project client contact is required!");

        }

        return valid;
    }

    public void addNewProject(){

        showProgress.setMessage("A moment");
        showProgress.show();
        new sendProjectData().execute(newProject);

    }

    public void selectProjectImage(View view){

        SelectFileHelper.askPermissions(this);
        startActivityForResult(SelectFileHelper.getPickImageChooserIntent(this), SelectFileHelper.IMAGE_RESULT);


    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case SelectFileHelper.ALL_PERMISSIONS_RESULT:

                for (String perms : SelectFileHelper.permissionsToRequest) {

                    if (!SelectFileHelper.hasPermission(perms,this)) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == SelectFileHelper.IMAGE_RESULT) {


                String filePath = SelectFileHelper.getImageFilePath(data,this);
                if (filePath != null) {
                    mBitmap = BitmapFactory.decodeFile(filePath);
                    project_photo.setImageBitmap(mBitmap);
                    project_photo.setVisibility(View.VISIBLE);
                    RankRequestActivity.recommendation_selection_started = true;
                }
            }

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

    public class sendProjectData extends AsyncTask<CandidateProject,Void,CandidateProject>{

        @Override
        protected void onPostExecute(CandidateProject candidateProject) {

            Toast.makeText(CandidateProjectActivity.this, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();

            showProgress.dismiss();
            if (candidateProject!=null){

                ProfileActivity.candidateProjectsAdapter.addCandidateProject(candidateProject);
                onBackPressed();

            }
//            return to the candidates profile activity
        }

        @Override
        protected CandidateProject doInBackground(CandidateProject... candidateProjects) {

            CandidateProject returnedProject;
            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.addCandidateProject(candidateProjects[0]);
                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                returnedProject = candidateBackgroundApiTasks.getCandidateProject();

            }

            return returnedProject;

        }
    }

}
