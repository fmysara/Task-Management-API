
package Task.Management.API.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service // this class provides a service
public class JwtService {

    @Value("${jwt.secret}") // reads values from application.properties
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generate a JWT token
    public String generateToken(String email) {

        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {

        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Check whether token is valid
    public boolean isTokenValid(String token) {

        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
