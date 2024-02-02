package com.bignerdranch.android.mad_project;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class viewOthersProfileFragment extends Fragment {
    private TextView tv_othersUsername, tv_othersFullName, tv_othersBio;
    private CircleImageView ci_othersProfilePhoto;
    private AppCompatButton btn_follow;
    RecyclerView rv_othersPost;
    private DatabaseReference databaseReference;
    profilePostRVAdapter adapter;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_others_profile, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        getDataFromSharedPref();
        checkIfFollowing();
        getPost();

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the function to handle the follow action
                followUser();
            }
        });
    }
    private void checkIfFollowing() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            DatabaseReference followingRef = FirebaseDatabase.getInstance().getReference("following").child(currentUserId);
            followingRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // The current user is already following this user
                        btn_follow.setText("Following");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error if needed
                }
            });
        }
    }
    private void followUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            // Reference to the current user's "following" node in the database
            DatabaseReference followingRef = FirebaseDatabase.getInstance().getReference("following").child(currentUserId);

            // Add the user with 'id' to the current user's following list
            followingRef.child(id).setValue(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Update UI or show a success message
                    btn_follow.setText("Following");
                    Toast.makeText(requireContext(), "You are now following this user.", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where the follow operation failed
                    Toast.makeText(requireContext(), "Failed to follow this user. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Handle the case where the current user is not logged in.
            Toast.makeText(requireContext(), "You need to be logged in to follow users.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPost() {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show();
        rv_othersPost.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        Query query = FirebaseDatabase.getInstance().getReference("posts")
                .orderByChild("userId")
                .equalTo(id);
        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();

        adapter =new profilePostRVAdapter(options, requireContext());
        rv_othersPost.setAdapter(adapter);
    }
    private void getDataFromSharedPref() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("othersPrefs", requireContext().MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String fullName = sharedPref.getString("fullName", "");
        String bio = sharedPref.getString("bio", "");
        String imageURL = sharedPref.getString("imageURL", "");
        id = sharedPref.getString("id", "");
        //Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show();
        setUserProfileData(username, fullName, bio, imageURL);
        //putDataInBundle(username,fullName, bio, imageURL);
    }

    private void setUserProfileData(String username, String fullName, String bio, String imageURL) {
        tv_othersUsername.setText(username);
        tv_othersFullName.setText(fullName);
        tv_othersBio.setText(bio);
        Glide.with(requireContext())
                .load(imageURL)
                .into(ci_othersProfilePhoto); // Target CircleImageView
    }

    private void findViews(View view) {
        tv_othersUsername = view.findViewById(R.id.tv_othersUsername);
        tv_othersFullName = view.findViewById(R.id.tv_othersFullName);
        tv_othersBio = view.findViewById(R.id.tv_othersBio);
        btn_follow = view.findViewById(R.id.btn_follow);
        ci_othersProfilePhoto = view.findViewById(R.id.ci_othersProfilePhoto);
        rv_othersPost = view.findViewById(R.id.rv_othersPost);
    }
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}