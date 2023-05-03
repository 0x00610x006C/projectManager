package i.m.allesssandro.projectmanager.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Jwt
{
    @Getter
    private final String token;

    private Jwt(String token)
    {
        this.token = token;
    }

    public static Jwt of(Long userId, Long validityInMinutes, String secretKey)
    {
        Instant issueDate = Instant.now();
        return new Jwt(Jwts.builder()
                .claim("user_id", userId)
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact());

    }

    public static Long from(String token, String secretKey)
    {
        return ((Claims) Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parse(token)
                .getBody())
                .get("user_id", Long.class);
    }

    public static Jwt of(String token)
    {
        return new Jwt(token);
    }

    public String getUserId()
    {
        return ((Claims) Jwts.parser().parse(token).getBody()).getId();
    }

    public LocalDateTime getExpiration(String secret)
    {
        return ((Claims) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parse(token)
                .getBody())
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDateTime getIssuedAt(String secret)
    {
        return ((Claims) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parse(token)
                .getBody())
                .getIssuedAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
