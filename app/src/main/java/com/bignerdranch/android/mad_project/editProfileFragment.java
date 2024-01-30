package com.bignerdranch.android.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfileFragment extends Fragment {
    EditText et_editFullName, et_editUserName, et_editBio;
    CircleImageView ci_editPicture;
    String username, fullName, bio, imageUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        getArgumentsFromProfileFrag();
        setTexts();
    }

    private void findViews(View view) {
        et_editUserName = view.findViewById(R.id.et_editUserName);
        et_editFullName = view.findViewById(R.id.et_editFullName);
        et_editBio = view.findViewById(R.id.et_editBio);
        ci_editPicture = view.findViewById(R.id.ci_editPicture);
    }

    private void setTexts() {
        et_editUserName.setText(username);
        et_editFullName.setText(fullName);
        et_editBio.setText(bio);
        Glide.with(requireContext())
                .load(imageUrl)
                .into(ci_editPicture); // Target CircleImageView
    }

    private void getArgumentsFromProfileFrag() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            fullName = bundle.getString("fullName");
            bio = bundle.getString("bio");
            imageUrl = bundle.getString("imageUrl");
        }
    }
}