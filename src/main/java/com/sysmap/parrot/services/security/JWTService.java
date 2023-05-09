package com.sysmap.parrot.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService implements IJWTService {
    private final long EXPIRATION_TIME = 7200000;
    private final String KEY ="33743677397A24432646294A404E635266556A586E5A7234753778214125442A" ;

    public String generateToken(String userId) {
        return Jwts
                .builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(genSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key genSignInKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }

    public boolean isValidToken(String token, String userId) {
        var sub = getClaim(token, Claims::getSubject);
        var tokenExpiration = getClaim(token, Claims::getExpiration);

        if(!sub.equals(userId)){
            return false;
        }

        if(tokenExpiration.before(new Date())){
            return false;
        }
        return true;
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = Jwts.parserBuilder()
                                   .setSigningKey(genSignInKey())
                                   .build().parseClaimsJws(token)
                                   .getBody();
        return claimsResolver.apply(claims);
    }

    public String getLoggedUserId(){
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        int startIndex = loggedInUser.indexOf("id=") + 3;
        int endIndex = loggedInUser.indexOf(",", startIndex);

        return loggedInUser.substring(startIndex, endIndex);
    }
}
