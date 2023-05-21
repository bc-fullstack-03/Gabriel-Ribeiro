package com.sysmap.parrot.dto;

import lombok.Data;

@Data
public class AuthenticateResponse {
    public String userId;
    public String username;
    public String token;
}
