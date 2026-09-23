package com.example.legacy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurityConfigurerAdapter is removed entirely in Spring Security 6 / Spring Boot 3
 * (replaced by a SecurityFilterChain bean), and authorizeRequests() is renamed to
 * authorizeHttpRequests() — the exact api_replacement example documented in
 * SPEC.md's OpenRewrite recipe matrix (Part B).
 */
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/users/**").authenticated()
                .anyRequest().permitAll()
            .and()
            .httpBasic();
        return http.build();
    }
}
