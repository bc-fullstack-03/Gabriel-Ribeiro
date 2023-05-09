package com.sysmap.parrot.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    public String username;
    public String email;
    public String password;
    public String description;

}

