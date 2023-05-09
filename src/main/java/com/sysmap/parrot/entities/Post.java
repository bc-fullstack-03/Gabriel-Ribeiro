package com.sysmap.parrot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
public class Post {
    @Id
    private String id;
    private String userId;
    private String Title;
    private String description;
    private String image;
    private List<Like> likes;
    private List<Comment> comments;
    private LocalDateTime created;

    public Post() {
        // default constructor
    }

    public Post(String userId, String content) {
    }
}
