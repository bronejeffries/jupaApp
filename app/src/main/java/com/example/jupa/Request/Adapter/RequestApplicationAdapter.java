package com.example.jupa.Request.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jupa.Activity.RequestApplicationViewActivity;
import com.example.jupa.R;
import com.example.jupa.Request.RequestApplicationObject;

import java.util.ArrayList;

public class RequestApplicationAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<RequestApplicationObject> requestApplicationObjectArrayList;
    public static final int PERSONAL_VIEW = 1, APPLICATION_VIEW = 2, APPROVED = 1, REJECTED = -1, FORWARDED = 2;
    public int viewPlatform;
    public static final String DATE_PREFIX = " on the ";

    public RequestApplicationAdapter(Context context, ArrayList<RequestApplicationObject> requestApplicationObjectArrayList, int viewPlatform) {

        this.context = context;
        this.requestApplicationObjectArrayList = requestApplicationObjectArrayList;
        this.viewPlatform = viewPlatform;

    }

    @Override
    public int getItemViewType(int position) {

        return viewPlatform;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View personalView;

        switch (viewType){

            case PERSONAL_VIEW:
                personalView = (CardView) LayoutInflater.from(context).inflate(R.layout.personal_application_view,parent,false);
                return new personalApplicationView(personalView);

            case APPLICATION_VIEW:
                personalView = (CardView)LayoutInflater.from(context).inflate(R.layout.request_item_layout,parent,false);
                return new applicationViewHolder(personalView);

            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemCount()>0){

            RequestApplicationObject requestApplicationObject = getRequestApplicationObjectArrayList().get(position);
            if (holder.getItemViewType() == PERSONAL_VIEW){

                ((personalApplicationView)holder).bind(requestApplicationObject,position);

            } else {

                ((applicationViewHolder)holder).bind(requestApplicationObject,position);

            }

        }


    }

    @Override
    public int getItemCount() {

        return getRequestApplicationObjectArrayList()!=null?getRequestApplicationObjectArrayList().size():0;

    }

    public void changeItem(int adapterPosition) {

        this.notifyItemChanged(adapterPosition);

    }

    public void changeItemStatus(int adapterPosition, int status) {

        this.getRequestApplicationObjectArrayList().get(adapterPosition).setStatus(status);
        this.changeItem(adapterPosition);

    }

    private class applicationViewHolder extends RecyclerView.ViewHolder{

        Context context;
        TextView roleView, applicationType, applicationStatus, viewApplicationDetails,name;

        public applicationViewHolder(@NonNull View itemView) {

            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            viewApplicationDetails = (TextView)itemView.findViewById(R.id.request_view);
            roleView = (TextView)itemView.findViewById(R.id.role_view);
            applicationType = (TextView)itemView.findViewById(R.id.type_view);
            applicationStatus = (TextView)itemView.findViewById(R.id.application_status);

        }

        public void bind(final RequestApplicationObject requestApplicationObject, final int position){

            getStatusInfo(requestApplicationObject.getStatus(),this.applicationStatus);
            applicationType.setText(requestApplicationObject.getRequest_type());
            viewApplicationDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showDetails(requestApplicationObject,position);

                }
            });
        }

    }


    private class personalApplicationView extends RecyclerView.ViewHolder{

        TextView textView, status_view;

        public personalApplicationView(@NonNull View itemView) {

            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.application_description);
            status_view = (TextView)itemView.findViewById(R.id.application_status);

        }

        public void bind(final RequestApplicationObject requestApplicationObject, final int position){

            String applicationText = requestApplicationObject.getRequest_type().
                                    equals(RequestApplicationViewActivity.ASSESSOR_REQUEST_TYPE)?
                                    context.getResources().getString(R.string.become_assessor):
                                    context.getResources().getString(R.string.higher_rank),
            application_intro = context.getResources().getString(R.string.you_submitted)+" ",
            record_date = DATE_PREFIX+requestApplicationObject.getRecord_date();

            String textViewInfo = application_intro+applicationText+record_date;

            getStatusInfo(requestApplicationObject.getStatus(),this.status_view);

            textView.setText(textViewInfo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDetails(requestApplicationObject, position);
                }
            });

        }

    }

    public void getStatusInfo(Integer status, TextView textView){


        String status_info;

        if (status.equals(APPROVED)){

            status_info = "approved";
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }else if (status.equals(REJECTED)){

            status_info = "rejected";
            textView.setTextColor(context.getResources().getColor(R.color.colorAccent));

        }else if(status.equals(FORWARDED)){

            status_info = "forwarded";
            textView.setTextColor(context.getResources().getColor(R.color.design_default_color_primary));

        }else {

            status_info = "pending";
            textView.setTextColor(context.getResources().getColor(R.color.black));

        }
        textView.setText(status_info);

    }


    public void showDetails(RequestApplicationObject applicationObject, int position){

        Intent intent = new Intent(context, RequestApplicationViewActivity.class);
        intent.putExtra(RequestApplicationViewActivity.REQUEST_EXTRA,applicationObject);
        intent.putExtra(RequestApplicationViewActivity.POSITION_EXTRA,position);
        intent.putExtra(RequestApplicationViewActivity.VIEW_PLATFORM_EXTRA,viewPlatform);
        context.startActivity(intent);


    }

    public ArrayList<RequestApplicationObject> getRequestApplicationObjectArrayList() {

        return requestApplicationObjectArrayList;

    }

    public void setRequestApplicationObjectArrayList(ArrayList<RequestApplicationObject> requestApplicationObjectArrayList) {

        this.requestApplicationObjectArrayList = requestApplicationObjectArrayList;
        this.notifyDataSetChanged();

    }



    public void addItem(RequestApplicationObject requestApplicationObject){

        if (getRequestApplicationObjectArrayList()==null){

            setRequestApplicationObjectArrayList(new ArrayList<RequestApplicationObject>());
        }

        getRequestApplicationObjectArrayList().add(requestApplicationObject);
        this.notifyDataSetChanged();
    }

    public void addItems(ArrayList<RequestApplicationObject> requestApplicationObjects){

        if (getRequestApplicationObjectArrayList()==null){

            setRequestApplicationObjectArrayList(new ArrayList<RequestApplicationObject>());
        }

        getRequestApplicationObjectArrayList().addAll(requestApplicationObjects);
        this.notifyDataSetChanged();
    }
}
