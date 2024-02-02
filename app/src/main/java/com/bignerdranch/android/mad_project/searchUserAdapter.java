package com.bignerdranch.android.mad_project;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class searchUserAdapter extends FirebaseRecyclerAdapter<User, searchUserAdapter.UserViewHolder> {
    Context context;
    FragmentManager fragmentManager;

    public searchUserAdapter(@NonNull FirebaseRecyclerOptions<User> options, Context context, FragmentManager fragmentManager) {
        super(options);
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
        String photo = model.getImageUrl();
        Glide.with(context)
                .load(photo)
                .into(holder.civProfile);
        holder.tvUsername.setText(model.getUsername());
        holder.tvFullName.setText(model.getFullName());
        openOthersProfile(holder.itemView, model);
    }

    private void openOthersProfile(View itemView, User model) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putDataInSharedPreferences(model);
                replaceFragment(new viewOthersProfileFragment());
            }
        });
    }

    private void putDataInSharedPreferences(User model) {
        SharedPreferences sharedPref = context.getSharedPreferences("othersPrefs", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", model.getUsername());
        editor.putString("fullName", model.getFullName());
        editor.putString("bio", model.getBio());
        editor.putString("imageURL", model.getImageUrl());
        editor.putString("id", model.getId());
        editor.apply();
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.userFragments, fragment, "settings")
                .addToBackStack(null)
                .commit();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_layout_design, parent, false);
        return new UserViewHolder(view);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvFullName;
        private final CircleImageView civProfile;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            civProfile = itemView.findViewById(R.id.civProfile);
        }
    }
}
