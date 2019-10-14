package com.example.jupa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.jupa.Fragments.InstitutionLoginFragment;
import com.example.jupa.Fragments.LoginFragment;
import com.example.jupa.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginPromptActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    LoginFragment loginFragment;
    InstitutionLoginFragment institutionLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prompt);


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        loginFragment = new LoginFragment();
        institutionLoginFragment = new InstitutionLoginFragment();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id){

                    case R.id.candidate_login:
                        setFragment(loginFragment);
                        return true;
                    case R.id.institution_login:
                        setFragment(institutionLoginFragment);
                        return true;
                }

                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.candidate_login);

    }


    public void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frag_container,fragment);
        fragmentTransaction.commit();


    }


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

    }

}
