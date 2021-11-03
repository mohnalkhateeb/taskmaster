package com.example.taskmaster;

import androidx.annotation.NonNull;

public class Task {


    String title;
    String body;
    String state;

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }

    public String getBody() {
        return body;
    }
    @Override
    @NonNull
    public String toString() {
        return String.format("TITLE: %s DESCRIPTION: %s STATE: %s", this.title, this.body, this.state);
    }

}