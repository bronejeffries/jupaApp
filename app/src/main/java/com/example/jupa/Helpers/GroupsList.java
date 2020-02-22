package com.example.jupa.Helpers;

import android.content.Context;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Candidate.Category.CandidateCategory;
import com.example.jupa.Group.Api.GroupBackgroundApiTasks;
import com.example.jupa.Group.Group;
import com.example.jupa.Institution.Institution;
import com.example.jupa.Rank.Rank;

import java.util.ArrayList;
import java.util.List;

public class GroupsList {

    public  Context context;
    public GroupBackgroundApiTasks groupBackgroundApiTasks;
    public  List<String> spinnerArray, categorySpinnerArray,ranksSpinnerArray, institutionSpinnerArray, candidateSpinnerArray;
    private ArrayList<Group> groupArrayList;
    private ArrayList<CandidateCategory> candidateCategoryArrayList;
    private ArrayList<Rank> rankArrayList;
    private ArrayList<Institution> institutionArrayList;
    private ArrayList<Candidate> candidateArrayList;

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

    public void makeInstitutionListFrom(ArrayList<Institution> arrayList){

        institutionArrayList = arrayList;
        makeInstitutionList();
    }

    public void makeRankListFrom(ArrayList<Rank> arrayList){

        rankArrayList = arrayList;
        makeRankList();
    }

    public void makeCandidateListFrom(ArrayList<Candidate> arrayList){

        setCandidateArrayList(arrayList);
        makeAssociationsList();
    }

    private void makeRankList() {

        setRanksSpinnerArray(new ArrayList<String>());

        if (rankArrayList != null){
            for (Rank rank: rankArrayList) {
                ranksSpinnerArray.add(rank.getName());
            }
        }

    }

    private void makeInstitutionList() {

        setInstitutionSpinnerArray(new ArrayList<String>());

        if (institutionArrayList !=null){

            for (Institution institution : institutionArrayList  ) {
                String institution_name = institution.getName()+" ("+institution.getCenter_No()+")";
                institutionSpinnerArray.add(institution_name);
            }

        }

    }

    public List<String> getCandidateSpinnerArray() {
        return candidateSpinnerArray;
    }

    public void setCandidateSpinnerArray(List<String> candidateSpinnerArray) {
        this.candidateSpinnerArray = candidateSpinnerArray;
    }

    public ArrayList<Candidate> getCandidateArrayList() {
        return candidateArrayList;
    }

    public void setCandidateArrayList(ArrayList<Candidate> candidateArrayList) {
        this.candidateArrayList = candidateArrayList;
    }

    private void makeAssociationsList() {

        setCandidateSpinnerArray(new ArrayList<String>());

        if (candidateArrayList !=null){

            for (Candidate candidate : candidateArrayList  ) {
                String candidate_name = candidate.getAssociation_name();
                candidateSpinnerArray.add(candidate_name);
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

    public Candidate findAssociationByName(String name){

        Candidate candidateFound = null;

        if (candidateArrayList!=null){

            for (Candidate candidate: getCandidateArrayList()) {
                if (candidate.getAssociation_name().equals(name)){
                    candidateFound =  candidate;
                }
            }
        }
        return candidateFound;

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

    public List<String> getInstitutionSpinnerArray() {
        return institutionSpinnerArray;
    }

    public void setInstitutionSpinnerArray(List<String> institutionSpinnerArray) {
        this.institutionSpinnerArray = institutionSpinnerArray;
    }

    public ArrayList<Institution> getInstitutionArrayList() {
        return institutionArrayList;
    }

    public void setInstitutionArrayList(ArrayList<Institution> institutionArrayList) {
        this.institutionArrayList = institutionArrayList;
    }
}
