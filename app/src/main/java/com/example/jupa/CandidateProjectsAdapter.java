package com.example.jupa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CandidateProjectsAdapter extends RecyclerView.Adapter {

    ArrayList<CandidateProject> candidateProjectArrayList;
    Context context;
    public static CandidateProjectsAdapter Instance = null;

    public CandidateProjectsAdapter(ArrayList<CandidateProject> candidateProjectArrayList, Context context) {
        this.candidateProjectArrayList = candidateProjectArrayList;
        this.context = context;
    }


    public static CandidateProjectsAdapter getInstance(ArrayList<CandidateProject> candidateProjectArrayList, Context context) {

        if (Instance == null && context!=null){
            Instance = new CandidateProjectsAdapter(candidateProjectArrayList,context);
        }
        return Instance;

    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProjectViewHolder viewHolder;
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.projectshowcard,parent,false);
        viewHolder = new ProjectViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0){
            final CandidateProject candidateProject = candidateProjectArrayList.get(position);
            ((ProjectViewHolder)holder).location.setText(candidateProject.getLocation());
            ((ProjectViewHolder)holder).client_name.setText(candidateProject.getClient_name());
            ((ProjectViewHolder)holder).client_contact.setText(candidateProject.getClient_contact());
            ((ProjectViewHolder)holder).assess_project_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAssessments(candidateProject);
                }
            });
        }

    }

    private void showAssessments(CandidateProject candidateProject) {

        Intent showAssessmentIntent = new Intent(context,ProjectAssessmentsActivity.class);
        showAssessmentIntent.putExtra(ProjectAssessmentsActivity.CANDIDATE_PROJECT_EXTRA,candidateProject);
        context.startActivity(showAssessmentIntent);

    }

    @Override
    public int getItemCount() {

        return (candidateProjectArrayList!=null)?(candidateProjectArrayList.size()):0;

    }


    public ArrayList<CandidateProject> getCandidateProjectArrayList() {
        return candidateProjectArrayList;
    }

    public void setCandidateProjectArrayList(ArrayList<CandidateProject> candidateProjectArrayList) {
        this.candidateProjectArrayList = candidateProjectArrayList;
        this.notifyDataSetChanged();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder{

        TextView location, client_contact, client_name;
        ImageView project_image;
        CardView cardView;
        Button assess_project_btn;

        public ProjectViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            location = (TextView)cardView.findViewById(R.id.project_location_input);
            client_contact = (TextView)cardView.findViewById(R.id.project_client_contact_input);
            client_name = (TextView)cardView.findViewById(R.id.project_client_name_input);
            project_image = (ImageView)cardView.findViewById(R.id.project_image);
            assess_project_btn = (Button)cardView.findViewById(R.id.assess_project_btn);
        }
    }
}
