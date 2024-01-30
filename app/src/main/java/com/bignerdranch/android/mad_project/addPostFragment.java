package com.bignerdranch.android.mad_project;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Objects;

public class addPostFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    String imageUrl;
    StorageTask uploadTask;
    StorageReference storageReference;
    EditText et_caption;
    ImageView iv_close, iv_addPost;
    TextView tv_post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        et_caption = view.findViewById(R.id.et_caption);
        iv_addPost = view.findViewById(R.id.iv_addPost);
        iv_close = view.findViewById(R.id.iv_close);
        tv_post = view.findViewById(R.id.tv_post);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseApp.initializeApp(requireActivity());


        storageReference = FirebaseStorage.getInstance().getReference("posts");

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeFragment = new homeFragment();
                addFragment(homeFragment);
            }
        });

        iv_addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    uploadPost(currentUser.getUid());

                } else {
                    Snackbar.make(v, "Select an image first", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void addFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.userFragments, fragment , "Add post")
                .commit();
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(requireContext())
                    .load(imageUri)
                    .into(iv_addPost);
        }
    }
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageuri = data.getData();
            iv_addPost.setImageURI(imageuri);
        }
    }*/

    private void uploadPost(String userId) {
        String caption = et_caption.getText().toString().trim();
        if (!caption.isEmpty()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
            String postId = databaseReference.push().getKey();

            // Include user ID in post data
            databaseReference.child(Objects.requireNonNull(postId)).child("userId").setValue(userId);
            databaseReference.child(postId).child("imageUrl").setValue(imageUri.toString());
            databaseReference.child(postId).child("caption").setValue(caption);

            Toast.makeText(getActivity(), "Post uploaded successfully", Toast.LENGTH_SHORT).show();
            Fragment homeFragment = new homeFragment();
            addFragment(homeFragment);

        } else {
            Toast.makeText(getActivity(), "Caption cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

}