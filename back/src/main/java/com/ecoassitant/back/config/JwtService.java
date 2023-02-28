package com.ecoassitant.back.config;

import com.ecoassitant.back.entity.ProfilEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Service for implement the jwt token
 */
@Service
public class JwtService {
    private final static String SECRET_KEY = "9nWcIUOcoBTOrAJjkSi3Ltl/OJpEyXvLPZ3R8hF6pZs=";

    /**
     * Function for generate token form a profile
     * @param profilEntity profile use for generate token
     * @return the token
     */
    public String generateToken(ProfilEntity profilEntity){
        return  generateToken(profilEntity, new HashMap<>());
    }

    /**
     * Function for generate token form a profile with extra claims
     * @param profilEntity profile use for generate token
     * @param claims extra claims
     * @return the token
     */
    public String generateToken(ProfilEntity profilEntity, Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(profilEntity.getMail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))//set expiration date in 1 day
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Getter for get secret key in a byte array
     * @return byte array that represent the secret key
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Function that check is the token is valid and was generated by the user
     * @param token the token to check
     * @param userDetails the user to check
     * @return is the token is valid
     */
    public boolean isValidToken(String token, UserDetails userDetails){
        String email = extractMail(token);
        return (Objects.equals(email,userDetails.getUsername())) && !isExpiredToken(token);
    }

    /**
     * Check if the token is expired
     * @param token the token to check
     * @return is the token is expired
     */
    private boolean isExpiredToken(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     * Function to extract mail from the token
     * @param token the token that contains the mail
     * @return the mail
     */
    public String extractMail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * Function to extract expiration date from token
     * @param token the token that contains the token
     * @return the expiration date
     */
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    /**
     * Function to extract a claims from token with claimsResolver
     * @param token the token that contain claims
     * @param claimsResolver the lambda to extract the claims
     * @return the claims
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Function to extract all claims from a token
     * @param token the token that contains all claims
     * @return all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Function to extract verify claims
     * @param token the token of the user
     * @return return verify claims. Return false is the claims verify is not present
     */
    public boolean extractVerify(String token){
        return (boolean) extractClaim(token, claims -> claims.get("verify"));
    }

    /**
     * Method to handle JwtException into an HttpStatus.FORBIDDEN on bad token
     *
     * @param ex exception
     * @return Map with the field mail and the message
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({JwtException.class, IllegalArgumentException.class})
    @ResponseBody
    public Map<String, String> handleDataViolationExceptions(DataIntegrityViolationException ex) {
        return Map.of("error", "invalid token");
    }
}
