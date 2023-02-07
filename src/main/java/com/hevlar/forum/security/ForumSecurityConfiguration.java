package com.hevlar.forum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class ForumSecurityConfiguration {

//    @Bean
//    public DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        // set the name of the attribute the CsrfToken will be populated on
//        requestHandler.setCsrfRequestAttributeName(null);
//        http
//                // ...
//                .csrf((csrf) -> csrf
//                        .csrfTokenRequestHandler(requestHandler)
//                );
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy())
                .and()
                .formLogin().disable();
        return httpSecurity.build();
    }

}
