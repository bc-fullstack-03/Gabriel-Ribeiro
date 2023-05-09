package com.sysmap.parrot.dto;

import lombok.Data;

@Data
public class CreateLoginRequest {
    private String email;
    private String password;
}

