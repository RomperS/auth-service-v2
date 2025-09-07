package com.olo.authservice.domain.port.outbound;

public interface JwtServicePort {

    String generateAccessToken(String username);
    String generateRefreshToken(String username);
    String generateActivateToken(String username);

    boolean validateToken(String token);

    Long getUserId(String token);
    String getUsername(String token);
    String getJti(String token);
    String getTokenType(String token);

    Long getAccessTokenExpiration();
    long getRefreshTokenExpiration();
}
