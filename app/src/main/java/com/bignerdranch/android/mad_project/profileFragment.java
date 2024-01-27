package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

public class profileFragment extends Fragment {
    ImageView icon_menu;
    NavigationView navigationView;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        addDialogBox();


    }

    private void addDialogBox() {
        icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeBottomDialogBox();
                navigationView = bottomSheetView.findViewById(R.id.navigationView);
                openNewFragment();
            }
        });
    }

    private void openNewFragment() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.icon_settings){
                    replaceFragment(new settingsFragment());
                    bottomSheetDialog.dismiss();
                }
                return true;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.userFragments, fragment , "settings")
                .addToBackStack(null)
                .commit();
    }

    private void initializeBottomDialogBox() {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_bottom_dialog_navigation, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
    }
    private void findViews(View view) {
        icon_menu = view.findViewById(R.id.icon_menu);
    }

}