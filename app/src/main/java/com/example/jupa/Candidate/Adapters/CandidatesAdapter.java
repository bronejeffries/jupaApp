package com.example.jupa.Candidate.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.GroupActivity;
import com.example.jupa.Activity.ProfileActivity;
import com.example.jupa.Activity.UserHomeActivity;
import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Group.Group;
import com.example.jupa.Helpers.LoggedInUser;
import com.example.jupa.R;
import com.example.jupa.Rank.Rank;

import java.util.ArrayList;

public class CandidatesAdapter extends RecyclerView.Adapter {


    public static CandidatesAdapter instance = null;
    public Context context;
    public ArrayList<Candidate> arrayList;
    public Candidate loggedInCandidate =  LoggedInUser.getInstance().getLoggedInCandidate();
    public Rank rank;
    public Group group;



    public CandidatesAdapter(Context context, ArrayList<Candidate> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public static CandidatesAdapter getInstance(@Nullable Context context, @Nullable ArrayList<Candidate> arrayList) {

        if (instance == null){
          instance =  new CandidatesAdapter(context,arrayList);
        }

        return instance;
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.personcardview,parent,false);
        return new CandidateViewHolder(cardView);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0) {
            final Candidate candidate = arrayList.get(position);
            ((CandidateViewHolder) holder).name.setText(candidate.getName());
            ((CandidateViewHolder) holder).role.setText(candidate.getRole());
            if (loggedInCandidate!=null){
                if (!loggedInCandidate.getRole().equals(UserHomeActivity.ADMINISTRATOR_ROLE)){

                    ((CandidateViewHolder) holder).role.setCompoundDrawables(null,null,null,null);

                }else {

                    ((CandidateViewHolder) holder).role.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            showRolesDialog(candidate,((CandidateViewHolder) holder));

                        }
                    });

                }
            }
            ((CandidateViewHolder) holder).status.setText(candidate.getStatus());
            ((CandidateViewHolder) holder).available.setText(candidate.getAvailable());
            ((CandidateViewHolder) holder).country.setText(candidate.getAddress());
            ((CandidateViewHolder) holder).profile_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProfile(candidate);
                }
            });
        }

    }

    private void showProfile(Candidate candidate) {

        Intent intent = new Intent(context, ProfileActivity.class);

        intent.putExtra(ProfileActivity.CANDIDATE_EXTRA, candidate);
        intent.putExtra(ProfileActivity.RANK_EXTRA,rank);
        intent.putExtra(GroupActivity.GROUP_TAG,group);
        context.startActivity(intent);


    }

    
    private void showRolesDialog(final Candidate candidate, final CandidateViewHolder holder) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String[] roles = {"Candidate","Assessor","Group Admin", "Administrator"};
        builder.setTitle("Edit Role").setItems(roles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                candidate.setRole(roles[i]);
                holder.role.setText(candidate.getRole());
                Toast.makeText(context,"Role Changed Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    public int getItemCount() {

        return (arrayList!=null)?arrayList.size():0;

    }

    public ArrayList<Candidate> getArrayList() {
        return arrayList;
    }

    public void updateArrayList(ArrayList<Candidate> candidateArrayList){

        if (getArrayList()==null){

            setArrayList(new ArrayList<Candidate>());

        }
        this.getArrayList().addAll(candidateArrayList);
        this.notifyDataSetChanged();

    }

    public int getLastItemId(){

        int last = 0;
        if (getItemCount()>0){

            last = getArrayList().get((getItemCount()-1)).getId();

        }

        return last;

    }

    public void setArrayList(ArrayList<Candidate> arrayList) {
        this.arrayList = arrayList;
    }


    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static class CandidateViewHolder extends RecyclerView.ViewHolder{

        TextView role, name, status,country, available, profile_view;
        public CandidateViewHolder(@NonNull View view) {

            super(view);
            role = (TextView)view.findViewById(R.id.role_view);
            name = (TextView)view.findViewById(R.id.name);
            status = (TextView)view.findViewById(R.id.status_view);
            country = (TextView)view.findViewById(R.id.country_view);
            available = (TextView)view.findViewById(R.id.available_view);
            profile_view = (TextView)view.findViewById(R.id.profile_view);

        }


    }

}
