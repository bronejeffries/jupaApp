package com.example.jupa;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class GroupsList {

    public static List<String> spinnerArray;


    public static List<String> getSpinnerArray() {
        return spinnerArray;
    }


    public static void setSpinnerArray(List<String> spinner) {

        spinnerArray = spinner;

    }

    public static void addItem(String item){
        getSpinnerArray().add(item);
    }

    public static List<String> makeList(){

        setSpinnerArray(new ArrayList<String>());

        GroupsAdapter current_adapter = GroupsAdapter.getInstance(null, null);
        if (current_adapter != null ){

            ArrayList<Group> groupArrayList = current_adapter.getGroupArrayList();

            if (groupArrayList!=null){

                for (Group group: groupArrayList) {
                    addItem(group.group_name);
                }

            }

        }

        return getSpinnerArray();
    }

    public static Group findGroupByName(String name){

        Group groupFound = null;
        GroupsAdapter current_adapter = GroupsAdapter.getInstance(null, null);
        if (current_adapter != null ){

            ArrayList<Group> groupArrayList = current_adapter.getGroupArrayList();

            if (groupArrayList!=null){

                for (Group group: groupArrayList) {
                    if (group.getGroup_name().equals(name)){
                        groupFound = group;
                    }
                }

            }

        }

        return groupFound;
    }

}
