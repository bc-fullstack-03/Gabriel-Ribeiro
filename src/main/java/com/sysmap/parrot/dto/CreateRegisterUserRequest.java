package com.sysmap.parrot.dto;

import lombok.Data;

@Data
public class CreateRegisterUserRequest {
    public String username;
    public String email;
    public String password;
}

