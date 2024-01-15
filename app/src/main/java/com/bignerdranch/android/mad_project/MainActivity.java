package com.bignerdranch.android.mad_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.bignerdranch.android.mad_project.joinInstaFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        ft = fm .beginTransaction();
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            mainFragment fragment = new mainFragment();
            ft.add(R.id.fragments, fragment, "instagram");
            //fm.popBackStack("instagram", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.commit();
        }
        else{
            joinInstaFragment fragment = new joinInstaFragment();
            ft.add(R.id.fragments, fragment, "instagram");
            fm.popBackStack("instagram", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.commit();
        }
    }
}