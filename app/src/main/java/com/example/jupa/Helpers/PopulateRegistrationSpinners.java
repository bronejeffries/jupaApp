package com.example.jupa.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Candidate.Api.CandidateBackgroundApiTasks;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Category.Api.CandidateCategoryBackgroundApiTasks;
import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Group.Adapter.GroupsAdapter;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.R;

import java.util.ArrayList;
import java.util.List;

public class PopulateRegistrationSpinners {

    Context context;
    public GroupsList groupsList;
    List<String> spinnerArray, categorySpinnerArray;
    GroupBackgroundApiTasks groupBackgroundApiTasks;
    CandidateCategoryBackgroundApiTasks candidateCategoryBackgroundApiTasks;
    CandidateBackgroundApiTasks candidateBackgroundApiTasks;
    ArrayList<Group> groupArrayList;
    Spinner groupSpinner,  category, genderSpinner, associationsSpinner;
    ArrayList<CandidateCategory> candidateCategoryArrayList;
    ArrayList<Candidate> candidateArrayList;
    showProgressbar showprogress;
    Button button;

    public PopulateRegistrationSpinners(Context context, @Nullable Spinner groupSpinner, @Nullable Spinner category, @Nullable Spinner genderSpinner) {
        this.context = context;
        this.groupSpinner = groupSpinner;
        this.category = category;
        this.genderSpinner = genderSpinner;
    }

    public void setShowProgress(showProgressbar showprogress) {
        this.showprogress = showprogress;
    }

    public void populateGroupSpinner() {

        groupsList = new GroupsList(context);
        spinnerArray = new ArrayList<>();

        GroupsAdapter current_adapter = GroupsAdapter.getInstance(null, null);

        if (current_adapter != null ){

            groupsList.makeListFrom(current_adapter.getGroupArrayList());
            spinnerArray = groupsList.getSpinnerArray();
            setGroupSpinner();

        }
        else {

            groupBackgroundApiTasks = new GroupBackgroundApiTasks(context);
            fetchAllGroups();

        }

    }

    public void setGroupSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
        fetchAllCategories();

    }

    public void populateAssociations(Spinner spinner,Integer group){
        associationsSpinner = spinner;
        groupsList = new GroupsList(context);
        candidateBackgroundApiTasks = new CandidateBackgroundApiTasks(context);
        fetchAssociations(UserHomeActivity.GROUP_ADMIN_ROLE,group);
    }

    private void fetchAssociations(String memberLevel,Integer group_id){
        new getAssociations().execute(memberLevel,String.valueOf(group_id));
    }

    private class getAssociations extends AsyncTask<String,Void,ArrayList<Candidate>>{


        @Override
        protected void onPostExecute(ArrayList<Candidate> candidates) {

            if (candidates==null){

                setButtonReload();

            }
            candidateArrayList = candidates;
            groupsList.makeCandidateListFrom(candidateArrayList);
//             = groupsList.getSpinnerArray();
            setAssociationsSpinner();

        }

        @Override
        protected ArrayList<Candidate> doInBackground(String... strings) {

            ArrayList<Candidate> candidates;

            synchronized (candidateBackgroundApiTasks){

                candidateBackgroundApiTasks.getCandidatesByMemberLevel(strings[0],strings[1]);
                try {
                    candidateBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                candidates = candidateBackgroundApiTasks.getCandidateArrayList();

            }
            return candidates;

        }
    }

    public void setButtonReload(){

        getButton().setText(context.getResources().getString(R.string.reload));

    }

    private void fetchAllGroups() {
        new fetchGroups().execute();
    }

    private class fetchGroups extends AsyncTask<Void,Void, ArrayList<Group>> {

        @Override
        protected void onPostExecute(ArrayList<Group> arrayList) {

            if (arrayList==null){
                setButtonReload();
            }
            groupArrayList = arrayList;
            groupsList.makeListFrom(groupArrayList);
            spinnerArray = groupsList.getSpinnerArray();
            setGroupSpinner();

        }

        @Override
        protected ArrayList<Group> doInBackground(Void... voids) {

            ArrayList<Group> groupArrayListReturned;

            synchronized (groupBackgroundApiTasks){

                groupBackgroundApiTasks.fetchGroups();
                try {
                    groupBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                groupArrayListReturned = groupBackgroundApiTasks.getGroupArrayList();

            }
            return groupArrayListReturned;

        }
    }

    private void fetchAllCategories() {

        candidateCategoryBackgroundApiTasks = new CandidateCategoryBackgroundApiTasks(context);
        new fetchCategories().execute();

    }

    public class fetchCategories extends AsyncTask<Void,Void,ArrayList<CandidateCategory>>{

        @Override
        protected void onPostExecute(ArrayList<CandidateCategory> candidateCategories) {

            candidateCategoryArrayList = candidateCategories;
            Log.e("post execute", "onPostExecute: "+candidateCategoryArrayList );
            groupsList.makeCategoryListFrom(candidateCategoryArrayList);
//            categorySpinnerArray = groupsList.getCategorySpinnerArray();
            setCategorySpinner();

        }

        @Override
        protected ArrayList<CandidateCategory> doInBackground(Void... voids) {

            ArrayList<CandidateCategory> candidateCategoryArrayListReturned;

            synchronized (candidateCategoryBackgroundApiTasks){

                candidateCategoryBackgroundApiTasks.fetchCategories();
                try {
                    candidateCategoryBackgroundApiTasks.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                candidateCategoryArrayListReturned = candidateCategoryBackgroundApiTasks.getCandidateCategoryArrayList();
            }
            return candidateCategoryArrayListReturned;
        }
    }

    public void setCategorySpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
                groupsList.categorySpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        showprogress.dismiss();

    }

    public void setAssociationsSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,groupsList.candidateSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        associationsSpinner.setAdapter(adapter);
        showprogress.dismiss();
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
