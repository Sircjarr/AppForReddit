package com.example.cliff.appforreddit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cliff.appforreddit.Model.Entry;
import com.example.cliff.appforreddit.Model.Feed;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://www.reddit.com/r/";

    EditText feedEditText;
    String currentFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedEditText = (EditText) findViewById(R.id.feedEditText);
    }

    public void refreshFeed(View view) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        currentFeed = feedEditText.getText().toString();
        if (currentFeed.trim().length() > 0) {
            init();
        }
    }

    public void init() {

        // Make an API call with Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // Convert XML into a readable format. EXAMPLE: '&lt' converted to '<'
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        FeedAPI feedAPI = retrofit.create(FeedAPI.class);
        Call<Feed> call = feedAPI.getFeed(currentFeed);

        // Callback code executed after Retrofit is finished getting data
        call.enqueue(new Callback<Feed>() {

            @Override
            public void onResponse(@NonNull Call<Feed> call, @NonNull Response<Feed> response) {

                final List<SubredditPost> posts = new ArrayList<>();

                // Retrieve all the entries within <feed>
                List<Entry> entries = response.body().getEntryList();

                /* TEST: See if the Model is correctly structured
                Log.i(TAG, "onResponse: Server response: " + response.toString());
                Log.i(TAG, "onResponse: Entries: " + response.body().getEntryList());
                Log.i(TAG, "onResponse: Entry author: " + entries.get(0).getAuthor().getName());
                */

                // Generate a SubredditPost with each entry
                for (int i = 0; i < entries.size(); i++) {

                    Entry entry = entries.get(i);

                    // A great way to extract tag data from HTML or XML pages
                    ExtractXML linksXML = new ExtractXML(entry.getContent(), "<a href=");
                    List<String> postContent = linksXML.start();

                    ExtractXML imagesXML = new ExtractXML(entry.getContent(), "<img src=");

                    // Add null if no <img> tags are found
                    try {
                        postContent.add(imagesXML.start().get(0));
                    }
                    catch (Exception e) {
                        // Picasso will load in a placeholder image if it detects null
                        postContent.add(null);
                        Log.i(TAG, "onResponse: Thumbnail image not found");
                    }

                    // Create entry from the extracted data
                    int lastIndex = postContent.size() - 1;
                    try {
                        posts.add(new SubredditPost(
                                entry.getTitle(),
                                entry.getAuthor().getName(),
                                entry.getUpdated(),
                                // The first item in postContent will be a link; the last an image.
                                postContent.get(0),
                                postContent.get(lastIndex)));
                    }
                    catch(NullPointerException e) {
                        posts.add(new SubredditPost(
                                entry.getTitle(),
                                "No author", // Author may be null
                                entry.getUpdated(),
                                postContent.get(0),
                                postContent.get(lastIndex)));
                    }
                }

                /* TEST: Print out each individual post
                for (int i = 0; i < posts.size(); i++) {
                    Log.i(TAG, "onResponse: " + "\n" +
                            "Post author: " + posts.get(i).getAuthor() + "\n" +
                            "Post title: " + posts.get(i).getTitle() + "\n" +
                            "Post updated: " + posts.get(i).getDate_updated() + "\n" +
                            "Post link: " + posts.get(i).getPost_url() + "\n" +
                            "Post url: " + posts.get(i).getThumbnail_url() + "\n" +
                            "-------------------------------------------");
                } */

                // Create the custom list from the posts
                ListView listView = (ListView) findViewById(R.id.listView);
                CustomListAdapter adapter = new CustomListAdapter(MainActivity.this, R.layout.card_layout_main, posts);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                        intent.putExtra("url", posts.get(position).getPost_url());
                        startActivity(intent);
                    }
                });

            }

            // If there is an error with the call, this method will run instead
            @Override
            public void onFailure(@NonNull Call<Feed> call, @NonNull Throwable t) {
                Log.i(TAG, "Unable to retrieve RSS " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error getting the RSS", Toast.LENGTH_LONG).show();
            }
        });
    }
}
