package com.example.jupa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jupa.Fragments.HomeFragment;
import com.example.jupa.Fragments.LoginOptionsFragment;
import com.example.jupa.Fragments.SearchOptionsFragment;
import com.example.jupa.Fragments.SignUpOptionsFragment;
import com.example.jupa.R;

public class MainActivity extends AppCompatActivity {


    SearchOptionsFragment searchOptionsFragment;
    LoginOptionsFragment loginOptionsFragment;
    SignUpOptionsFragment signUpOptionsFragment;
    HomeFragment homeFragment;
    private FragmentTransaction fragmentTransaction;
    ImageButton homeButton;
    Button SearchButton,LoginButton, RegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchOptionsFragment = new SearchOptionsFragment();
        loginOptionsFragment = new LoginOptionsFragment();
        signUpOptionsFragment = new SignUpOptionsFragment();
        homeFragment = new HomeFragment();

        homeButton = (ImageButton)findViewById(R.id.home_button);
        SearchButton = (Button)findViewById(R.id.search_button);
        LoginButton = (Button)findViewById(R.id.sigin_button);
        RegisterButton = (Button)findViewById(R.id.signUpOptions);

        setFragment(homeFragment,"Home");
        MarkButtonClicked(homeButton);

    }


    public void setFragment(Fragment fragment,String title){

        setTitle(title);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frag_container,fragment);
        fragmentTransaction.commit();

    }

    public void MarkButtonClicked(View view){

        view.setBackgroundColor( getResources().getColor(R.color.white_trans));

    }


    public void UnMarkButtonClicked(View view){

        view.setBackground(getResources().getDrawable(R.drawable.button_primary));

    }


    public void searchClicked(View view){

        setFragment(searchOptionsFragment,"Search");
        MarkButtonClicked(SearchButton);

        UnMarkButtonClicked(homeButton);
        UnMarkButtonClicked(LoginButton);
        UnMarkButtonClicked(RegisterButton);

    }

    public void loginOptionClicked(View view){

        setFragment(loginOptionsFragment,"Sign In");
        MarkButtonClicked(LoginButton);
        UnMarkButtonClicked(SearchButton);
        UnMarkButtonClicked(homeButton);
        UnMarkButtonClicked(RegisterButton);

    }

    public void signUpOptionClicked(View view){

        setFragment(signUpOptionsFragment,"Sign Up");
        MarkButtonClicked(RegisterButton);
        UnMarkButtonClicked(SearchButton);
        UnMarkButtonClicked(LoginButton);
        UnMarkButtonClicked(homeButton);

    }

    public void homeClicked(View view){

        setFragment(homeFragment,"Home");
        MarkButtonClicked(homeButton);
        UnMarkButtonClicked(SearchButton);
        UnMarkButtonClicked(LoginButton);
        UnMarkButtonClicked(RegisterButton);

    }




    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }


}
