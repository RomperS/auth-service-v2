package com.olo.authservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jti;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;
    private boolean isRevoked;
    private Instant expiredAt;
}
