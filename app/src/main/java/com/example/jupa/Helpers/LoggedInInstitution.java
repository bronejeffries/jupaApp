package com.example.jupa.Helpers;

import com.example.jupa.Institution.Institution;

public class LoggedInInstitution {


    Institution loggedInInstitution;

    public static LoggedInInstitution Instance = null;

    public static LoggedInInstitution getInstance() {
        if (Instance == null ){

            Instance = new LoggedInInstitution();

        }
        return Instance;
    }

    public void LogginInstitution(Institution institution){

        loggedInInstitution = institution;

    }

    public void logOutInstitution(){

        loggedInInstitution = null;

    }

    public Institution getLoggedInInstitution() {
        return loggedInInstitution;
    }

}
