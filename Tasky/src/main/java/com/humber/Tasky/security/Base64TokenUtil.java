package com.humber.Tasky.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Base64TokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("iat", new Date());
        claims.put("exp", Date.from(Instant.now().plusSeconds(expiration)));
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        try {
            String jsonClaims = objectMapper.writeValueAsString(claims);
            String tokenData = secret + ":" + jsonClaims;
            return Base64.getEncoder().encodeToString(tokenData.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error creating token", e);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> (String) claims.get("sub"));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, claims -> objectMapper.convertValue(claims.get("exp"), Date.class));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractAllClaims(String token) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedString = new String(decodedBytes);
            String[] parts = decodedString.split(":", 2);
            if (!parts[0].equals(secret)) {
                throw new RuntimeException("Invalid token signature");
            }
            return objectMapper.readValue(parts[1], Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting claims", e);
        }
    }

    private <T> T extractClaim(String token, ClaimExtractor<T> extractor) {
        Map<String, Object> claims = extractAllClaims(token);
        return extractor.extract(claims);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @FunctionalInterface
    private interface ClaimExtractor<T> {
        T extract(Map<String, Object> claims);
    }
}