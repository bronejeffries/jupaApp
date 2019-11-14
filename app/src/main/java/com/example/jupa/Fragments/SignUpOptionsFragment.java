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
import com.example.jupa.Activity.NewInstitutionActivity;
import com.example.jupa.Activity.RegistrationActivity;
import com.example.jupa.Institution.Institution;
import com.example.jupa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpOptionsFragment extends Fragment {


    View view;
    CardView candidateCard, AssessorCard, AssociationCard, InstitutionCard;

    public SignUpOptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_sign_up_options, container, false);
        candidateCard = (CardView)view.findViewById(R.id.candidate_signup);
        AssessorCard = (CardView)view.findViewById(R.id.assessor_signup);
        AssociationCard = (CardView)view.findViewById(R.id.association_signup);
        InstitutionCard = (CardView)view.findViewById(R.id.institution_signup);

        candidateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterClicked();

            }
        });

        AssessorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterClicked();

            }
        });

        AssociationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterClicked();

            }
        });

        InstitutionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InstitutionRegisterClicked();

            }
        });

        return view;

    }


    public void RegisterClicked(){
        Intent intent = new Intent(getContext(), RegistrationActivity.class);
        startActivity(intent);

    }

    public void InstitutionRegisterClicked(){

        Intent intent = new Intent(getContext(), NewInstitutionActivity.class);
        startActivity(intent);

    }


}
