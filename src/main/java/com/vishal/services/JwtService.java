package com.vishal.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiry:3600}")  // default 1 hour (in seconds)
    private int expiry;


    // Get signing key (secure way – never hardcode secret in code)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate JWT token with custom claims + username as subject
    public String generateToken(Map<String, Object> extraClaims, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry * 1000L);

        return Jwts.builder()
                .setClaims(extraClaims != null ? extraClaims : new HashMap<>())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Overloaded: simple token with only username
    public String generateToken(String username) {
        return generateToken(null, username);
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token against UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Internal: parse all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // For testing: print a sample token on startup
    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "vishal@example.com");
        claims.put("role", "USER");
        claims.put("phone", "9999999999");

        String token = generateToken(claims, "vishal");
        System.out.println("Sample JWT Token: " + token);

        // Test extraction
        System.out.println("Extracted username: " + extractUsername(token));

        // FIXED: use empty list instead of null
        var user = new org.springframework.security.core.userdetails.User(
                "vishal",
                "",
                java.util.Collections.emptyList()   // ← or Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        System.out.println("Token valid? " + isTokenValid(token, user));
    }
}