package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class joinInstaFragment extends Fragment {
    private AppCompatButton btn_createAccount;
    private TextView tv_login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_insta, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addCreateAccountFrag();
         addloginFrag();
    }

    private void addCreateAccountFrag() {
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment createAccountFragment = new createAccountFragment();
                addFragment(createAccountFragment);
            }
        });
    }
    private void addloginFrag() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new loginFragment();
                addFragment(loginFragment);
            }
        });
    }

    private void addFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragments, fragment , "create Account")
                .addToBackStack(null)
                .commit();
    }
    public void init(View view){
        btn_createAccount = view.findViewById(R.id.btn_createAccount);
        tv_login = view.findViewById(R.id.tv_login);
    }

}