package com.bignerdranch.android.mad_project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class createAccountFragment extends Fragment {

    private TextInputEditText et_signupName, et_signupEmail, et_signupDob, et_signupPassword;
    private AppCompatButton btn_signup;
    private UserAccount userAccount = new UserAccount();
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener listener;
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
        //getUserDob();
        setUpClickListener();
        datePickerDialog();
        //alertDialog();

    }

    private void datePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        et_signupDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener, 2023, 12, 30);
                dateDialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getDate();
            }
        };
    }

    private void getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            userAccount.setDob(dateFormat.parse(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
                    Toast.makeText(getActivity(), userAccount.getDob().toString(), Toast.LENGTH_SHORT).show();
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