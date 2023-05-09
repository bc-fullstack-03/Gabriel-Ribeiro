package com.sysmap.parrot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@AllArgsConstructor
public class Comment {
    @Id
    private String id;
    private String userId;
    private String content;
    private List<Like> likes;

    public Comment() {}
}
