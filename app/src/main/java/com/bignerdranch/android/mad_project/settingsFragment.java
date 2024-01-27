package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class settingsFragment extends Fragment {
    NavigationView settingsView;
    TextView logout;
    private FirebaseAuth mAuth;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fm = getParentFragmentManager();
        ft = fm .beginTransaction();
        findViews(view);
        logingout();
        openNewFragment();
    }

    private void logingout() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                replaceFragment(new loginFragment());
            }
        });
    }
    private void logout() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }
    private void openNewFragment() {
        settingsView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.icon_notifications){
                    //replaceFragment(new settingsFragment());
                    Toast.makeText(requireContext(), "notofications", Toast.LENGTH_SHORT).show();
                }
                else if (id == R.id.icon_account){
                    //replaceFragment(new settingsFragment());
                    Toast.makeText(requireContext(), "notofications", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        fm.popBackStack();
        ft.replace(R.id.fragments, fragment, "instagram");
        ft.commit();
    }
    private void findViews(View view) {
        settingsView = view.findViewById(R.id.settingsView);
        logout = view.findViewById(R.id.logout);
    }
}