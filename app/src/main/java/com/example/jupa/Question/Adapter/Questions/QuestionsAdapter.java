package com.example.jupa.Question.Adapter.Questions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.AssessementActivity;
import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Question.Question;
import com.example.jupa.R;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter {

    ArrayList<Question> questionArrayList;
    Context context;
    Boolean assessment;

    public QuestionsAdapter(ArrayList<Question> questionArrayList, Context context, Boolean assessment) {
        this.questionArrayList = questionArrayList;
        this.context = context;
        this.assessment = assessment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.questionviewlayout,parent,false);

        return new questionViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0) {
            final Question question = questionArrayList.get(position);
            ((questionViewHolder) holder).questionView.setText(question.getTitle());

            if (this.assessment){
                ((questionViewHolder)holder).open_button.setVisibility(View.VISIBLE);
                ((questionViewHolder)holder).open_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!((questionViewHolder)holder).getQuestion_open()){

                            ((questionViewHolder)holder).gradeLayout.setVisibility(View.VISIBLE);
                            ((questionViewHolder)holder).other_remarks_layout.setVisibility(View.VISIBLE);
                            ((questionViewHolder)holder).archive_assessment.setVisibility(View.VISIBLE);
                            ((questionViewHolder)holder).setQuestion_open(true);
                            ((questionViewHolder)holder).open_button.setText("Close");
                            ((questionViewHolder)holder).open_button.setBackgroundColor(context.getResources().getColor(R.color.design_default_color_primary_dark));

                        }else{
                            ((questionViewHolder)holder).gradeLayout.setVisibility(View.GONE);
                            ((questionViewHolder)holder).other_remarks_layout.setVisibility(View.GONE);
                            ((questionViewHolder)holder).archive_assessment.setVisibility(View.GONE);
                            ((questionViewHolder)holder).setQuestion_open(false);
                            ((questionViewHolder)holder).open_button.setText("Open");
                            ((questionViewHolder)holder).open_button.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

                        }
                    }
                });
            }

            ((questionViewHolder)holder).archive_assessment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String grade = ((questionViewHolder)holder).grade_spinner.getSelectedItem().toString();
                    String other_remarks = ((questionViewHolder)holder).otherRemarks.getText().toString();
                    int assessment_group_id = AssessementActivity.assessmentGroup.getId();
                    int question_id = question.getQuestion_id();

                    Assessment assessment = new Assessment(assessment_group_id,question_id,grade,other_remarks);
                    Toast.makeText(context, "Assessment archived", Toast.LENGTH_SHORT).show();
                    ((questionViewHolder)holder).archive_assessment.setText("Archived");
                    ((questionViewHolder)holder).archive_assessment.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

                }
            });

        }

    }

    @Override
    public int getItemCount() {

        return questionArrayList!=null?questionArrayList.size():0;

    }


    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public void setQuestionArrayList(ArrayList<Question> questionArrayList) {
        this.questionArrayList = questionArrayList;
        this.notifyDataSetChanged();
    }

    public void addItemToArrayList(Question question){

        if (getQuestionArrayList()!=null){

            setQuestionArrayList(new ArrayList<Question>());
        }
        getQuestionArrayList().add(question);
        this.notifyDataSetChanged();
    }

    private class questionViewHolder extends RecyclerView.ViewHolder{

        TextView questionView;
        TextView open_button,archive_assessment;
        Spinner grade_spinner;
        EditText otherRemarks;
        LinearLayout gradeLayout, other_remarks_layout;
        Boolean question_open = false;

        public questionViewHolder(@NonNull View view) {

            super(view);

            questionView = (TextView)view.findViewById(R.id.full_question_display);
            open_button = (TextView) view.findViewById(R.id.open_question);
            grade_spinner = (Spinner)view.findViewById(R.id.grade_spinner);
            otherRemarks = (EditText)view.findViewById(R.id.other_remarks);
            gradeLayout = (LinearLayout)view.findViewById(R.id.grade_layout);
            other_remarks_layout = (LinearLayout)view.findViewById(R.id.other_remarks_layout);
            archive_assessment =  (TextView)view.findViewById(R.id.archive_assessment);

        }

        public Boolean getQuestion_open() {
            return question_open;
        }

        public void setQuestion_open(Boolean question_open) {
            this.question_open = question_open;
        }

    }



}
