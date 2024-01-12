package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class joinInstaFragment extends Fragment {
    private AppCompatButton btn_createAccount;

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
    }

    private void addCreateAccountFrag() {
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragments, new createAccountFragment(), "create Account")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    public void init(View view){
        btn_createAccount = view.findViewById(R.id.btn_createAccount);
    }

}