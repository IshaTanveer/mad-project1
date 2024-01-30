package com.bignerdranch.android.mad_project;

import android.app.DatePickerDialog;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class createAccountFragment extends Fragment {

    private TextInputEditText et_signupUserName, et_signupEmail, et_signupDob, et_signupPassword;
    private AppCompatButton btn_signup;
    private UserAccount userAccount = new UserAccount();
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener listener;
    private DatabaseReference reference;
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
        datePickerDialog();
        setUpClickListener();

    }

    private void datePickerDialog() {
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
                userAccount.setDob(String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth));
                et_signupDob.setText(userAccount.getDob());
            }
        };
    }

    private void getUserPassword() {
        et_signupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAccount.setEmail(Objects.requireNonNull(et_signupEmail.getText()).toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpClickListener() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userAccount.getName() == null)
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
                else if(userAccount.getEmail() == null)
                    Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                else if(!isValidEmail(userAccount.getEmail()))
                    Toast.makeText(getActivity(), "not a valid mail", Toast.LENGTH_SHORT).show();
                else if(userAccount.getDob() == null)
                    Toast.makeText(getActivity(), "Enter DOB", Toast.LENGTH_SHORT).show();
                else if(userAccount.getPassword() == null)
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
                else if(userAccount.getPassword().length() < 8)
                    Toast.makeText(getActivity(), "password must have 8 characters", Toast.LENGTH_SHORT).show();
                else if (userAccount.getName() != null && userAccount.getEmail() != null && userAccount.getPassword() != null && userAccount.getDob() != null) {
                    createAccount();
                }

            }
        });
    }

    private void createAccount() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(userAccount.getEmail(), userAccount.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(userid);
                            String username = userAccount.getName();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id" ,userid);
                            hashMap.put("username" ,username.toLowerCase());
                            hashMap.put("fullName" ," ");
                            hashMap.put("bio" ," ");
                            hashMap.put("imageUrl" ,"https://firebasestorage.googleapis.com/v0/b/mad-project-7ed3f.appspot.com/o/Default_pfp.jpg?alt=media&token=ef6bc5f2-6a80-4487-a496-15b8fc7003bd ");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Account created.", Toast.LENGTH_SHORT).show();
                                        Fragment loginFragment = new loginFragment();
                                        addFragment(loginFragment);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void addFragment(Fragment fragment) {
        getParentFragmentManager().popBackStack("createAccount", 0);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragments, fragment , "create Account")
                .commit();
    }
    public static boolean isValidEmail(CharSequence target) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "gmail.com";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(target).matches();
    }

    private void getUserName() {
        et_signupUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAccount.setName(Objects.requireNonNull(et_signupUserName.getText()).toString().trim());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    private void initializeIds(View view) {
        et_signupUserName = view.findViewById(R.id.et_signupName);
        et_signupEmail = view.findViewById(R.id.et_signupEmail);
        et_signupDob = view.findViewById(R.id.et_signupDob);
        et_signupPassword = view.findViewById(R.id.et_signupPassword);
        btn_signup = view.findViewById(R.id.btn_signup);

    }
}