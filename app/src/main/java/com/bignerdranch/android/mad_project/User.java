package com.bignerdranch.android.mad_project;

public class User {
    private String id;
    private String username;
    private String fullName;
    private String bio;
    private String imageUrl;

    public User() {
    }

    public User(String id, String username, String fullName, String bio, String imageUrl) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBio() {
        return bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
