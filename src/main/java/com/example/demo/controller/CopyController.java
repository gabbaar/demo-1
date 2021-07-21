package com.example.demo.controller;

import com.example.demo.service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class CopyController {
    @Autowired
    private Services services;

    @GetMapping("/user")
    public String getUser() {
        services.addUsers();
        return "All Post/User inserted into kafka";
    }

    @GetMapping("/post")
    public String getPosts() {
        services.addPosts();
        return "All Post/User inserted into kafka";
    }

    @GetMapping("/comment")
    public String getComents() {
        services.addComments();
        return "All Post/User inserted into kafka";
    }
}
