package com.ecoassitant.back.auth.config;

import com.ecoassitant.back.entity.ProfilEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import io.jsonwebtoken.Jwts;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    private final static String SECRET_KEY = "9nWcIUOcoBTOrAJjkSi3Ltl/OJpEyXvLPZ3R8hF6pZs=";

    public String generateToken(ProfilEntity profilEntity){
        return  generateToken(profilEntity, new HashMap<>());
    }

    public String generateToken(ProfilEntity profilEntity, HashMap<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(profilEntity.getMail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        String email = extractMail(token);
        return (Objects.equals(email,userDetails.getUsername())) && !isExpiredToken(token);
    }

    private boolean isExpiredToken(String token){
        return extractExpiration(token).before(new Date());
    }

    public String extractMail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
