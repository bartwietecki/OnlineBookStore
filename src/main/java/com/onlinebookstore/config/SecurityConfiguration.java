package com.onlinebookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfiguration {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests(req -> req
//                        .requestMatchers("/books", "/books/**", "/uploads/**", "/cart/**").permitAll()
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN"))
//
//
//                .formLogin(form -> form.loginPage("/login").permitAll())
//                .logout(logout -> logout.logoutSuccessUrl("/books"));
//
//
//            .authorizeHttpRequests(req -> req
//                .requestMatchers("/register-admin").permitAll());
//
//
////        http.logout(logout -> logout.logoutSuccessUrl("/books"));
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/books", "/books/**", "/uploads/**", "/cart/**", "/contact-us/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/register-admin").permitAll()
                )
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/books"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}