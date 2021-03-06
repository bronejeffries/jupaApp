package com.example.jupa.Group.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.jupa.Activity.GroupQuestionsActivity;
import com.example.jupa.Activity.GroupsActivity;
import com.example.jupa.Activity.InstitutionActivity;
import com.example.jupa.Activity.InstitutionsActivity;
import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Group.Group;
import com.example.jupa.Activity.GroupActivity;
import com.example.jupa.Activity.GroupSearchActivity;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter {

    public ArrayList<Group> groupArrayList;

    public Context context;

    public static GroupsAdapter Instance = null;

    public GroupsAdapter(ArrayList<Group> groupArrayList, Context context) {
        this.context = context;
        this.groupArrayList = groupArrayList;
    }

    public static GroupsAdapter getInstance(@Nullable ArrayList<Group> arrayList, @Nullable Context context){

        if (Instance == null && context != null ){
            Instance = new GroupsAdapter(arrayList,context);
        }
        return Instance;

    }

    private static class GroupViewHolder extends ViewHolder {

        TextView groupName;
        CardView cardView;

        public GroupViewHolder(@NonNull View view) {

            super(view);
            groupName = (TextView)view.findViewById(R.id.group_name);
            cardView = (CardView)view.findViewById(R.id.group_card_view);
        }

    }

    public void showActionsDialog(final Group group){

        String[] items = {"Search Trade Candidates","Manage Trade Test Module"};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){

                    case 0:
                        Intent intent = new Intent(context, GroupSearchActivity.class);
                        intent.putExtra(GroupActivity.GROUP_TAG,group);
                        context.startActivity(intent);
                        break;

                    case 1:
                        viewGroupQuestions(group.getId());
                        break;

                }
            }
        });

        alertDialogBuilder.create()
                          .show();

    }

    private void viewGroupQuestions(Integer groupId) {

        Intent questionsIntent = new Intent(context, GroupQuestionsActivity.class);
        questionsIntent.putExtra(GroupQuestionsActivity.GROUP_EXTRA,groupId);
        context.startActivity(questionsIntent);

    }


    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GroupViewHolder holder = null;
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.groupcard_view,parent,false);
        holder = new GroupViewHolder(relativeLayout);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (this.getItemCount()>0){
        Holder((GroupViewHolder) holder,position);
        }

    }


    public void Holder(GroupViewHolder holder, int position) {

            final Group group = groupArrayList.get(position);
            holder.groupName.setText(group.getGroup_name());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    if(GroupsActivity.group_search_purpose){

                        intent = new Intent(context, UserHomeActivity.class);
                        intent.putExtra(GroupQuestionsActivity.GROUP_EXTRA,group);
                        intent.putExtra(InstitutionsActivity.SEARCH_PURPOSE,true);
                        context.startActivity(intent);

                    }else {
                        if (GroupsActivity.institution_view){

                            intent = new Intent(context,GroupActivity.class);
                            intent.putExtra(GroupActivity.GROUP_TAG,group);
                            context.startActivity(intent);

                        }else {

                            showActionsDialog(group);

                        }
                    }

                }
            });

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (this.groupArrayList != null){
            size = this.groupArrayList.size();
        }
        return size;
    }


    public ArrayList<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public void setGroupArrayList(ArrayList<Group> groupArrayList) {
        this.groupArrayList = groupArrayList;
        this.notifyDataSetChanged();
    }

    public void addItemToList(Group group){
        getGroupArrayList().add(group);
        notifyDataSetChanged();
    }

}
