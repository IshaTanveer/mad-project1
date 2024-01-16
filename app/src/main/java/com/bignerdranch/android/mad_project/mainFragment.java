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

import com.bignerdranch.android.mad_project.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class mainFragment extends Fragment {
    private BottomNavigationView bnView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bnView = view.findViewById(R.id.bottomNavigation);
        bnView.setSelectedItemId(R.id.icon_home);
        addFragment(new homeFragment());
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.icon_search)
                    replaceFragment(new searchFragment());
                else if (id == R.id.icon_addPost)
                    replaceFragment(new addPostFragment());
                else if (id == R.id.icon_reels)
                    replaceFragment(new reelsFragment());
                else if (id == R.id.icon_profile)
                    replaceFragment(new profileFragment());
                else if (id == R.id.icon_home)
                    addFragment(new homeFragment());
                return false;
            }
        });

    }
    private void addFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .add(R.id.userFragments, fragment , "create Account")
                .commit();
    }
    private void replaceFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.userFragments, fragment , "create Account")
                .commit();
    }
}