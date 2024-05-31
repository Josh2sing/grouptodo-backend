package com.example.springboot;

import java.util.UUID;

// TODO Implement
public class TodoItem {
    public String title;
    public boolean isDone;
    public UUID id;

    public TodoItem(String title) {
        this.title = title;
        this.isDone = false;
        this.id = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
    }
    public boolean getIsDone() {
        return isDone;
    }
}
