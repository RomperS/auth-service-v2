package com.olo.authservice.infrastructure.security;

import com.olo.authservice.infrastructure.entities.UserEntity;
import com.olo.authservice.infrastructure.repositories.JpaUserRepository;
import com.olo.internalauthlibrary.exceptions.permissions.InvalidPermissionValueException;
import com.olo.internalauthlibrary.permissions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<Role> userRoles = new HashSet<>(user.getRoles());
        Set<Title> userTitles = new HashSet<>(user.getTitles());

        for (Title title : userTitles) {
            Role requiredRole = title.getRole();
            if (!userRoles.contains(requiredRole)) {
                throw new InvalidPermissionValueException("The user does not have the required role for the title: " + requiredRole.name());
            }
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        userTitles.forEach(title -> {
            Role requiredRole = title.getRole();
            String authority = String.format("AUTHORITY_%S::%S", requiredRole.name(), title.name());
            authorities.add(new SimpleGrantedAuthority(authority));
        });

        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getId(), userRoles.stream().toList(), userTitles.stream().toList(), authorities);
    }
}
