package com.bignerdranch.android.mad_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.common.reflect.TypeToken;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragment extends Fragment {
    ImageView icon_menu;
    NavigationView navigationView;
    private View bottomSheetView;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tv_profileUsername, tv_profileFullName, tv_bio;
    private CircleImageView ci_ProfilePhoto;
    private AppCompatButton btn_editProfile;
    private Bundle bundle;
    Task<DataSnapshot> dbRef;
    RecyclerView rv_profilePost;
    private DatabaseReference databaseReference;
    profilePostRVAdapter adapter;
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
        editProfile();
        addDialogBox();
        getDataFromSharedPref();
        getPost();
    }

    private void getPost() {
        rv_profilePost.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser != null ? currentUser.getUid() : "";

        Query query = FirebaseDatabase.getInstance().getReference("posts")
                .orderByChild("userId")
                .equalTo(currentUserId);

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();

        adapter =new profilePostRVAdapter(options, requireContext());
        rv_profilePost.setAdapter(adapter);
    }

    private void getDataFromSharedPref() {
        SharedPreferences sharedPref = requireContext().getSharedPreferences("myPrefs", requireContext().MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String fullName = sharedPref.getString("fullName", "");
        String bio = sharedPref.getString("bio", "");
        String imageURL = sharedPref.getString("imageURL", "");
        //Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show();
        setUserProfileData(username, fullName, bio, imageURL);
        putDataInBundle(username,fullName, bio, imageURL);
    }

    private void editProfile() {
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new editProfileFragment();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            }
        });
    }

    private void setUserProfileData(String name,String fullName, String bio, String imageUrl) {
        tv_profileUsername.setText(name);
        tv_profileFullName.setText(fullName);
        tv_bio.setText(bio);
        Glide.with(requireContext())
                .load(imageUrl)
                .into(ci_ProfilePhoto); // Target CircleImageView
    }

    private void putDataInBundle(String name,String fullName, String bio, String imageUrl) {
        bundle = new Bundle();
        bundle.putString("username", name);
        bundle.putString("fullName", fullName);
        bundle.putString("bio", bio);
        bundle.putString("imageUrl", imageUrl);
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
        tv_profileFullName = view.findViewById(R.id.tv_profileFullName);
        tv_profileUsername = view.findViewById(R.id.tv_profileUsername);
        ci_ProfilePhoto = view.findViewById(R.id.ci_ProfilePhoto);
        tv_bio = view.findViewById(R.id.tv_bio);
        btn_editProfile = view.findViewById(R.id.btn_editProfile);
        rv_profilePost = view.findViewById(R.id.rv_profilePost);
    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromSharedPref(); // Reload data from SharedPreferences
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