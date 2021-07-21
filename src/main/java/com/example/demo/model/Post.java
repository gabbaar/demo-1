package com.example.demo.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private String id;
    private String text;
    private String image;
    private Integer likes;
    private String link;
    private String appId;
    private List<String> tags;
    private String publishDate;
    private User owner;
}
