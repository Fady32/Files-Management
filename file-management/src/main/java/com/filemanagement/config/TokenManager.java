package com.filemanagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TokenManager {

    // You should ideally keep the secret in a secure location like application.properties or an environment variable
    private String secretKey = "w1LpxL3VV5PCj2k6fY1lc5LOhUSKnOIJczg9kB8bm50"; // Replace this with a secure key

    // Token expiration time (for example, 1 hour)
    private long expirationTime = 1000 * 60 * 60;

    public String generateToken(String email, List<String> permissions) {


        return Jwts.builder().subject(email).claim("permissions", permissions).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Sign the token using HMAC algorithm and the secret key
                .compact();  // Build and return the token
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey).build()  // The same secret used for signing the token
                    .parseClaimsJws(token);  // Parse the claims
            return true;  // Token is valid
        } catch (Exception e) {
            return false;  // Token is invalid
        }
    }


    public String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody(); // Get the claims from the token
        return claims.getSubject(); // Return the subject (username) from the claims
    }

    public List<String> getPermissionsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody(); // Get the claims from the token
        return (List<String>) claims.get("permissions"); // Return the subject (username) from the claims
    }

    /**
     * Check if the token has expired
     *
     * @param token - The JWT token
     * @return True if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration(); // Get the expiration date from the token
        return expiration.before(new Date()); // Check if the expiration date is before the current date
    }
}
