package com.bignerdranch.android.mad_project;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class searchUserAdapter extends FirebaseRecyclerAdapter<User, searchUserAdapter.UserViewHolder> {
    Context context;
    public searchUserAdapter(@NonNull FirebaseRecyclerOptions<User> options, Context context) {
        super(options);
        this.context= context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
       // holder.bind(model);
        String photo = model.getImageUrl();
        Glide.with(context)
               .load(photo)
                .into(holder.civProfile);
        holder.tvUsername.setText(model.getUsername());
        holder.tvFullName.setText(model.getFullName());
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

        public void bind(User user) {
            tvUsername.setText(user.getUsername());
            tvFullName.setText(user.getFullName());
            Glide.with(itemView.getContext())
                    .load(user.getImageUrl())
                    .into(civProfile);


        }
    }
}
