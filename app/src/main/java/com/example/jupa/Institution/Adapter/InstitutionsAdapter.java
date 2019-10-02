package com.example.jupa.Institution.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Institution.Institution;
import com.example.jupa.R;

import java.util.ArrayList;

public class InstitutionsAdapter extends RecyclerView.Adapter {

    ArrayList<Institution> institutionArrayList;
    Context context;

    public InstitutionsAdapter(ArrayList<Institution> institutionArrayList, Context context) {
        this.institutionArrayList = institutionArrayList;
        this.context = context;
    }

    public ArrayList<Institution> getInstitutionArrayList() {
        return institutionArrayList;
    }

    public void setInstitutionArrayList(ArrayList<Institution> institutionArrayList) {
        this.institutionArrayList = institutionArrayList;
        this.notifyDataSetChanged();
    }

    public void addItem(Institution institution){

        if (getInstitutionArrayList()==null){
            setInstitutionArrayList(new ArrayList<Institution>());
        }
        getInstitutionArrayList().add(institution);
        this.notifyDataSetChanged();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0){

            Institution institution = getInstitutionArrayList().get(position);
            ((InstitutionViewHolder)holder).nameView.setText(institution.getName());
            String center_show = "( "+institution.getName()+" )";
            ((InstitutionViewHolder)holder).centerView.setText(center_show);

        }


    }

    @Override
    public int getItemCount() {

        return getInstitutionArrayList()!=null?getInstitutionArrayList().size():0;

    }


    public static class InstitutionViewHolder extends RecyclerView.ViewHolder{

        View view;
        TextView nameView,centerView;
        public InstitutionViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            nameView = (TextView)itemView.findViewById(R.id.institutions_name);
            centerView = (TextView)itemView.findViewById(R.id.institutions_center);
        }
    }


}
