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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
        getPost();
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