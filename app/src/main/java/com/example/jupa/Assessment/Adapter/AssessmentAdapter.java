package com.example.jupa.Assessment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.InstitutionResultsActivity;
import com.example.jupa.Activity.ResultSearchActivity;
import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.Question;
import com.example.jupa.R;

import java.util.ArrayList;

public class AssessmentAdapter extends RecyclerView.Adapter {

    ArrayList<Assessment> assessmentArrayList;

    Context context;

    public AssessmentAdapter(ArrayList<Assessment> assessmentArrayList, Context context) {

        this.assessmentArrayList = assessmentArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.institution_assessment_view,parent,false);
        return new assessmentViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0){
            ((assessmentViewHolder)holder).bind(getAssessmentArrayList().get(position),position);
        }

    }

    @Override
    public int getItemCount() {

        return getAssessmentArrayList()!=null?getAssessmentArrayList().size():0;

    }

    private class assessmentViewHolder extends RecyclerView.ViewHolder{
        showProgressbar showProgress;
        Boolean content_loading;
        Assessment thisAssessment;
        ImageView imageView;
        TextView questionView, candidate_name, candidate_regNo, remarks, remarksIntroView;
        EditText comment;
        ImageButton marks;
        Button close_submit;
        CandidateBackgroundApiTasks candidateBackgroundApiTasks;
        QuestionApiBackgroundTasks questionApiBackgroundTasks;
        Candidate assessment_owner;
        Question thisQuestion;
        int position, searchAdapterPosition;
        boolean opened, fromSearch;

        public assessmentViewHolder(@NonNull View itemView) {

            super(itemView);
            showProgress = new showProgressbar(context);
            imageView = (ImageView)itemView.findViewById(R.id.pic_view);
            questionView = (TextView)itemView.findViewById(R.id.question);
            marks = (ImageButton)itemView.findViewById(R.id.marks);
            candidateBackgroundApiTasks = CandidateBackgroundApiTasks.getInstance(context);
            questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(context);
            candidate_name = (TextView)itemView.findViewById(R.id.candidate_name);
            candidate_regNo = (TextView)itemView.findViewById(R.id.candidate_regNo);
            remarks = (TextView)itemView.findViewById(R.id.assessment_remarks);
            remarksIntroView = (TextView)itemView.findViewById(R.id.remarks_intro);
            comment = (EditText)itemView.findViewById(R.id.Institution_comment);
            close_submit = (Button)itemView.findViewById(R.id.close_gap_submit);
        }

        public Candidate getAssessment_owner() {
            return assessment_owner;
        }


        public void setAssessment_owner(Candidate assessment_owner) {

            this.assessment_owner = assessment_owner;
            thisAssessment.setCandidate(getAssessment_owner());
        }


        public Question getThisQuestion() {
            return thisQuestion;
        }


        public void setThisQuestion(Question thisQuestion) {
            this.thisQuestion = thisQuestion;
            thisAssessment.setQuestion(getThisQuestion());
            setQuestionContent();
            setCandidate_Content();
        }

        public void setCandidate_Content(){

            candidate_name.setText(getAssessment_owner().getName());
            candidate_name.setBackgroundColor(context.getResources().getColor(R.color.white));
            candidate_regNo.setText(getAssessment_owner().getRegistration_no());
            candidate_regNo.setBackgroundColor(context.getResources().getColor(R.color.white));

        }

        public void setQuestionContent(){

            questionView.setText(getThisQuestion().getTitle());
            questionView.setBackgroundColor(context.getResources().getColor(R.color.white));
            setMarksImage(marks,thisAssessment.getGrade());
            marks.setBackgroundColor(context.getResources().getColor(R.color.white));
            ((CardView)itemView).setForeground(null);

        }

        private void setMarksImage(ImageButton marks, String grade) {

            if (Integer.parseInt(grade)>context.getResources().getInteger(R.integer.passmark)){

                marks.setImageResource(R.drawable.ic_check_black_24dp);

            }else {

                marks.setImageResource(R.drawable.ic_close_black_24dp);
                marks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showCloseDialog();
                    }
                });

                close_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!comment.getText().toString().isEmpty()){

                            thisAssessment.setInstitute_remarks(comment.getText().toString());
                            showProgress.setMessage("a moment..");
                            showProgress.show();
                            new closeGap().execute(thisAssessment);

                        }else {
                            comment.setError("Comment required!");
                            return;
                        }

                    }
                });

            }
        }

        private class closeGap extends AsyncTask<Assessment,Void,Assessment>{

            @Override
            protected void onPostExecute(Assessment assessment) {

                Toast.makeText(context, candidateBackgroundApiTasks.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress.dismiss();

                if (assessment!=null){

                    thisAssessment.setGrade(assessment.getGrade());
                    if (fromSearch){

                        updateItemAtPosition(searchAdapterPosition,thisAssessment);
                        InstitutionResultsActivity.assessmentAdapter.notifyItemChanged(position,thisAssessment);

                    }else{

                        if (context instanceof ResultSearchActivity){

                            updateItemAtPosition(position,thisAssessment);
                            InstitutionResultsActivity.assessmentAdapter.notifyItemChanged(position,thisAssessment);

                        }else if(context instanceof InstitutionResultsActivity ){
                            updateItemAtPosition(position,thisAssessment);
                        }

                    }

                    Toast.makeText(context, assessment.getGrade(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected Assessment doInBackground(Assessment... assessments) {

                Assessment assessment;

                synchronized (candidateBackgroundApiTasks){

                    candidateBackgroundApiTasks.closeAssessmentGap(assessments[0]);

                    try {

                        candidateBackgroundApiTasks.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

                    assessment = candidateBackgroundApiTasks.getAssessment();

                }

                return assessment;

            }
        }

        public void showCloseDialog(){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            String[] optionsArray = {"Close Gap","Cancel"};
            alertDialogBuilder.setItems(optionsArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    switch (i){

                        case 0:
                            comment.setVisibility(View.VISIBLE);
                            close_submit.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            dialogInterface.dismiss();
                            break;
                    }

                }
            });

            alertDialogBuilder.create().show();

        }

        public void setDarkTheme(){

            ((CardView)itemView).setForeground(context.getResources().getDrawable(R.drawable.light_dark_foreground));
            questionView.setBackground(context.getResources().getDrawable(R.drawable.data_loading_background));
            candidate_name.setBackground(context.getResources().getDrawable(R.drawable.data_loading_background));
            candidate_regNo.setBackground(context.getResources().getDrawable(R.drawable.data_loading_background));
            marks.setBackground(context.getResources().getDrawable(R.drawable.data_loading_background));

        }

        public void bind(Assessment assessment, Integer position){

            thisAssessment = assessment;

            if (thisAssessment.getObject_position()!=null){

                this.position = thisAssessment.getObject_position();
                this.searchAdapterPosition = position;
                this.fromSearch = true;

            }else {

                this.fromSearch = false;
                this.position = position;

            }

            remarks.setText(thisAssessment.getOther_remarks());
            if (thisAssessment.getQuestion()==null||thisAssessment.getCandidate()==null){

                setDarkTheme();
                new getCandidateDetails().execute(thisAssessment.getCandidate_id());
                new getQuestion().execute(thisAssessment.getQuestion_id());

            }else {

                setContentNormal();

            }

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!opened){

                        remarksIntroView.setVisibility(View.VISIBLE);
                        remarks.setVisibility(View.VISIBLE);
                        opened = true;

                    }else {

                        remarksIntroView.setVisibility(View.GONE);
                        remarks.setVisibility(View.GONE);
                        opened = false;

                    }

                    return true;

                }
            });

        }

        private void setContentNormal() {

            questionView.setText(thisAssessment.getQuestion().getTitle());
            setMarksImage(marks,thisAssessment.getGrade());
            candidate_name.setText(thisAssessment.getCandidate().getName());
            candidate_regNo.setText(thisAssessment.getCandidate().getRegistration_no());

        }

        private class getQuestion extends AsyncTask<Integer, Void, Question>{

            @Override
            protected void onPostExecute(Question question) {

                if (question!=null){

                    setThisQuestion(question);
                }

            }

            @Override
            protected Question doInBackground(Integer... integers) {

                Question returnedQuestion;

                synchronized (questionApiBackgroundTasks){

                    questionApiBackgroundTasks.getQuestionById(integers[0]);
                    try {
                        questionApiBackgroundTasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    returnedQuestion = questionApiBackgroundTasks.getQuestion();
                }

                return returnedQuestion;

            }

        }

        private class getCandidateDetails extends AsyncTask<Integer,Void,Candidate>{

            @Override
            protected void onPostExecute(Candidate candidate) {

                if (candidate!=null){
                    setAssessment_owner(candidate);
                }

            }

            @Override
            protected Candidate doInBackground(Integer... integers) {

                Candidate returnedCandidate;

                synchronized (candidateBackgroundApiTasks){

                    candidateBackgroundApiTasks.GetCandidateById(integers[0]);
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

    }


    public ArrayList<Assessment> getAssessmentArrayList() {

        return assessmentArrayList;

    }

    public void setAssessmentArrayList(ArrayList<Assessment> assessmentArrayList) {

        this.assessmentArrayList = assessmentArrayList;
        this.notifyDataSetChanged();

    }

    public void updateItemAtPosition(int position, Assessment assessment){

        this.notifyItemChanged(position);

    }

}
