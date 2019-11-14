package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jupa.R;

public class SearchActivity extends AppCompatActivity {

    Boolean byName, byCountry, byState, byCity, byRank, byGroup, byInstitution;

    public static String BY_NAME_EXTRA="by_name", BY_COUNTRY_EXTRA = "byCountry",
                         BY_STATE_EXTRA = "byState", BY_CITY_EXTRA="byCity",BY_RANK_EXTRA = "by_rank",
                        BY_GROUP_EXTRA = "by_group", BY_INSTITUTION_EXTRA="byInstitution";

    Intent intent;
    LinearLayout name_layout, country_layout, state_layout, city_layout, rank_layout, group_layout, institution_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        intent = getIntent();
        byName = intent.getBooleanExtra(BY_NAME_EXTRA,false);
        byCountry = intent.getBooleanExtra(BY_COUNTRY_EXTRA,false);
        byState = intent.getBooleanExtra(BY_STATE_EXTRA,false);
        byCity = intent.getBooleanExtra(BY_CITY_EXTRA,false);
        byRank = intent.getBooleanExtra(BY_RANK_EXTRA,false);
        byGroup = intent.getBooleanExtra(BY_GROUP_EXTRA,false);
        byInstitution = intent.getBooleanExtra(BY_INSTITUTION_EXTRA,false);

        name_layout = (LinearLayout)findViewById(R.id.by_name_layout);
        country_layout = (LinearLayout)findViewById(R.id.by_country_layout);
        state_layout = (LinearLayout)findViewById(R.id.by_state_layout);
        city_layout = (LinearLayout)findViewById(R.id.by_city_layout);
        rank_layout = (LinearLayout)findViewById(R.id.by_rank_layout);
        group_layout = (LinearLayout)findViewById(R.id.by_group_layout);
        institution_layout = (LinearLayout)findViewById(R.id.by_institution_layout);

        setLayout();

    }

    public void setLayout(){

        if (byName){

            showLayout(name_layout);

        }else if(byCountry){

            showLayout(country_layout);

        }else if(byState){

            showLayout(state_layout);

        }else if(byCity){

            showLayout(city_layout);

        }else if(byRank){

            showLayout(rank_layout);

        }else if(byGroup){

            showLayout(group_layout);

        }else if(byInstitution){

            showLayout(institution_layout);

        }

    }

    public void showLayout(LinearLayout linearLayout){

        linearLayout.setVisibility(View.VISIBLE);

    }


}
