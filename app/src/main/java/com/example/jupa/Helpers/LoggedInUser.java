package com.example.jupa.Helpers;

import com.example.jupa.Candidate.Candidate;

public class LoggedInUser {


    Candidate loggedInCandidate;

    public static LoggedInUser Instance = null;

    public static LoggedInUser getInstance() {
        if (Instance == null ){

            Instance = new LoggedInUser();

        }
        return Instance;
    }

    public void LoginUser(Candidate candidate){
        loggedInCandidate = candidate;
    }

    public void LogOutUser(){
        loggedInCandidate = null;
    }

    public Candidate getLoggedInCandidate() {
        return loggedInCandidate;
    }


}
