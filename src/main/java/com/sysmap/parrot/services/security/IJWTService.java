package com.sysmap.parrot.services.security;

public interface IJWTService {
     String generateToken(String userId);
     boolean isValidToken(String token, String userId);
     String getLoggedUserId();
}
