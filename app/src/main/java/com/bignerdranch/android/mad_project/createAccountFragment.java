package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class createAccountFragment extends Fragment {

    private TextInputEditText et_signupName, et_signupEmail, et_signupDob, et_signupPassword;
    private AppCompatButton btn_signup;
    private UserAccount userAccount = new UserAccount();
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
        getUserName();
        getUserEmail();
        getUserPassword();
        getUserDob();
        setUpClickListener();

    }

    private void getUserDob() {
        et_signupDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getUserPassword() {
        et_signupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAccount.setPassword(Objects.requireNonNull(et_signupPassword.getText()).toString().trim());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getUserEmail() {
        et_signupEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAccount.setEmail(Objects.requireNonNull(et_signupEmail.getText()).toString().trim());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setUpClickListener() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), userAccount.getPassword(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserName() {
        et_signupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 userAccount.setName(Objects.requireNonNull(et_signupName.getText()).toString().trim());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void initializeIds(View view){
        et_signupName = view.findViewById(R.id.et_signupName);
        et_signupEmail = view.findViewById(R.id.et_signupEmail);
        et_signupDob = view.findViewById(R.id.et_signupDob);
        et_signupPassword = view.findViewById(R.id.et_signupPassword);
        btn_signup = view.findViewById(R.id.btn_signup);

    }
}