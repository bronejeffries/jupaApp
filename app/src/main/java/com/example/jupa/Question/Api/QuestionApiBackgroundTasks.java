package com.example.jupa.Question.Api;

import android.content.Context;

import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.RetrofitSingleton;
import com.example.jupa.Question.Api.Question.QuestionApiData;
import com.example.jupa.Question.Api.Question.QuestionsApiInterface;
import com.example.jupa.Question.Api.Question.QuestionsListApiData;
import com.example.jupa.Question.Api.QuestionCategory.QuestionCategoriesListApiData;
import com.example.jupa.Question.Api.QuestionCategory.QuestionCategoryApiData;
import com.example.jupa.Question.Question;
import com.example.jupa.Question.QuestionCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionApiBackgroundTasks {

    Context context;
    Question question;
    QuestionCategory questionCategory;
    ArrayList<Question> questionArrayList;
    ArrayList<QuestionCategory> questionCategoryArrayList;
    static QuestionApiBackgroundTasks Instance = null;
    public String SUCCESS = "1", FAILED = "0",message;
    static QuestionsApiInterface questionspiInterface = RetrofitSingleton.getRetrofitInstance().create(QuestionsApiInterface.class);


    public QuestionApiBackgroundTasks(Context context) {
        this.context = context;
    }

    public static QuestionApiBackgroundTasks getInstance(Context context) {

        if (Instance == null && context!=null){
            Instance = new QuestionApiBackgroundTasks(context);
        }

        return Instance;
    }

    public void createQuestionCategory(QuestionCategory questionCategory){

        Call<QuestionCategoryApiData> call = questionspiInterface.addQuestionCategory(questionCategory.getId());
        call.enqueue(new Callback<QuestionCategoryApiData>() {
            @Override
            public void onResponse(Call<QuestionCategoryApiData> call, Response<QuestionCategoryApiData> response) {

                synchronized (QuestionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setQuestionCategory(response.body().getQuestionCategory());
                    }else {

                        setQuestionCategory(null);
                    }
                    setMessage(response.body().getMessage());
                    QuestionApiBackgroundTasks.this.notifyAll();
                }


            }

            @Override
            public void onFailure(Call<QuestionCategoryApiData> call, Throwable t) {

                synchronized (QuestionApiBackgroundTasks.this){

                    setMessage(t.getMessage());
                    setQuestionCategory(null);
                    QuestionApiBackgroundTasks.this.notifyAll();
                }
            }
        });

    }



    public void createQuestion(Question question){

        Integer current_user_id = LoggedInUser.getInstance().getLoggedInCandidate().getId();
        Call<QuestionApiData> call = questionspiInterface.addQuestionToCategory(question.getCategory_id(),question.getTitle(),question.getQuestion_code(),current_user_id);

        call.enqueue(new Callback<QuestionApiData>() {
            @Override
            public void onResponse(Call<QuestionApiData> call, Response<QuestionApiData> response) {

                synchronized (QuestionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setQuestion(response.body().getQuestion());

                    }else {
                        setQuestion(null);
                    }

                    setMessage(response.body().getMessage());
                    QuestionApiBackgroundTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<QuestionApiData> call, Throwable t) {

                synchronized (QuestionApiBackgroundTasks.this){

                    setMessage(t.getMessage());
                    setQuestion(null);
                    QuestionApiBackgroundTasks.this.notifyAll();
                }
            }
        });

    }


    public void getGroupQuestionCategories(int group_id){

        Call<QuestionCategoriesListApiData> call = questionspiInterface.getGroupQuestionCategories(group_id);
        call.enqueue(new Callback<QuestionCategoriesListApiData>() {
            @Override
            public void onResponse(Call<QuestionCategoriesListApiData> call, Response<QuestionCategoriesListApiData> response) {

                synchronized (QuestionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setQuestionCategoryArrayList(response.body().getQuestionCategoryArrayList());

                    }else {

                        setQuestionCategoryArrayList(null);

                    }
                    setMessage(response.body().getMessage());
                    QuestionApiBackgroundTasks.this.notifyAll();
                }


            }

            @Override
            public void onFailure(Call<QuestionCategoriesListApiData> call, Throwable t) {

                synchronized (QuestionApiBackgroundTasks.this){

                    setMessage(t.getMessage());
                    setQuestionCategoryArrayList(null);
                    QuestionApiBackgroundTasks.this.notifyAll();
                }
            }
        });

    }


    public void getCategoryQuestions(int category_id){

        Call<QuestionsListApiData> call = questionspiInterface.getCategoryQuestions(category_id);

        call.enqueue(new Callback<QuestionsListApiData>() {
            @Override
            public void onResponse(Call<QuestionsListApiData> call, Response<QuestionsListApiData> response) {

                synchronized (QuestionApiBackgroundTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setQuestionArrayList(response.body().getQuestionArrayList());

                    }else {

                        setQuestionArrayList(null);
                    }

                    setMessage(response.body().getMessage());
                    QuestionApiBackgroundTasks.this.notifyAll();

                }


            }

            @Override
            public void onFailure(Call<QuestionsListApiData> call, Throwable t) {

                synchronized (QuestionApiBackgroundTasks.this){

                    setMessage(t.getMessage());
                    setQuestionArrayList(null);
                    QuestionApiBackgroundTasks.this.notifyAll();
                }
            }
        });

    }



    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public void setQuestionArrayList(ArrayList<Question> questionArrayList) {
        this.questionArrayList = questionArrayList;
    }

    public ArrayList<QuestionCategory> getQuestionCategoryArrayList() {
        return questionCategoryArrayList;
    }

    public void setQuestionCategoryArrayList(ArrayList<QuestionCategory> questionCategoryArrayList) {
        this.questionCategoryArrayList = questionCategoryArrayList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
