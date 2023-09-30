package com.example.apnaghar;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        String content = getIntent().getStringExtra("content");

        WebView webView = findViewById(R.id.webView);
        webView.loadData(content, "text/html", "UTF-8");
    }
}
