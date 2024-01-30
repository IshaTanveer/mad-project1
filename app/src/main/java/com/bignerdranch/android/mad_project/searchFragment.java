package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DatabaseReference;

public class searchFragment extends Fragment {

    private UserAdapter userAdapter;
    private RecyclerView rvSearchUser;
    private DatabaseReference databaseReference;
    private androidx.appcompat.widget.SearchView svSearchUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rvSearchUser = view.findViewById(R.id.rvSearchUser);
        svSearchUser = view.findViewById(R.id.svSearchUser);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        rvSearchUser.setLayoutManager(new LinearLayoutManager(getContext()));
        svSearchUser.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search text changes
                if (newText.length() > 0) {
                    performSearch(newText);
                } else {
                    // If the search query is empty, show all users
                    loadAllUsers();
                }
                return true;
            }
        });

        // Initialize with all users
        loadAllUsers();

        return view;
    }

    private void performSearch(String query) {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference.orderByChild("username").startAt(query).endAt(query + "\uf8ff"), User.class)
                        .build();

        userAdapter = new UserAdapter(options);
        rvSearchUser.setAdapter(userAdapter);

        userAdapter.startListening();
    }

    private void loadAllUsers() {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(databaseReference, User.class)
                        .build();

        userAdapter = new UserAdapter(options);
        rvSearchUser.setAdapter(userAdapter);

        userAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (userAdapter != null) {
            userAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (userAdapter != null) {
            userAdapter.stopListening();
        }
    }
}
