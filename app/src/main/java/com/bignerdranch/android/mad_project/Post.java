package com.bignerdranch.android.mad_project;

public class Post {
    String imageUrl;

    public Post() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Post(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
