package com.bignerdranch.android.mad_project;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class editProfileFragment extends Fragment {
    EditText et_editFullName, et_editUserName, et_editBio;
    CircleImageView ci_editPicture;
    String username, fullName, bio, imageUrl;
    AppCompatButton btn_save;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    String photoUrl;
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
        getImage();
        editProfile();
    }

    private void editProfile() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_editUserName.getText().toString().trim();
                String fullName = et_editFullName.getText().toString().trim();
                String bio = et_editBio.getText().toString().trim();
                updateDB(username, fullName, bio, String.valueOf(imageUri));
            }
        });
    }

    private void getImage() {
        ci_editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }

    private void putDataInsharedPrefernces(String username, String fullName, String bio, String photoUrl) {
        //SharedPreferences sharedPref = requireActivity().getPreferences(requireContext().MODE_PRIVATE);
        SharedPreferences sharedPref = requireContext().getSharedPreferences("myPrefs", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("fullName", fullName);
        editor.putString("bio", bio);
        editor.putString("imageURL", photoUrl);
        editor.apply();
    }
    private void updateDB(String username, String fullName, String bio, String photoUrl) {
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
            HashMap<String , Object> data = new HashMap<>();
            data.put("username", username);
            data.put("fullName", fullName);
            data.put("bio", bio);
            data.put("imageUrl", photoUrl);
            String userId = firebaseuser.getUid();
            Task<Void> dbRef = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(userId)
                    .updateChildren(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show();
                            putDataInsharedPrefernces(username, fullName, bio, photoUrl);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    private void findViews(View view) {
        et_editUserName = view.findViewById(R.id.et_editUserName);
        et_editFullName = view.findViewById(R.id.et_editFullName);
        et_editBio = view.findViewById(R.id.et_editBio);
        ci_editPicture = view.findViewById(R.id.ci_editPicture);
        btn_save = view.findViewById(R.id.btn_save);
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
   @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK && data != null && data.getData() != null) {
           imageUri = data.getData();
           Glide.with(requireContext())
                   .load(imageUri)
                   .into(ci_editPicture);
       }
   }
}