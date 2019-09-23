package com.example.jupa.Assessment.AssessmentGroup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Assessment.AssessmentGroup.AssessmentGroup;
import com.example.jupa.R;

import java.util.ArrayList;

public class AssessmentGroupsAdapter extends RecyclerView.Adapter {


    ArrayList<AssessmentGroup> assessmentGroupArrayList;
    Context context;

    public AssessmentGroupsAdapter(ArrayList<AssessmentGroup> assessmentGroupArrayList, Context context) {
        this.assessmentGroupArrayList = assessmentGroupArrayList;
        this.context = context;
    }

    public ArrayList<AssessmentGroup> getAssessmentGroupArrayList() {
        return assessmentGroupArrayList;
    }

    public void setAssessmentGroupArrayList(ArrayList<AssessmentGroup> assessmentGroupArrayList) {
        this.assessmentGroupArrayList = assessmentGroupArrayList;
    }

    public void addItem(AssessmentGroup assessmentGroup){

        if (assessmentGroupArrayList==null){
            assessmentGroupArrayList = new ArrayList<>();
        }
        getAssessmentGroupArrayList().add(assessmentGroup);
        this.notifyDataSetChanged();

    }

    public void addArrayList(ArrayList<AssessmentGroup> assessmentGroups){

        if (assessmentGroupArrayList==null){
            assessmentGroupArrayList = new ArrayList<>();
        }

        getAssessmentGroupArrayList().addAll(assessmentGroups);

        this.notifyDataSetChanged();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RelativeLayout relativeLayout =  (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_show_view,parent,false);

        return new AssessmentGroupViewHolder(relativeLayout);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0){

            AssessmentGroup assessmentGroup = assessmentGroupArrayList.get(position);
            ((AssessmentGroupViewHolder)holder).textView.setText(assessmentGroup.getName());
            ((AssessmentGroupViewHolder)holder).assess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });

        }




    }

    @Override
    public int getItemCount() {
        return assessmentGroupArrayList!=null?assessmentGroupArrayList.size():0;
    }

    private static class AssessmentGroupViewHolder extends RecyclerView.ViewHolder{


        RelativeLayout holderView;
        TextView textView, assess,view;


        public AssessmentGroupViewHolder(View view) {

            super(view);
            holderView = (RelativeLayout)view;
            textView = (TextView)view.findViewById(R.id.assessment_name);
            assess = (TextView)view.findViewById(R.id.assessment_continue);
            view = (TextView)view.findViewById(R.id.assessment_view);

        }
    }



}
