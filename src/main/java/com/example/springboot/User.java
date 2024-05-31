package com.example.springboot;

import com.example.springboot.requests.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

// TODO implement
public class User {
    private UUID id;
    private String name;
    private ArrayList<UUID> groups;
    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.groups = new ArrayList<>();
    }
    public User(UserDTO user) {
        this.name = user.name;
        this.id = user.id;
    }
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addGroup(UUID group) {
        groups.add(group);
    }
    public void removeGroup(Group group) {
        groups.remove(group.getGroupUUID());
    }
}
