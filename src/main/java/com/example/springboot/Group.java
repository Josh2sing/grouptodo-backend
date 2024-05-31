package com.example.springboot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Group {
    private HashMap<UUID, User> users;
    private HashMap<UUID, TodoItem> items;
    private User owner;
    private UUID groupUUID;
    private String groupPin;
    private String title;

    public Group(String title, User owner) {
        users = new HashMap<>();
        this.owner = owner;
        users.put(owner.getId(), owner);
        items = new HashMap<>();
        groupUUID = UUID.randomUUID();
        groupPin = generateGroupPin();
        this.title = title;
    }
    public TodoItem addItem(String itemName) {
        TodoItem item = new TodoItem(itemName);
        try {
            items.put(item.id, item);
        } catch (Exception e) {
            System.err.println("Error adding item to list");
            throw new RuntimeException(e);
        }
        return item;
    }
    public void addUser(User user) {
        users.put(user.getId(), user);
    }
    public HashMap<UUID, TodoItem> getItems() {
        return items;
    }
    public void removeUser(User user) {
        users.remove(user.getId());
    }
    public void removeItem(UUID itemId) {
        items.remove(itemId);
    }
    public TodoItem markItem(UUID itemUUID) {
        TodoItem found = items.get(itemUUID);
        found.isDone = !found.isDone;
        return found;
    }
    public boolean authUser(UUID userUUID) {
        return users.containsKey(userUUID);
    }

    public UUID getGroupUUID() {return groupUUID;}
    public String getGroupPin() {return groupPin;}
    public String getTitle() {return title;}
    private String generateGroupPin() {
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder pin = new StringBuilder();
        int index;
        for(int i = 0; i < 4; i++) {
            index = (int) (Math.random() * 62);
            pin.append(charset.charAt(index));
        }
        return pin.toString();
    }
}
