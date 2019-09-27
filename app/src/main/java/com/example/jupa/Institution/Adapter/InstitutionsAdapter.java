package com.example.jupa.Institution.Adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Institution.Institution;

import java.util.ArrayList;

public class InstitutionsAdapter {

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
    }



    public static class InstitutionViewHolder extends RecyclerView.ViewHolder{


        public InstitutionViewHolder(@NonNull View itemView) {

            super(itemView);

        }
    }


}
