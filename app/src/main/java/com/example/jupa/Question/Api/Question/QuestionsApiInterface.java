package com.example.jupa.Question.Api.Question;

import com.example.jupa.Question.Api.QuestionCategory.QuestionCategoriesListApiData;
import com.example.jupa.Question.Api.QuestionCategory.QuestionCategoryApiData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuestionsApiInterface {



    @GET("get_qtns_InSubcategory.php")
    Call<QuestionsListApiData> getCategoryQuestions(@Query("assessment_cat_id") int category_id );

    @FormUrlEncoded
    @POST("add_question.php")
    Call<QuestionApiData> addQuestionToCategory(@Field("category_id") int assessment_cat_id, @Field("question_description") String question_description,
                                                @Field("unique_no") String unique_no, @Field("user_id") int user_id);


    @GET("get_qtn_subcat_ingroup.php")
    Call<QuestionCategoriesListApiData> getGroupQuestionCategories(@Query("group_id") int group_id);


    @FormUrlEncoded
    @POST("add_qtn_category.php")
    Call<QuestionCategoryApiData> addQuestionCategory(@Field("group_id") int category_id);




}
