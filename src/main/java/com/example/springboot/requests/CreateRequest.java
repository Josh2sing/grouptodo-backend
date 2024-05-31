package com.example.springboot.requests;

import com.example.springboot.User;

import java.util.UUID;

public class CreateRequest {
    public UUID userUUID;
    public String title;
    public UserDTO user;
}
