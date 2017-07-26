package com.example.cliff.appforreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = (WebView) findViewById(R.id.webView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView loadingEditText = (TextView) findViewById(R.id.loadingTextView);

        Intent intent = getIntent();
        String postURL = intent.getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                loadingEditText.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        webView.loadUrl(postURL);
    }
}
