package com.bignerdranch.android.mad_project;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class mainFragment extends Fragment {
    private BottomNavigationView bnView;
    Task<DataSnapshot> dbRef;

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
       // bnView.setSelectedItemId(R.id.icon_home);
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
                return true;
            }
        });
        getUserProfileData();

    }
    private void getUserProfileData() {
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
            String userId = firebaseuser.getUid();
            dbRef = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(requireContext(), "yeehaww", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = dataSnapshot.child("username").getValue(String.class);
                        String fullName = dataSnapshot.child("fullName").getValue(String.class);
                        String bio = dataSnapshot.child("bio").getValue(String.class);
                        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                        putDataInsharedPrefernces(name, fullName, bio, imageUrl);
                    }
                    else{
                        Toast.makeText(requireContext(), ":(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void putDataInsharedPrefernces(String username, String fullName, String bio, String imageUrl) {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("myPrefs", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("fullName", fullName);
        editor.putString("bio", bio);
        editor.putString("imageURL", imageUrl);
        editor.apply();
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