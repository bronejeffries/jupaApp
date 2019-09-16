package com.example.jupa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    public static final String GROUP_TAG = "group" ;
    Group group;
    RegisteredCandidates registeredCandidates;
    final static String MASON_CATEGORY = "mason", PROFESSIONAL_CATEGORY = "professional";
    RecyclerView masonry_recycler_view , professional_recycler_view;
    TextView masonry_title, professional_title;
    CardView masonry_card, professional_card;
    Boolean masonryCard_visible = true, professionalCard_visible = true;

    ArrayList<Candidate> GroupCandidates,  masonryCandidates, professionalCandidates;
    private CandidatesAdapter masonCandidatesAdapter;
    private CandidatesAdapter professionalCandidatesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();
        group = (Group)intent.getParcelableExtra(GROUP_TAG);
        registeredCandidates = RegisteredCandidates.getInstance();
        GroupCandidates = registeredCandidates.filterListByGroup(group.getGroup_name());
        masonry_recycler_view = (RecyclerView)findViewById(R.id.masonry_recycler_view);
        professional_recycler_view = (RecyclerView)findViewById(R.id.professional_recycler_view);

        masonry_title = (TextView)findViewById(R.id.masonry);
        masonry_card = (CardView)findViewById(R.id.mansory_card_view);

        masonry_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (masonryCard_visible){
                    masonry_card.setVisibility(View.GONE);
                    masonryCard_visible = false;
                }else {
                    masonry_card.setVisibility(View.VISIBLE);
                    masonryCard_visible = true;
                }
            }
        });

        professional_title = (TextView)findViewById(R.id.professional);
        professional_card = (CardView)findViewById(R.id.professional_card_view);

        professional_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (professionalCard_visible){

                    professional_card.setVisibility(View.GONE);
                    professionalCard_visible = false;
                }else {

                    professional_card.setVisibility(View.VISIBLE);
                    professionalCard_visible = true;

                }

            }
        });

        populateMasonCandidates(filterByCategory(GroupCandidates,MASON_CATEGORY));
        populateProfessionalCandidates(filterByCategory(GroupCandidates,PROFESSIONAL_CATEGORY));
    }

    private void populateProfessionalCandidates(ArrayList<Candidate> professionals) {

        professionalCandidatesAdapter = new CandidatesAdapter(this,professionals);
        professional_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        professional_recycler_view.setAdapter(professionalCandidatesAdapter);

    }

    private void populateMasonCandidates(ArrayList<Candidate> masons) {
        masonCandidatesAdapter = new CandidatesAdapter(this,masons);
        masonry_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        masonry_recycler_view.setAdapter(masonCandidatesAdapter);
    }


    public static ArrayList<Candidate> filterByCategory(ArrayList<Candidate> candidateArrayList , String categoryName){

        ArrayList<Candidate> filteredList = new ArrayList<>();

        for (Candidate candidate : candidateArrayList ) {

            if (candidate.getCategory() != null ){

                if (candidate.getCategory().equals(categoryName)){

                    filteredList.add(candidate);

                }

            }

        }

        return filteredList;

    }


}
