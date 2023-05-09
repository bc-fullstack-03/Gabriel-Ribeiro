package com.sysmap.parrot.dto;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private String description;
}
