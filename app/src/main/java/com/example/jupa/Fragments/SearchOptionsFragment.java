package com.example.jupa.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jupa.Activity.SearchActivity;
import com.example.jupa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchOptionsFragment extends Fragment {

    View view;

    CardView name_card, country_card, state_card, city_card, rank_card, group_card, institution_card;

    public SearchOptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search_options, container, false);

        name_card = (CardView) view.findViewById(R.id.name_card);
        country_card = (CardView) view.findViewById(R.id.country_card);
        state_card = (CardView) view.findViewById(R.id.state_card);
        city_card = (CardView)view.findViewById(R.id.city_card);
        rank_card = (CardView)view.findViewById(R.id.rank_card);
        group_card = (CardView)view.findViewById(R.id.group_card);
        institution_card = (CardView)view.findViewById(R.id.institution_card);

        name_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent name_intent = new Intent(getContext(), SearchActivity.class);
                name_intent.putExtra(SearchActivity.BY_NAME_EXTRA,true);
                startActivity(name_intent);

            }
        });

        country_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent country_intent = new Intent(getContext(), SearchActivity.class);
                country_intent.putExtra(SearchActivity.BY_COUNTRY_EXTRA,true);
                startActivity(country_intent);

            }
        });

        state_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent state_intent = new Intent(getContext(), SearchActivity.class);
                state_intent.putExtra(SearchActivity.BY_STATE_EXTRA,true);
                startActivity(state_intent);

            }
        });

        city_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent city_intent = new Intent(getContext(), SearchActivity.class);
                city_intent.putExtra(SearchActivity.BY_CITY_EXTRA,true);
                startActivity(city_intent);

            }
        });

        rank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rank_intent = new Intent(getContext(), SearchActivity.class);
                rank_intent.putExtra(SearchActivity.BY_RANK_EXTRA,true);
                startActivity(rank_intent);

            }
        });

        group_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent group_intent = new Intent(getContext(), SearchActivity.class);
                group_intent.putExtra(SearchActivity.BY_GROUP_EXTRA,true);
                startActivity(group_intent);

            }
        });

        institution_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent institution_intent = new Intent(getContext(), SearchActivity.class);
                institution_intent.putExtra(SearchActivity.BY_INSTITUTION_EXTRA,true);
                startActivity(institution_intent);

            }
        });

        return view;

    }



}
