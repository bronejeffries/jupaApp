package com.example.jupa.Institution.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.InstitutionActivity;
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

        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.institution_item_view,parent,false);

        return new InstitutionViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0){

            Institution institution = getInstitutionArrayList().get(position);
            ((InstitutionViewHolder)holder).bind(institution);

        }


    }

    @Override
    public int getItemCount() {

        return getInstitutionArrayList()!=null?getInstitutionArrayList().size():0;

    }


    private class InstitutionViewHolder extends RecyclerView.ViewHolder{

        Institution thisInstitution;
        View view;
        TextView nameView;
        public InstitutionViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            nameView = (TextView)itemView.findViewById(R.id.institutions_name);
        }

        public void bind(Institution institution){

            this.thisInstitution = institution;
            nameView.setText(institution.getTitltleRepresentation());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInstitution();
                }
            });

        }

        public void showInstitution(){

            Intent intent = new Intent(context, InstitutionActivity.class);
            intent.putExtra(InstitutionActivity.INSTITUTION_EXTRA,this.thisInstitution);
            context.startActivity(intent);

        }
    }


}
