package com.example.apnaghar;

public class NewsItem {
    private String title;
    private String createdAt;
    private String content;

    public NewsItem(String title, String createdAt, String content) {
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }
}
