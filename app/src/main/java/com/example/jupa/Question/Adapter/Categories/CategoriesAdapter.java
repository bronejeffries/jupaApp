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

import com.example.jupa.Question.Adapter.Questions.QuestionsAdapter;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.Question;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter {

    boolean assessment = true;
    ArrayList<QuestionCategory> questionCategoryArrayList;
    Context context;

    public CategoriesAdapter(boolean assessment, ArrayList<QuestionCategory> questionCategoryArrayList, Context context) {
        this.assessment = assessment;
        this.questionCategoryArrayList = questionCategoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.questioncategoryview,parent,false);

        return new questionCategoryViewHoler(relativeLayout,context);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {


        if (getItemCount()>0){


            final QuestionCategory questionCategory = questionCategoryArrayList.get(position);
            ((questionCategoryViewHoler)holder).category_view.setText(questionCategory.getName());
            ((questionCategoryViewHoler)holder).category_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!((questionCategoryViewHoler)holder).getRecyclerViewOpen()){

                        ((questionCategoryViewHoler)holder).category_question_recycler_view.setVisibility(View.VISIBLE);
                        ((questionCategoryViewHoler)holder).setRecyclerViewOpen(true);
                        ((questionCategoryViewHoler)holder).populate_recyclerview(questionCategory,isAssessment());

                    }else {

                        ((questionCategoryViewHoler)holder).category_question_recycler_view.setVisibility(View.GONE);
                        ((questionCategoryViewHoler)holder).setRecyclerViewOpen(false);

                    }


                }
            });

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

    private static class questionCategoryViewHoler extends RecyclerView.ViewHolder{


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


        public void populate_recyclerview(QuestionCategory questionCategory, Boolean assessment){

            category_progress.setVisibility(View.VISIBLE);
            questionsAdapter = new QuestionsAdapter(null,context,assessment);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

            category_question_recycler_view.setLayoutManager(linearLayoutManager);
            category_question_recycler_view.setAdapter(questionsAdapter);
            new fetchQuestionsInCategory().execute(questionCategory.getId());

        }


        private class fetchQuestionsInCategory extends AsyncTask<Integer,Void,ArrayList<Question>>{


            @Override
            protected void onPostExecute(ArrayList<Question> questions) {

                if (questions!=null){
                    questionsAdapter.setQuestionArrayList(questions);
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


}
