package com.onlinebookstore.config;

import com.onlinebookstore.entity.User;
import com.onlinebookstore.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User userByUsername = userRepository.findByUsername(username);
        if (userByUsername == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (passwordEncoder.matches(password, userByUsername.getPassword())) {
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userByUsername.getRole().getName()));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}