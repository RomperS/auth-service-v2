package com.olo.authservice.infrastructure.security;

import com.olo.authservice.infrastructure.entities.UserEntity;
import com.olo.authservice.infrastructure.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name() )));
        user.getTitles().forEach(title -> authorities.add(new SimpleGrantedAuthority("TITLE_" + title.name())));

        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getId(), user.getRoles(), user.getTitles(), authorities);
    }
}
