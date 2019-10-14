package com.example.jupa.Question.Adapter.Categories;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.AssessmentActivity;
import com.example.jupa.Activity.Assessment_reportActivity;
import com.example.jupa.Question.Adapter.Questions.QuestionsAdapter;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.Question;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter {

    boolean assessment = true;
    boolean report_review = false;
    private static final int REPORT = 2, ASSESSMENT = 1;
    ArrayList<QuestionCategory> questionCategoryArrayList;
    Context context;

    public CategoriesAdapter(boolean assessment, ArrayList<QuestionCategory> questionCategoryArrayList, Context context, Boolean report_review) {
        this.assessment = assessment;
        this.questionCategoryArrayList = questionCategoryArrayList;
        this.context = context;
        this.report_review = report_review;
    }

    @Override
    public int getItemViewType(int position) {

        if (report_review){

            return REPORT;

        }else {

            return ASSESSMENT;

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ASSESSMENT){

            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.questioncategoryview,parent,false);

            return new questionCategoryViewHoler(relativeLayout,context);

        } else if (viewType == REPORT) {

            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.reportquestion_category_view,parent,false);

            return new ReportCategoryViewHolder(relativeLayout,context);

        }else {

            return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {


        if (getItemCount()>0 && holder!=null){

            switch (holder.getItemViewType()){

                case ASSESSMENT:
                    final QuestionCategory questionCategory = questionCategoryArrayList.get(position);
                    ((questionCategoryViewHoler)holder).category_view.setText(questionCategory.getName());
                    ((questionCategoryViewHoler)holder).category_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!((questionCategoryViewHoler)holder).getRecyclerViewOpen()){

                                ((questionCategoryViewHoler)holder).category_question_recycler_view.setVisibility(View.VISIBLE);
                                ((questionCategoryViewHoler)holder).setRecyclerViewOpen(true);
                                ((questionCategoryViewHoler)holder).populate_recyclerview(questionCategory,isAssessment(),isReport_review());

                            }else {

                                ((questionCategoryViewHoler)holder).category_question_recycler_view.setVisibility(View.GONE);
                                ((questionCategoryViewHoler)holder).setRecyclerViewOpen(false);

                            }


                        }
                    });
                    break;

                case REPORT:
                    final QuestionCategory ReportQuestionCategory = questionCategoryArrayList.get(position);
                    ((ReportCategoryViewHolder)holder).category_view.setText(ReportQuestionCategory.getName());
                    ((ReportCategoryViewHolder)holder).category_question_recycler_view.setVisibility(View.VISIBLE);
                    ((ReportCategoryViewHolder)holder).populate_recyclerview(ReportQuestionCategory,isAssessment(),isReport_review());
                    break;

                default:
                    return;

            }

        }



    }

    @Override
    public int getItemCount() {

        return questionCategoryArrayList !=null?questionCategoryArrayList.size():0;

    }


    public boolean isAssessment() {
        return assessment;
    }

    public void setAssessment(boolean assessment) {
        this.assessment = assessment;
    }

    public ArrayList<QuestionCategory> getQuestionCategoryArrayList() {
        return questionCategoryArrayList;
    }

    public void setQuestionCategoryArrayList(ArrayList<QuestionCategory> questionCategoryArrayList) {
        this.questionCategoryArrayList = questionCategoryArrayList;
        this.notifyDataSetChanged();
    }

    public void addItem(QuestionCategory questionCategory){

        if (getQuestionCategoryArrayList()==null){

            setQuestionCategoryArrayList(new ArrayList<QuestionCategory>());
        }

        getQuestionCategoryArrayList().add(questionCategory);
        this.notifyDataSetChanged();

    }

    private static class questionCategoryViewHoler extends RecyclerView.ViewHolder  {


        TextView category_view;
        RecyclerView category_question_recycler_view;
        ProgressBar category_progress;
        Context context;
        Boolean recyclerViewOpen = false;
        QuestionApiBackgroundTasks questionApiBackgroundTasks;
        private QuestionsAdapter questionsAdapter;


        public questionCategoryViewHoler(View view, Context context) {

            super(view);

            this.context = context;
            category_progress = (ProgressBar)view.findViewById(R.id.category_progressLoader);
            category_view = (TextView)view.findViewById(R.id.categoryView);
            category_question_recycler_view = (RecyclerView)view.findViewById(R.id.category_questions_recycler_view);
            questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(context);

        }


        public void populate_recyclerview(QuestionCategory questionCategory, Boolean assessment, Boolean reportReview){

            category_progress.setVisibility(View.VISIBLE);
            questionsAdapter = new QuestionsAdapter(null,context,assessment, reportReview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

            category_question_recycler_view.setLayoutManager(linearLayoutManager);
            category_question_recycler_view.setAdapter(questionsAdapter);
            new fetchQuestionsInCategory().execute(questionCategory.getId());

        }

        public ArrayList<Question> cleanArrayListForAssessment(ArrayList<Question> questions){

            ArrayList<Question> cleanedQuestions = new ArrayList<>();

            if (questions!=null ){

                for (Question question: questions ) {

                    if (!AssessmentActivity.assessmentLinkedHashMap.containsKey(question.getQuestion_id())){

                        cleanedQuestions.add(question);

                    }

                }

            }

            return cleanedQuestions;

        }



        private class fetchQuestionsInCategory extends AsyncTask<Integer,Void,ArrayList<Question>>{


            @Override
            protected void onPostExecute(ArrayList<Question> questions) {

                if (questions!=null){

                    ArrayList<Question> newList = cleanArrayListForAssessment(questions);
                    questionsAdapter.setQuestionArrayList(newList);

                }else {

                    Toast.makeText(context, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();

                }
                category_progress.setVisibility(View.GONE);

            }


            @Override
            protected ArrayList<Question> doInBackground(Integer... integers) {

                ArrayList<Question> returnedQuestions;

                synchronized (questionApiBackgroundTasks){

                    questionApiBackgroundTasks.getCategoryQuestions(integers[0]);

                    try {
                        questionApiBackgroundTasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    returnedQuestions = questionApiBackgroundTasks.getQuestionArrayList();

                }



                return returnedQuestions;
            }
        }


        public Boolean getRecyclerViewOpen() {
            return recyclerViewOpen;
        }

        public void setRecyclerViewOpen(Boolean recyclerViewOpen) {
            this.recyclerViewOpen = recyclerViewOpen;
        }
    }


    private class ReportCategoryViewHolder extends RecyclerView.ViewHolder{

        TextView category_view;
        RecyclerView category_question_recycler_view;
        ProgressBar category_progress;
        Context context;
        QuestionApiBackgroundTasks questionApiBackgroundTasks;
        private QuestionsAdapter questionsAdapter;

        public ReportCategoryViewHolder(@NonNull View itemView, Context context) {

            super(itemView);
            this.context = context;
            category_progress = (ProgressBar)itemView.findViewById(R.id.category_progressLoader);
            category_view = (TextView)itemView.findViewById(R.id.categoryView);
            category_question_recycler_view = (RecyclerView)itemView.findViewById(R.id.category_questions_recycler_view);
            questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(context);
        }

        public void populate_recyclerview(QuestionCategory questionCategory, Boolean assessment, Boolean reportReview){

            category_progress.setVisibility(View.VISIBLE);
            questionsAdapter = new QuestionsAdapter(null,context,assessment, reportReview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            category_question_recycler_view.setLayoutManager(linearLayoutManager);
            category_question_recycler_view.setAdapter(questionsAdapter);
            new fetchQuestionsInCategory().execute(questionCategory.getId());

        }


        public ArrayList<Question> cleanArrayListForReport(ArrayList<Question> questions){

            ArrayList<Question> cleanedQuestions = new ArrayList<>();

            if (questions!=null ){

                for (Question question: questions ) {

                    if (Assessment_reportActivity.assessmentLinkedHashMap.containsKey(question.getQuestion_id())){

                        cleanedQuestions.add(question);

                    }

                }

            }

            return cleanedQuestions;

        }

        private class fetchQuestionsInCategory extends AsyncTask<Integer,Void, ArrayList<Question>> {


            @Override
            protected void onPostExecute(ArrayList<Question> questions) {

                if (questions!=null){
                    ArrayList<Question> newReportList = cleanArrayListForReport(questions);
                    questionsAdapter.setQuestionArrayList(newReportList);
                }else {

                    Toast.makeText(context, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();

                }
                category_progress.setVisibility(View.GONE);

            }



            @Override
            protected ArrayList<Question> doInBackground(Integer... integers) {

                ArrayList<Question> returnedQuestions;

                synchronized (questionApiBackgroundTasks){

                    questionApiBackgroundTasks.getCategoryQuestions(integers[0]);

                    try {
                        questionApiBackgroundTasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    returnedQuestions = questionApiBackgroundTasks.getQuestionArrayList();

                }



                return returnedQuestions;
            }
        }



    }



    public boolean isReport_review() {
        return report_review;
    }

    public void setReport_review(boolean report_review) {
        this.report_review = report_review;
    }
}
