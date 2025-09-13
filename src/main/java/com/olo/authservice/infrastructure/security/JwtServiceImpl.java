package com.olo.authservice.infrastructure.security;

import com.olo.authservice.domain.exception.token.InvalidTokenException;
import com.olo.authservice.domain.exception.token.MissingTokenException;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.infrastructure.entities.TokenEntity;
import com.olo.authservice.infrastructure.repositories.JpaTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtServicePort {

    private final JpaTokenRepository jpaTokenRepository;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access-token.expiration.minutes}")
    private long jwtAccessTokenExpiration;

    @Value("${jwt.refresh-token.expiration.days}")
    private long jwtRefreshTokenExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof CustomUserDetails customDetails) {
            claims.put("userId", customDetails.getId());
            claims.put("roles", customDetails.getRole());
            claims.put("titles", customDetails.getTitle());
        }
        claims.put("type", "access_token");

        long expirationMillis = jwtAccessTokenExpiration * 60 * 1000L;
        return buildToken(claims, userDetails.getUsername(), expirationMillis);
    }

    @Override
    public String generateRefreshToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof CustomUserDetails customDetails) {
            claims.put("userId", customDetails.getId());
        }
        claims.put("type", "refresh_token");

        long expirationMillis = jwtRefreshTokenExpiration * 24 * 60 * 60 * 1000L;
        return buildToken(claims, userDetails.getUsername(), expirationMillis);
    }

    @Override
    public String generateActivateToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof CustomUserDetails customDetails) {
            claims.put("userId", customDetails.getId());
        }
        claims.put("type", "activate_token");

        long expirationMillis = 24 * 60 * 60 * 1000L;
        return buildToken(claims, userDetails.getUsername(), expirationMillis);
    }

    @Override
    public boolean validateToken(String token) {
        return isValidToken(token);
    }

    @Override
    public Long getUserId(String token) {
        return extractUserId(token);
    }

    @Override
    public String getUsername(String token) {
        return extractUsername(token);
    }

    @Override
    public String getJti(String token) {
        return extractJti(token);
    }

    @Override
    public String getTokenType(String token) {
        return extractTokenType(token);
    }

    //Multiply to return the time in milliseconds
    @Override
    public Long getAccessTokenExpiration() {
        return jwtAccessTokenExpiration * 60 * 1000L;
    }

    //Multiply to return the time in milliseconds
    @Override
    public long getRefreshTokenExpiration() {
        return jwtRefreshTokenExpiration * 24 * 60 * 60 * 1000L;
    }



    /* ------------------------------------------------- */

    protected String buildToken(Map<String, Object> claims, String subject, long expirationMillis) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        Map<String, Object> finalClaims = new HashMap<>(claims);
        finalClaims.put("sub", subject);
        finalClaims.put("jti", UUID.randomUUID().toString());
        finalClaims.put("iat", now);
        finalClaims.put("exp", expiryDate);

        return Jwts.builder()
                .claims(finalClaims)
                .signWith(key)
                .compact();
    }

    protected <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    protected String extractJti(String token) {
        return extractClaim(token, Claims::getId);
    }

    protected Long extractUserId(String token) {
        return extractClaim(token, c -> c.get("userId", Long.class));
    }

    protected String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    protected String extractTokenType(String token) {
        return extractClaim(token, c -> c.get("type", String.class));
    }

    protected boolean isValidToken(String token) {
        if (!validatePreconditions(token)) {
            return false;
        }
        if (!validateSignature(token)) {
            return false;
        }
        if (isTokenExpired(token)) {
            return false;
        }
        return !isTokenRevoked(token);
    }

    protected boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    protected boolean validateSignature(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            throw new InvalidTokenException("Token signature is not valid.", e);
        }
    }

    protected boolean isTokenRevoked(String token) {
        String jti = extractJti(token);
        Optional<TokenEntity> tokenDomain = jpaTokenRepository.findByJti(jti);
        return tokenDomain.isPresent() && tokenDomain.get().isRevoked();
    }



    protected boolean validatePreconditions(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new MissingTokenException("Token cannot be null or empty.");
        }
        return true;
    }

    protected Optional<UserDetails> validateTokenAndGetUser(String token) {
        if (!isValidToken(token)) {
            return Optional.empty();
        }
        String username = extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Optional.of(userDetails);
    }
}
