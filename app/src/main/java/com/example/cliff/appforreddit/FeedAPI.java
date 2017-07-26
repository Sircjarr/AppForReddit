package com.example.cliff.appforreddit;

import com.example.cliff.appforreddit.Model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// This interface is used by Retrofit to make the GET request to the server

public interface FeedAPI {

    String BASE_URL = "http://www.reddit.com/r/";

    // Non-static calls
    @GET("{feed_name}/.rss")
    Call<Feed> getFeed(@Path("feed_name") String feed_name);

    // Static calls
    // @GET("funny/.rss")
    // Call<Feed> getFeed();
}
