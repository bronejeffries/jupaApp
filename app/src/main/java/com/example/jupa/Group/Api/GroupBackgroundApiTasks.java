package com.example.jupa.Group.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Candidate.Api.CandidateListApiData;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Group.Group;
import com.example.jupa.Activity.GroupSearchActivity;
import com.example.jupa.Helpers.RetrofitSingleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class GroupBackgroundApiTasks {

    Context context;
    Group group;
    ArrayList<Group> groupArrayList = new ArrayList<>();
    ArrayList<Candidate> groupCandidatesList = new ArrayList<>();
    public String SUCCESS = "1", FAILED = "0", message;

    public static GroupBackgroundApiTasks Instance = null;
    public final static GroupApi_Interface groupApi_interface = RetrofitSingleton.getRetrofitInstance().create(GroupApi_Interface.class);

    public static GroupBackgroundApiTasks getInstance(Context context) {

        if (Instance == null && context!=null ){

            Instance = new GroupBackgroundApiTasks(context);

        }

        return Instance;
    }



    public GroupBackgroundApiTasks(Context context) {
        this.context = context;
    }


    public void fetchGroups(){

        Call<GrouplistApiData> call = groupApi_interface.getAllGroups();
        call.enqueue(new Callback<GrouplistApiData>() {
            @Override
            public void onResponse(Call<GrouplistApiData> call, Response<GrouplistApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroupArrayList(response.body().getGroupArrayList());
                    }

                    GroupBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<GrouplistApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    t.printStackTrace();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }
        });


    }


    public void getGroupCandidatesWithRole(int group_id, String member_level){

        Call<GroupCandidatesListApiData> call = groupApi_interface.getCandidatesWithRole(group_id,member_level);
        call.enqueue(new Callback<GroupCandidatesListApiData>() {
            @Override
            public void onResponse(Call<GroupCandidatesListApiData> call, Response<GroupCandidatesListApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroupCandidatesList(response.body().getGroupCandidates());
                    }
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<GroupCandidatesListApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    t.printStackTrace();
                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    GroupBackgroundApiTasks.this.notifyAll();

                }

            }
        });

    }


    public void addNewGroup(Group group){

        Call<GroupApiData> call = groupApi_interface.addGroup(group.getGroup_name(),group.getGroup_code(), UserHomeActivity.thisCandidate.getId());
        call.enqueue(new Callback<GroupApiData>() {
            @Override
            public void onResponse(Call<GroupApiData> call, Response<GroupApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getSuccess());

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroup(response.body().getGroup());
                    }

                    setMessage(response.body().getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                }

//                groupName = groupName_input.getText().toString().toLowerCase();
//                groupCode = groupCode_input.getText().toString().toLowerCase();
//                save_group(groupName,groupCode);
            }

            @Override
            public void onFailure(Call<GroupApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){
                    GroupBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });


    }

    public void getGroupById(Integer group_id){

        Call<GroupApiData> call = groupApi_interface.getGroupById(group_id);
        call.enqueue(new Callback<GroupApiData>() {
            @Override
            public void onResponse(Call<GroupApiData> call, Response<GroupApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getSuccess());

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroup(response.body().getGroup());
                    }else {
                        setGroup(null);
                    }
                    setMessage(response.body().getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<GroupApiData> cgetGroupByIdall, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    setMessage(t.getMessage());
                    setGroup(null);
                    GroupBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();
                }

            }

        });


    }


    public void fetchGroupCandidates(int group_id){

        Call<GroupCandidatesListApiData> call = groupApi_interface.getCandidatesInAGroup(group_id);

        call.enqueue(new Callback<GroupCandidatesListApiData>() {
            @Override
            public void onResponse(Call<GroupCandidatesListApiData> call, Response<GroupCandidatesListApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getGroupCandidates().toString());
                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroupCandidatesList(response.body().getGroupCandidates());
                    }
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<GroupCandidatesListApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    GroupBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();
                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void fetchAssessorCandidates(GroupSearchActivity.searchObject searchObject){

        Log.e(TAG, "fetchAssessorCandidates: "+searchObject.getGroup_id()+" "+searchObject.getLast() );


        Call<CandidateListApiData> call = groupApi_interface.getCandidatesByAssessor(searchObject.getAssessor_id(),searchObject.getInstitution_id()
                                                                , searchObject.getLast(),searchObject.getLimit());

        call.enqueue(new Callback<CandidateListApiData>() {
            @Override
            public void onResponse(Call<CandidateListApiData> call, Response<CandidateListApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getCandidateArrayList().toString());

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setGroupCandidatesList(response.body().getCandidateArrayList());

                    }else {

                        setGroupCandidatesList(null);

                    }
                    setMessage(response.body().getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<CandidateListApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){
                    setGroupCandidatesList(null);
                    setMessage(t.getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }
        });

    }

    public void SearchGroupCandidates(GroupSearchActivity.searchObject newSearchObject){

        Log.e(TAG, "SearchGroupCandidates: "+newSearchObject.getGroup_id()+" "+newSearchObject.getRank_id()+" "+newSearchObject.getStatus()+" "+newSearchObject.getLast() );

        Call<GroupCandidatesListApiData> call = groupApi_interface.searchCandidatesInAgroup(newSearchObject.getGroup_id(),
                newSearchObject.getRank_id(), newSearchObject.getStatus(),newSearchObject.getLast(),newSearchObject.getLimit());

        call.enqueue(new Callback<GroupCandidatesListApiData>() {
            @Override
            public void onResponse(Call<GroupCandidatesListApiData> call, Response<GroupCandidatesListApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getGroupCandidates().toString());
                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroupCandidatesList(response.body().getGroupCandidates());
                    }
                    setMessage(response.body().getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<GroupCandidatesListApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    setGroupCandidatesList(new ArrayList<Candidate>());
                    setMessage(t.getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();

                }

            }
        });

    }

    public void SearchGroupInstituionCandidates(GroupSearchActivity.searchObject newSearchObject){

        Log.e(TAG, "SearchGroupCandidates: "+newSearchObject.getGroup_id()+" "+newSearchObject.getInstitution_id()+" "+newSearchObject.getMember_level()+" "+newSearchObject.getLast());

        Call<GroupCandidatesListApiData> call = groupApi_interface.searchInsititutionCandidatesInAgroup(newSearchObject.getGroup_id(),
                                                                    newSearchObject.getInstitution_id(),newSearchObject.getMember_level(),
                                                                    newSearchObject.getLast(),newSearchObject.getLimit());

        call.enqueue(new Callback<GroupCandidatesListApiData>() {
            @Override
            public void onResponse(Call<GroupCandidatesListApiData> call, Response<GroupCandidatesListApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getGroupCandidates().toString());

                    if (response.body().getSuccess().equals(SUCCESS)){
                        setGroupCandidatesList(response.body().getGroupCandidates());
                    }
                    setMessage(response.body().getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<GroupCandidatesListApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    setGroupCandidatesList(new ArrayList<Candidate>());
                    setMessage(t.getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();

                }

            }
        });

    }

    public void SearchInstituionCandidatesByLevel(GroupSearchActivity.searchObject newSearchObject){

        Log.e(TAG, "SearchGroupCandidates: "+newSearchObject.getGroup_id()+" "+newSearchObject.getInstitution_id()+" "+newSearchObject.getMember_level()+" "+newSearchObject.getLast());

        Call<CandidateListApiData> call = groupApi_interface.searchInsititutionCandidatesbyMemberLevel(newSearchObject.getInstitution_id(),newSearchObject.getMember_level(),
                newSearchObject.getLast(),newSearchObject.getLimit());

        call.enqueue(new Callback<CandidateListApiData>() {
            @Override
            public void onResponse(Call<CandidateListApiData> call, Response<CandidateListApiData> response) {

                synchronized (GroupBackgroundApiTasks.this){

                    Log.e(TAG, "onResponse: "+response.body().getCandidateArrayList().toString());

                    if (response.body().getSuccess().equals(SUCCESS)){

                        setGroupCandidatesList(response.body().getCandidateArrayList());

                    }else {

                        setGroupCandidatesList(null);

                    }

                    setMessage(response.body().getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();

                }

            }

            @Override
            public void onFailure(Call<CandidateListApiData> call, Throwable t) {

                synchronized (GroupBackgroundApiTasks.this){

                    setGroupCandidatesList(null);
                    setMessage(t.getMessage());
                    GroupBackgroundApiTasks.this.notifyAll();
                    t.printStackTrace();

                }

            }
        });

    }



    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public void setGroupArrayList(ArrayList<Group> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    public ArrayList<Candidate> getGroupCandidatesList() {
        return groupCandidatesList;
    }

    public void setGroupCandidatesList(ArrayList<Candidate> groupCandidatesList) {
        this.groupCandidatesList = groupCandidatesList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
