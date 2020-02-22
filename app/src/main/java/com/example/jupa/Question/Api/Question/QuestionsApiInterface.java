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
    Call<QuestionApiData> addQuestionToCategory(@Field("assessment_cat_id") int assessment_cat_id, @Field("question_description") String question_description,
                                                @Field("unique_no") String unique_no, @Field("user_id") int user_id);


    @GET("get_qtn_subcat_ingroup.php")
    Call<QuestionCategoriesListApiData> getGroupQuestionCategories(@Query("group_id") int group_id);


    @FormUrlEncoded
    @POST("add_qtn_category.php")
    Call<QuestionCategoryApiData> addQuestionCategory(@Field("group_id") int category_id, @Field("question_sub_cat_name") String category_name ,@Field("user_id") int User_id);

    @GET("get_qtn_byid.php")
    Call<QuestionApiData> getQuestion(@Query("question_id") int question_id );

    @FormUrlEncoded
    @POST("update_qtn_category.php")
    Call<QuestionCategoryApiData> updatequestioncategory(@Field("question_cat_id")Integer id,@Field("question_category_name") String category_name);

    @FormUrlEncoded
    @POST("delete_qtn_category.php")
    Call<QuestionCategoryApiData> deletequestioncategory(@Field("question_cat_id")Integer id);


}
