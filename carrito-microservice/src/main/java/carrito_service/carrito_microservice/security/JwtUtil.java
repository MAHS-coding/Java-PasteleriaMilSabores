package carrito_service.carrito_microservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final byte[] keyBytes;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.keyBytes = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException ex) {
            return null;
        }
    }

    public String extractUsername(String token) {
        Claims claims = parseClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = parseClaims(token);
        if (claims == null) return null;
        Object roles = claims.get("roles");
        if (roles instanceof List) return (List<String>) roles;
        return null;
    }

    public boolean isTokenExpired(String token) {
        Claims claims = parseClaims(token);
        if (claims == null) return true;
        Date exp = claims.getExpiration();
        return exp == null || exp.before(new Date());
    }

    public boolean validate(String token) {
        return parseClaims(token) != null && !isTokenExpired(token);
    }

}
