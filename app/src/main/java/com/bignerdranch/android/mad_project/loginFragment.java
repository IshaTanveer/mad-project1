package com.bignerdranch.android.mad_project;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Pattern;

public class loginFragment extends Fragment {
    private TextInputEditText et_loginEmail, et_loginPassword;
    private AppCompatButton btn_login;
    private UserAccount userAccount = new UserAccount();
    private FragmentManager fm;
    private FragmentTransaction ft;

    public loginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login2, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fm = getParentFragmentManager();
        ft = fm .beginTransaction();
        initializeIds(view);
        getUserEmail();
        getUserPassword();
        setUpClickListener();

    }

    private void setUpClickListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userAccount.getEmail() == null)
                    Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                else if(userAccount.getPassword() == null)
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
                else if (userAccount.getEmail() != null && userAccount.getPassword() != null ) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();


                    mAuth.signInWithEmailAndPassword(userAccount.getEmail(), userAccount.getPassword())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Toast.makeText(getActivity(), "logged in", Toast.LENGTH_SHORT).show();
                                        Fragment mainFragment = new mainFragment();
                                        addFragment(mainFragment);
                                    } else {
                                        Toast.makeText(getActivity(), "wrong email or password.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }

        });
    }
    private void addFragment(Fragment fragment) {
        fm.popBackStack();
        ft.replace(R.id.fragments, fragment, "instagram");
        ft.commit();
    }
    private void getUserPassword() {
        et_loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAccount.setPassword(Objects.requireNonNull(et_loginPassword.getText()).toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getUserEmail() {
        et_loginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userAccount.setEmail(Objects.requireNonNull(et_loginEmail.getText()).toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initializeIds(View view) {
        et_loginEmail = view.findViewById(R.id.et_loginEmail);
        et_loginPassword = view.findViewById(R.id.et_loginPassword);
        btn_login = view.findViewById(R.id.btn_login);
    }
}