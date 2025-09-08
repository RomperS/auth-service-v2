package com.olo.authservice.infrastructure.mappers;

import com.olo.authservice.domain.model.Token;
import com.olo.authservice.infrastructure.entities.TokenEntity;

public class TokenMapper {

    public static Token entityToModel(TokenEntity tokenEntity) {
        if (tokenEntity == null) {
            return null;
        }

        return new Token(
                tokenEntity.getId(),
                tokenEntity.getJti(),
                tokenEntity.getUser().getId(),
                tokenEntity.getRefreshToken(),
                tokenEntity.isRevoked(),
                tokenEntity.getExpiredAt()
        );
    }

    public static TokenEntity modelToEntity(Token token) {
        if (token == null) {
            return null;
        }

        return new TokenEntity(
                token.id(),
                token.jti(),
                null,
                token.refreshToken(),
                token.isRevoked(),
                token.expiredAt()
        );
    }
}
