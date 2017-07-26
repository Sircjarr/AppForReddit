package com.example.cliff.appforreddit;

// Each item in the ListView has these elements

public class SubredditPost {

    private String title;
    private String author;
    private String date_updated;
    private String postUrl;
    private String thumbnailUrl;

    public SubredditPost(String title, String author, String date_updated, String post_url, String thumbnail_url) {
        this.title = title;
        this.author = author;
        this.date_updated = date_updated;
        this.postUrl = post_url;
        this.thumbnailUrl = thumbnail_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getPost_url() {
        return postUrl;
    }

    public void setPost_url(String post_url) {
        this.postUrl = post_url;
    }

    public String getThumbnail_url() {
        return thumbnailUrl;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnailUrl = thumbnail_url;
    }
}
