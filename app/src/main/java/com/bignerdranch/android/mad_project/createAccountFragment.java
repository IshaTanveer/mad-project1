package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

public class createAccountFragment extends Fragment {

    private TextInputEditText et_signupName, et_signupEmail, et_signupDob, et_signupPassword;
    private AppCompatButton btn_signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeIds(view);

    }
    private void initializeIds(View view){
        et_signupName = view.findViewById(R.id.et_signupName);
        et_signupEmail = view.findViewById(R.id.et_signupEmail);
        et_signupDob = view.findViewById(R.id.et_signupDob);
        et_signupPassword = view.findViewById(R.id.et_signupPassword);
        btn_signup = view.findViewById(R.id.btn_signup);
    }
}