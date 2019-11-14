package com.example.jupa.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jupa.Activity.CandidateLoginActivity;
import com.example.jupa.Activity.InstitutionLoginActivity;
import com.example.jupa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginOptionsFragment extends Fragment {


    View view;
    CardView candidateCard, AssessorCard, AssociationCard, InstitutionCard, AdministratorCard;

    public LoginOptionsFragment() {

        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login_options, container, false);
        candidateCard = (CardView)view.findViewById(R.id.candidate_login);
        AssessorCard = (CardView)view.findViewById(R.id.assessor_login);
        AssociationCard = (CardView)view.findViewById(R.id.association_login);
        InstitutionCard = (CardView)view.findViewById(R.id.institution_login);
        AdministratorCard = (CardView)view.findViewById(R.id.administration_login);

        candidateCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginClicked();
            }
        });

        AssessorCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginClicked();
            }
        });

        AssociationCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginClicked();
            }
        });


        AdministratorCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LoginClicked();
            }
        });

        InstitutionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InstitutionLoginClicked();

            }
        });

        return view;

    }

    public void LoginClicked(){
        Intent intent = new Intent(getContext(), CandidateLoginActivity.class);
        startActivity(intent);

    }

    public void InstitutionLoginClicked(){

        Intent intent = new Intent(getContext(), InstitutionLoginActivity.class);
        startActivity(intent);

    }


}
