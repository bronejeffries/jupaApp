package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jupa.Institution.Institution;
import com.example.jupa.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InstitutionCoursesActivity extends AppCompatActivity {


    public static String INSTITUTION_EXTRA = "institution";
    Institution institution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_courses);
        institution = getIntent().getParcelableExtra(INSTITUTION_EXTRA);

    }

    public void addCourseBottomSheet(View view) {

        BottomSheetDialog addSubjectBottomSheetDialog = new BottomSheetDialog(this);
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.add_course_layout,null,false);

        Button save_subject = (Button)cardView.findViewById(R.id.save_subject_btn);
        final EditText subject_name = (EditText)cardView.findViewById(R.id.subject_name_input);
        save_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subject = subject_name.getText().toString();


            }
        });

        addSubjectBottomSheetDialog.setContentView(cardView);
        addSubjectBottomSheetDialog.show();


    }

}
