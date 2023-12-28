package com.hamroroom.proximitysearch.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(httpRequest->
                        httpRequest.anyRequest().permitAll())
                .build();

    }
}
