package com.example.jupa.Question.Adapter.Categories;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.AssessmentActivity;
import com.example.jupa.Activity.Assessment_reportActivity;
import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.Helpers.showProgressbar;
import com.example.jupa.Question.Adapter.Questions.QuestionsAdapter;
import com.example.jupa.Question.Api.QuestionApiBackgroundTasks;
import com.example.jupa.Question.Question;
import com.example.jupa.Question.QuestionCategory;
import com.example.jupa.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CategoriesAdapter extends RecyclerView.Adapter {

    boolean assessment = true;
    boolean report_review = false;
    boolean questionsView;

    private static final int REPORT = 2, ASSESSMENT = 1;
    ArrayList<QuestionCategory> questionCategoryArrayList;
    Context context;
    public static LinkedHashMap<String,ArrayList<QuestionsAdapter.QuestionGrade>> listLinkedHashMap;

    public CategoriesAdapter(boolean assessment, ArrayList<QuestionCategory> questionCategoryArrayList, Context context, Boolean report_review) {
        this.assessment = assessment;
        this.questionCategoryArrayList = questionCategoryArrayList;
        this.context = context;
        this.report_review = report_review;
    }

    @Override
    public int getItemViewType(int position) {

        if (assessment||isQuestionsView()){

            return ASSESSMENT;

        }else {

            return REPORT;

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
                    ((questionCategoryViewHoler)holder).bind(questionCategory,position);
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

    public void removeItem(Integer position){
        if(getQuestionCategoryArrayList()!=null){
            getQuestionCategoryArrayList().remove(position);
            this.notifyItemRemoved(position);
            this.notifyDataSetChanged();
        }
    }

    public void changeItemAtPosition(Integer position,QuestionCategory updatedquestionCategory){

        if (getQuestionCategoryArrayList()!=null){
            getQuestionCategoryArrayList().set(position,updatedquestionCategory);
            this.notifyItemChanged(position,updatedquestionCategory);
            this.notifyItemChanged(position);
            this.notifyDataSetChanged();
        }

    }

    private class questionCategoryViewHoler extends RecyclerView.ViewHolder  {

        EditText titleEditText,codeEditText;
        TextView category_view,addQuestion,category_options;
        RecyclerView category_question_recycler_view;
        ProgressBar category_progress;
        Context context;
        QuestionCategory questionCategory;
        Boolean recyclerViewOpen = false;
        QuestionApiBackgroundTasks questionApiBackgroundTasks;
        private QuestionsAdapter questionsAdapter;
        showProgressbar showProgress;
        AlertDialog addQuestionDialog;
        Integer position;


        public questionCategoryViewHoler(View view, Context context) {

            super(view);
            this.context = context;
            category_progress = (ProgressBar)view.findViewById(R.id.category_progressLoader);
            category_view = (TextView)view.findViewById(R.id.categoryView);
            category_question_recycler_view = (RecyclerView)view.findViewById(R.id.category_questions_recycler_view);
            questionApiBackgroundTasks = QuestionApiBackgroundTasks.getInstance(context);
            addQuestion = (TextView)view.findViewById(R.id.add_question);
            category_options = (TextView)view.findViewById(R.id.category_options);
            showProgress = new showProgressbar(this.context);

        }

        public void addNewQuestionToCategory(Question question){

            showProgress.setMessage("Adding a new Question...");
            showProgress.show();
            new addCategoryQuestion().execute(question);

        }

        private void AddNewQuestion() {

            AlertDialog.Builder addNewQuestionDialog = new AlertDialog.Builder(context);
            LinearLayout addQuestionLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.addnewquestiondialog,null,false);
            titleEditText =  (EditText) addQuestionLinearLayout.findViewById(R.id.question_title_input);
            codeEditText = (EditText)addQuestionLinearLayout.findViewById(R.id.question_code_input);
            addQuestionLinearLayout.findViewById(R.id.save_question_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Question question = new Question(LoggedInUser.getInstance().getLoggedInCandidate().getId(),questionCategory.getId(),
                                                     titleEditText.getText().toString(),codeEditText.getText().toString());

                    addNewQuestionToCategory(question);

                }
            });
            addQuestionLinearLayout.findViewById(R.id.cancel_action).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addQuestionDialog.dismiss();
                }
            });
            addNewQuestionDialog.setView(addQuestionLinearLayout);
            addNewQuestionDialog.setTitle("Add New Question");
            addQuestionDialog = addNewQuestionDialog.create();
            addQuestionDialog.setCanceledOnTouchOutside(false);
            addQuestionDialog.show();

        }

        public void bind(final QuestionCategory questionCategory,Integer position){

            this.position = position;
            this.questionCategory = questionCategory;
            category_view.setText(questionCategory.getName());
            category_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!getRecyclerViewOpen()){

                        category_question_recycler_view.setVisibility(View.VISIBLE);
                        if (isQuestionsView()){
                            addQuestion.setVisibility(View.VISIBLE);
                            category_options.setVisibility(View.VISIBLE);
                        }
                        setRecyclerViewOpen(true);
                        populate_recyclerview(questionCategory,isAssessment(),isReport_review());

                    }else {

                        category_question_recycler_view.setVisibility(View.GONE);
                        addQuestion.setVisibility(View.GONE);
                        category_options.setVisibility(View.GONE);
                        setRecyclerViewOpen(false);

                    }


                }
            });

            if (isQuestionsView()){

                addQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddNewQuestion();
                    }
                });

                category_options.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] items = {"Edit Category","Delete Category"};
                        AlertDialog.Builder optionsDialog = new AlertDialog.Builder(context);
                        optionsDialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                switch (i){
                                    case 0:
                                        showEditDialog(questionCategory);
                                        break;
                                    case 1:
                                        showConfirmDeleteDialog(questionCategory);
                                        break;
                                }

                            }
                        });

                        optionsDialog.create().show();
                    }
                });

            }

        }

        private void showConfirmDeleteDialog(final QuestionCategory questionCategory) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setTitle("Delete Question Category");
            alertBuilder.setMessage("All questions associated to this category will be lost!\n Do you wish to continue");
            alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    showProgress.setMessage("Deleting");
                    showProgress.show();
                    new deleteQuestionCategory().execute(questionCategory);

                }
            });

            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertBuilder.create().show();

        }

        private void showEditDialog(final QuestionCategory questionCategory) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setTitle("Edit Question Category");
            final EditText questionEditText = new EditText(context);
            questionEditText.setText(questionCategory.getName());
            alertBuilder.setView(questionEditText);
            alertBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                        showProgress.setMessage("Update Question Category");
                        showProgress.show();
                        questionCategory.setName(questionEditText.getText().toString());
                        new editQuestionCategory().execute(questionCategory);

                }
            });
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                }
            });
            alertBuilder.create().show();
        }

        public void populate_recyclerview(QuestionCategory questionCategory, Boolean assessment, Boolean reportReview){

            category_progress.setVisibility(View.VISIBLE);
            questionsAdapter = new QuestionsAdapter(null,context,assessment, reportReview);
            questionsAdapter.setQuestionView(isQuestionsView());
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

                    if(!isQuestionsView()){

                        ArrayList<Question> newList = cleanArrayListForAssessment(questions);
                        questionsAdapter.setQuestionArrayList(newList);


                    }else {

                        questionsAdapter.setQuestionArrayList(questions);

                    }

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

        private class addCategoryQuestion extends AsyncTask<Question,Void,Question>{

            @Override
            protected void onPostExecute(Question question) {

                if (question!=null){

                    questionsAdapter.addItemToArrayList(question);
                    codeEditText.setText("");
                    titleEditText.setText("");

                }
                    Toast.makeText(context, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
                    showProgress.dismiss();

            }

            @Override
            protected Question doInBackground(Question... questions) {

                Question returnedQuestion;

                synchronized (questionApiBackgroundTasks){

                    questionApiBackgroundTasks.createQuestion(questions[0]);

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

        private class editQuestionCategory extends AsyncTask<QuestionCategory,Void,QuestionCategory>{

            @Override
            protected void onPostExecute(QuestionCategory result) {

                if (result!=null){

                    changeItemAtPosition(position,result);

                }
                Toast.makeText(context, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress.dismiss();
            }
            @Override
            protected QuestionCategory doInBackground(QuestionCategory... questionCategories) {
                QuestionCategory returnedQuestionCategory;

                synchronized (questionApiBackgroundTasks){

                    questionApiBackgroundTasks.updateQuestionCategory(questionCategories[0]);

                    try {
                        questionApiBackgroundTasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    returnedQuestionCategory = questionApiBackgroundTasks.getQuestionCategory();

                }

                return returnedQuestionCategory;
            }
        }

        private class deleteQuestionCategory extends AsyncTask<QuestionCategory,Void,Boolean>{

            @Override
            protected void onPostExecute(Boolean result) {
                if (result){

                    removeItem(position);

                }
                Toast.makeText(context, questionApiBackgroundTasks.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress.dismiss();
            }
            @Override
            protected Boolean doInBackground(QuestionCategory... questionCategories) {

                Boolean result;

                synchronized (questionApiBackgroundTasks){

                    questionApiBackgroundTasks.deleteQuestionCategory(questionCategories[0]);

                    try {
                        questionApiBackgroundTasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    result = questionApiBackgroundTasks.getSuccess();

                }



                return result;
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
        QuestionCategory questionCategory;
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
            this.questionCategory = questionCategory;
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
                    questionsAdapter.setQuestionArrayList(newReportList,questionCategory.getName());

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

    public boolean isQuestionsView() {
        return questionsView;
    }

    public void setQuestionsView(boolean questionsView) {
        this.questionsView = questionsView;
    }

    public static LinkedHashMap<String, ArrayList<QuestionsAdapter.QuestionGrade>> getListLinkedHashMap() {
        return listLinkedHashMap!=null?listLinkedHashMap:(new LinkedHashMap<String, ArrayList<QuestionsAdapter.QuestionGrade>>());
    }

    public static void setListLinkedHashMap(LinkedHashMap<String, ArrayList<QuestionsAdapter.QuestionGrade>> listLinkedHashMap) {
        CategoriesAdapter.listLinkedHashMap = listLinkedHashMap;
    }
}
