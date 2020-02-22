package com.example.jupa.Question.Adapter.Questions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.AssessmentActivity;
import com.example.jupa.Activity.Assessment_reportActivity;
import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Assessment.Assessment;
import com.example.jupa.Question.Adapter.Categories.CategoriesAdapter;
import com.example.jupa.Question.Question;
import com.example.jupa.R;

import java.util.ArrayList;

import static com.example.jupa.Activity.AssessmentActivity.archivedAssessment;

public class QuestionsAdapter extends RecyclerView.Adapter {

    ArrayList<Question> questionArrayList;
    Context context;
    Boolean assessment,questionView;
    private boolean reportReview;
    private static final int REPORT_VIEW_TYPE = 2;
    private static final int ASSESSMENT_VIEW_TYPE = 1;
    private static final int QUESTION_VIEW_TYPE = 3;
    ArrayList<QuestionGrade> questionGradeArrayList;
    public static String this_questionCategory;

    public QuestionsAdapter(ArrayList<Question> questionArrayList, Context context, Boolean assessment,Boolean reportReview) {
        this.questionArrayList = questionArrayList;
        this.context = context;
        this.assessment = assessment;
        this.reportReview = reportReview;

    }

    @Override
    public int getItemViewType(int position) {


        if (this.reportReview){

            return REPORT_VIEW_TYPE;

        }else if(this.assessment) {

            return ASSESSMENT_VIEW_TYPE;

        }else if(this.getQuestionView()) {

            return QUESTION_VIEW_TYPE;

        }else{

            return 0;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == REPORT_VIEW_TYPE){

            LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.report_question_view,parent,false);
            return new reportQuestionViewHolder(linearLayout);

        }else if (viewType == ASSESSMENT_VIEW_TYPE ){

            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.questionviewlayout,parent,false);

            return new questionViewHolder(linearLayout);

        }else if (viewType == QUESTION_VIEW_TYPE ){

            CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.institution_item_view,parent,false);

            return new categoryQuestionViewHolder(cardView);

        }else {

            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0) {

            final Question question = questionArrayList.get(position);

            switch (holder.getItemViewType()){

                case REPORT_VIEW_TYPE:
                    setQuestionGradeArrayList(new ArrayList<QuestionGrade>());
                    if (Assessment_reportActivity.assessmentLinkedHashMap.containsKey(question.getQuestion_id())){

                        Assessment assessmentRetrieved = Assessment_reportActivity.assessmentLinkedHashMap.get(question.getQuestion_id());
                        ((reportQuestionViewHolder) holder).questionView.setText(question.getTitle());
                        ((reportQuestionViewHolder)holder).setMarksImage(assessmentRetrieved.getGrade());
                        QuestionGrade questionGrade = new QuestionGrade(question.getTitle(),assessmentRetrieved.getGrade());
                        questionGradeArrayList.add(questionGrade);
                        CategoriesAdapter.getListLinkedHashMap().put(this_questionCategory,questionGradeArrayList);
                    }
                    break;

                case ASSESSMENT_VIEW_TYPE:

                    if (!AssessmentActivity.assessmentLinkedHashMap.containsKey(question.getQuestion_id())){

                        ((questionViewHolder) holder).questionView.setText(question.getTitle());
                        if (UserHomeActivity.loggedInUserRole.equals(UserHomeActivity.ASSESSOR_ROLE)){

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

                            ((questionViewHolder)holder).archive_assessment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    int selected_grade_position = ((questionViewHolder)holder).grade_spinner.getSelectedItemPosition();
                                    String grade = AssessmentActivity.grade_array[selected_grade_position];
                                    String other_remarks = ((questionViewHolder)holder).otherRemarks.getText().toString();
                                    int assessed_candidate_id = AssessmentActivity.assessedCandidate.getId();
                                    int question_id = question.getQuestion_id();
                                    int assessor_id = UserHomeActivity.thisCandidate.getId();
                                    int institution_id = UserHomeActivity.thisCandidate.getInstitution_id();
                                    Assessment assessment = new Assessment(question_id,grade,other_remarks,assessed_candidate_id,assessor_id,institution_id);
                                    archivedAssessment.add(assessment);
                                    Toast.makeText(context, "Assessment archived "+grade, Toast.LENGTH_SHORT).show();
                                    ((questionViewHolder)holder).archive_assessment.setText("Archived");
                                    ((questionViewHolder)holder).archive_assessment.setEnabled(false);
                                    ((questionViewHolder)holder).archive_assessment.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

                                }
                            });

                        }
                    }
                    break;

                case QUESTION_VIEW_TYPE:

                    ((categoryQuestionViewHolder) holder).questionView.setText(question.getTitle());
                    break;

                default:
                    return;

            }

        }

    }

    @Override
    public int getItemCount() {

        return questionArrayList!=null?questionArrayList.size():0;

    }


    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public void setQuestionArrayList(ArrayList<Question> questionArrayList,String questionCategory) {
        this_questionCategory = questionCategory;
        this.questionArrayList = questionArrayList;
        this.notifyDataSetChanged();
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

    private class reportQuestionViewHolder extends RecyclerView.ViewHolder{

        TextView questionView;
        ImageButton marks_view;
        public reportQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionView = (TextView)itemView.findViewById(R.id.question);
            marks_view = (ImageButton) itemView.findViewById(R.id.marks);
        }

        private void setMarksImage(String grade) {

            if (Integer.parseInt(grade)>context.getResources().getInteger(R.integer.passmark)){

                marks_view.setImageResource(R.drawable.ic_check_black_24dp);

            }else {

                marks_view.setImageResource(R.drawable.ic_close_black_24dp);

            }

        }


    }

    private class categoryQuestionViewHolder extends RecyclerView.ViewHolder{

        TextView questionView;

        public categoryQuestionViewHolder(@NonNull View itemView) {

            super(itemView);
            questionView = (TextView)itemView.findViewById(R.id.institutions_name);

        }

    }

    private class questionViewHolder extends RecyclerView.ViewHolder{

        View holderview;
        TextView questionView;
        TextView open_button,archive_assessment;
        Spinner grade_spinner;
        EditText otherRemarks;
        LinearLayout gradeLayout, other_remarks_layout;
        Boolean question_open = false;

        public questionViewHolder(@NonNull View view) {

            super(view);
            holderview = view;
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

    public Boolean getQuestionView() {
        return questionView;
    }

    public void setQuestionView(Boolean questionView) {
        this.questionView = questionView;
    }

    public ArrayList<QuestionGrade> getQuestionGradeArrayList() {
        return questionGradeArrayList!=null?questionGradeArrayList:(new ArrayList<QuestionGrade>());
    }

    public void setQuestionGradeArrayList(ArrayList<QuestionGrade> questionGradeArrayList) {
        this.questionGradeArrayList = questionGradeArrayList;
    }

    public static class QuestionGrade{

        String question,grade;

        public QuestionGrade(String question, String grade) {
            this.question = question;
            this.grade = grade;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }


}
