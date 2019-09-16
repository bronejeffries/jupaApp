package com.example.jupa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CandidatesAdapter extends RecyclerView.Adapter {


    public static CandidatesAdapter instance = null;
    public Context context;
    public ArrayList<Candidate> arrayList;


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
            ((CandidateViewHolder) holder).status.setText(candidate.getStatus());
            ((CandidateViewHolder) holder).available.setText(candidate.getAvailable());
            ((CandidateViewHolder) holder).country.setText(candidate.getCountry());
            ((CandidateViewHolder) holder).profile_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProfile(candidate);
                }
            });
            ((CandidateViewHolder) holder).role.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showRolesDialog(candidate,((CandidateViewHolder) holder));

                }
            });
        }

    }

    private void showProfile(Candidate candidate) {

        Toast.makeText(context, "Profile Loading.....", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context,ProfileActivity.class);
        intent.putExtra(ProfileActivity.CANDIDATE_EXTRA, candidate);
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
