package com.example.jupa.Skill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.jupa.R;

import java.util.ArrayList;

public class SkillsAdapter extends BaseAdapter {

    ArrayList<Skill> skillArrayList;
    Context context;
    static SkillsAdapter instance = null;


    public SkillsAdapter(ArrayList<Skill> skillArrayList, Context context) {
        this.skillArrayList = skillArrayList;
        this.context = context;
    }


    public static SkillsAdapter getInstance(@Nullable ArrayList<Skill> skillArrayList, @Nullable Context context){

        if (instance == null && context!=null ){
            instance = new SkillsAdapter(skillArrayList,context);
        }
        return instance;
    }


    @Override
    public int getCount() {
        return (skillArrayList!= null)?skillArrayList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return (skillArrayList!= null)?skillArrayList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        SkillViewHolder viewHolder;
//        if (view == null){

           LinearLayout row_view = (LinearLayout)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_item,viewGroup,false);
//            viewHolder = new SkillViewHolder(view);
//            view.setTag(viewHolder);

//        }else {
//            viewHolder = (SkillViewHolder)view.getTag();
//        }

        Skill skill = (Skill) getItem(i);
        TextView name = (TextView)row_view.findViewById(R.id.list_item_name);
        name.setText(skill.getName());
        return row_view;
    }

    public static class SkillViewHolder{

        TextView list_row_name;
        View view;

        public SkillViewHolder(View view) {
            this.view = view;
            list_row_name = (TextView)view.findViewById(R.id.list_item_name);
        }

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Toast.makeText(context, "Skill added "+ skillArrayList.size(), Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Skill> getSkillArrayList() {
        return skillArrayList;
    }

    public void setSkillArrayList(ArrayList<Skill> skillArrayList) {
        this.skillArrayList = skillArrayList;
        this.notifyDataSetChanged();
    }
}
