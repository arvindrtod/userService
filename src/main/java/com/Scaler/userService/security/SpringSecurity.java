package com.Scaler.userService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
public class SpringSecurity {

//    @Bean
//    public SecurityFilterChain filterCriteria(HttpSecurity http) throws Exception {
//        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
//        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
//
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//
//        return http.build();
//    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
