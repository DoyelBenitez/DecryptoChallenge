package com.decrypto.challenge.common._core.configs;

import com.decrypto.challenge.auth._core.filters.FiltersConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author dbenitez
 */
@Configuration
@EnableWebSecurity
public class CommonSecurityConfig {

    @Bean
    public SecurityFilterChain countrySecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/favicon/**", "/test/**", "/actuator/**", "/error")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/favicon**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/test/ping").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
