package com.decrypto.challenge.country._core.configs;

import com.decrypto.challenge.auth._core.filters.FiltersConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
public class CountrySecurityConfig {

    @Bean
    public SecurityFilterChain commonSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/country/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/country/v1/countries/**").permitAll()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .with(new FiltersConfig(), Customizer.withDefaults());
        return http.build();
    }
}
