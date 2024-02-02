package com.bignerdranch.android.mad_project;

public class Post {
    String imageUrl;
    String postId;
    String userId;
    String caption;

    public Post(String imageUrl, String postId, String userId, String caption) {
        this.imageUrl = imageUrl;
        this.postId = postId;
        this.userId = userId;
        this.caption = caption;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCaption() {
        return caption;
    }

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
