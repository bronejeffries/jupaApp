package com.example.jupa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RanksAdapter extends BaseAdapter {


    ArrayList<Rank> rankArrayList;
    Context context;
    static RanksAdapter instance = null;


    public RanksAdapter(ArrayList<Rank> rankArrayList, Context context) {
        this.rankArrayList = rankArrayList;
        this.context = context;
    }


    public static RanksAdapter getInstance(@Nullable ArrayList<Rank> rankArrayList, @Nullable Context context){

        if (instance == null ){

            instance = new RanksAdapter(rankArrayList,context);

        }
        return instance;
    }


    @Override
    public int getCount() {

        return (rankArrayList != null)? rankArrayList.size():0;

    }

    @Override
    public Object getItem(int i) {
        return (rankArrayList != null)? rankArrayList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//        if (view == null){

        LinearLayout row_view = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_item,viewGroup,false);
//            viewHolder = new SkillViewHolder(view);
//            view.setTag(viewHolder);

//        }else {
//            viewHolder = (SkillViewHolder)view.getTag();
//        }
        Rank rank = (Rank) getItem(i);
        TextView name = (TextView)row_view.findViewById(R.id.list_item_name);
        name.setText(rank.getName());
        return row_view;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public ArrayList<Rank> getRankArrayList() {
        return rankArrayList;
    }

    public void setRankArrayList(ArrayList<Rank> rankArrayList) {
        this.rankArrayList = rankArrayList;
        this.notifyDataSetChanged();
    }

    public void addItem(Rank rank){
        getRankArrayList().add(rank);
        this.notifyDataSetChanged();
    }


}
