package com.example.Libaray.pojo;

public class TransactionRequest {
    private String title;      // nullable for borrowing history
    private String username;

    public TransactionRequest() {}

    public TransactionRequest(String username) {
        this.username = username;
    }

    public TransactionRequest(String title, String username) {
        this.title = title;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
