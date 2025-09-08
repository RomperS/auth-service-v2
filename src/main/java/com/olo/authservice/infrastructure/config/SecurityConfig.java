package com.olo.authservice.infrastructure.config;

import com.olo.authservice.infrastructure.security.CustomAuthenticationEntryPoint;
import com.olo.authservice.infrastructure.security.CustomUserDetailsService;
import com.olo.authservice.infrastructure.security.JwtAuthFilter;
import com.olo.authservice.infrastructure.security.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtServiceImpl jwtServiceImpl) {
        return new JwtAuthFilter(jwtServiceImpl);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/validation/login", "/validation/logout", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/validation/signup").hasAnyAuthority("ROLE_SUPER_ADMIN", "TITLE_SECRETARY")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }
}
