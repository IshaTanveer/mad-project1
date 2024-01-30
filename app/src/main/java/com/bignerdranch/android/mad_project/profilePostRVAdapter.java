package com.bignerdranch.android.mad_project;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class profilePostRVAdapter extends FirebaseRecyclerAdapter<Post, profilePostRVAdapter.ViewHolder> {
    Context context;
    public profilePostRVAdapter(@NonNull FirebaseRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_postPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_postPhoto = itemView.findViewById(R.id.iv_postPhoto);
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull profilePostRVAdapter.ViewHolder holder, int position, @NonNull Post model) {
        String photo = model.getImageUrl();
        Glide.with(context)
                .load(photo)
                .into(holder.iv_postPhoto);
    }

    @NonNull
    @Override
    public profilePostRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_post_rv_layout, parent, false);
        return new ViewHolder(view);
    }
}
