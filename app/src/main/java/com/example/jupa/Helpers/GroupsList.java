package com.example.jupa.Helpers;

import android.content.Context;

import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Rank.Rank;

import java.util.ArrayList;
import java.util.List;

public class GroupsList {


    public  Context context;
    public GroupBackgroundApiTasks groupBackgroundApiTasks;
    public  List<String> spinnerArray, categorySpinnerArray,ranksSpinnerArray;
    private ArrayList<Group> groupArrayList;
    private ArrayList<CandidateCategory> candidateCategoryArrayList;
    private ArrayList<Rank> rankArrayList;




    public GroupsList(Context context) {
        this.context = context;
        groupBackgroundApiTasks = GroupBackgroundApiTasks.getInstance(context.getApplicationContext());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getSpinnerArray() {
        return spinnerArray;
    }


    public void setSpinnerArray(List<String> spinner) {

        spinnerArray = spinner;

    }

    public  void addItem(String item){
        getSpinnerArray().add(item);
    }

    public void makeList(){
        setSpinnerArray(new ArrayList<String>());

        if (groupArrayList !=null){
            for (Group group: groupArrayList) {
                addItem(group.getGroup_name());
            }
        }

    }

    public void makeCategoryList(){

        setCategorySpinnerArray(new ArrayList<String>());

        if (candidateCategoryArrayList != null){
            for (CandidateCategory candidateCategory: candidateCategoryArrayList) {
                categorySpinnerArray.add(candidateCategory.getName());
            }
        }

    }

    public void makeCategoryListFrom(ArrayList<CandidateCategory> arrayList){

        candidateCategoryArrayList = arrayList;
        makeCategoryList();
    }

    public void makeRankListFrom(ArrayList<Rank> arrayList){

        rankArrayList = arrayList;
        makeRankList();
    }

    private void makeRankList() {

        setRanksSpinnerArray(new ArrayList<String>());

        if (rankArrayList != null){
            for (Rank rank: rankArrayList) {
                ranksSpinnerArray.add(rank.getName());
            }
        }

    }


    public void makeListFrom(ArrayList<Group> arrayList){

        groupArrayList = arrayList;
        makeList();
    }

    public Group findGroupByName(String name){

        Group groupFound = null;


            if (groupArrayList!=null){

                for (Group group: groupArrayList) {
                    if (group.getGroup_name().equals(name)){
                        groupFound = group;
                    }
                }

            }

        return groupFound;
    }

    public CandidateCategory findCandidateCategoryByName(String name){

        CandidateCategory candidateCategoryFound = null;


        if (candidateCategoryArrayList!=null){

            for (CandidateCategory candidateCategory: candidateCategoryArrayList) {
                if (candidateCategory.getName().equals(name)){
                    candidateCategoryFound =  candidateCategory;
                }
            }

        }

        return candidateCategoryFound;
    }

    public Rank findRankByName(String name){

        Rank rankFound = null;


        if (rankArrayList!=null){

            for (Rank rank: rankArrayList) {
                if (rank.getName().equals(name)){
                    rankFound =  rank;
                }
            }
        }
        return rankFound;
    }

    public List<String> getCategorySpinnerArray() {
        return categorySpinnerArray;
    }

    public void setCategorySpinnerArray(List<String> categorySpinnerArray) {
        this.categorySpinnerArray = categorySpinnerArray;
    }

    public List<String> getRanksSpinnerArray() {
        return ranksSpinnerArray;
    }

    public void setRanksSpinnerArray(List<String> ranksSpinnerArray) {
        this.ranksSpinnerArray = ranksSpinnerArray;
    }
}