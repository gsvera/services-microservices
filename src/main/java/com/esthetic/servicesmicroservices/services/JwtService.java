package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import java.util.function.Function;
@Service
public class JwtService {
    private static final String SECRET_KEY = "586E32ACM1PT387PEL4DOF4428472B4B6250655368566B597033733676397924";
    public String GetToken(User user) {
        return GetToken(new HashMap<>(), user);
    }
    private String GetToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*+60*+24)) // PARA QUE EL TOKEN EXPIRE EN UN DIA
                .signWith(GetKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key GetKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token, Claims::getSubject);
    }
    public String getIdUserFromToken(String token) {
        Claims claims = getAllClaims(token);
        return claims.get("id_user", String.class);
    }
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(GetKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Date getExpiration(String token) {
        return getClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
