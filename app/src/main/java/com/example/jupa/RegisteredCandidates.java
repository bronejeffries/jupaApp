package com.example.jupa;

import java.util.ArrayList;
import java.util.List;

public class RegisteredCandidates {


    public ArrayList<Candidate> candidateArrayList = new ArrayList<>();

    public static RegisteredCandidates instance = null ;

    public static RegisteredCandidates getInstance() {
        if (instance == null){

            instance = new RegisteredCandidates();

        }
        return instance;
    }


    public void addToList(Candidate candidate){

        getCandidateArrayList().add(candidate);

    }

    public  ArrayList<Candidate> getCandidateArrayList() {
        return candidateArrayList;
    }

    public  void setCandidateArrayList(ArrayList<Candidate> candidateArrayList) {

        this.candidateArrayList = candidateArrayList;

    }

    public ArrayList<Candidate> filterListByGroup(String groupname){

        ArrayList<Candidate> filteredList = new ArrayList<>();

        for (Candidate candidate: this.getCandidateArrayList() ) {

            if (candidate.getGroup() != null){

                if (candidate.getCandidate_group().getGroup_name().equals(groupname)){

                    filteredList.add(candidate);

                }

            }
        }
        return filteredList;
    }



//    public ArrayList<Candidate> filterListByAssesor(Candidate Assessor){
//
//        ArrayList<Candidate> filteredList = new ArrayList<>();
//
//        for (Candidate candidate: filterListByGroup(Assessor.getCandidate_group().getGroup_name()) ) {
//
//            if (candidate.getGroup() != null && candidate.getCandidateAssessor()!=null){
//
//                if (candidate.getCandidateAssessor().getName().equals(Assessor.getName())){
//
//                    filteredList.add(candidate);
//                }
//
//            }
//        }
//        return filteredList;
//
//    }

//    public List<String> filterGroupAssessorsList(Group group) {
//
//        List<String> stringList = new ArrayList<>();
//        ArrayList<Candidate> groupAssessors = filterGroupAssessors(group);
//        for (Candidate candidate : groupAssessors) {
//            stringList.add(candidate.getName());
//        }
//
//        return stringList;
//
//    }

    public ArrayList<Candidate> filterGroupAssessors( Group group ){

        ArrayList<Candidate> filteredArrayList = new ArrayList<>() ;
        for (Candidate candidate:filterListByGroup(group.getGroup_name())) {

            if (candidate.getRole().equals(UserHomeActivity.ASSESSOR_ROLE)){

                filteredArrayList.add(candidate);

            }

        }

        return filteredArrayList;

    }


    public Candidate getAssessorFromGroup(Group group, String AssessorName){

        Candidate FoundAssessor = null;
        for (Candidate assessor: filterGroupAssessors(group)) {
            if (assessor.getName().equals(AssessorName)){
                FoundAssessor = assessor;
            }
        }

        return FoundAssessor;
    }

    public Candidate retrieveCandidateByEMail(String email){

        Candidate foundCandidate = null;

        for (Candidate candidate: this.getCandidateArrayList() ) {

            if (candidate.getEmail() != null){

                if (candidate.getEmail().equals(email)){

                    foundCandidate = candidate;

                }

            }
        }

        return foundCandidate;

    }




}
