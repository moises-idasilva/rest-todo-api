/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                    .and()
                        .authorizeHttpRequests()
//                          .anyRequest().permitAll()
                            // Endpoint: api/users/**
                            .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                            .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                    .and()
                        .logout().permitAll()
                    .and()
                        .csrf().disable();

        return http.build();
    }

}
