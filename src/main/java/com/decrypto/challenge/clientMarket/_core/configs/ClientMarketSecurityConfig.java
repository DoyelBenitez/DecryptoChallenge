package com.decrypto.challenge.clientMarket._core.configs;

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
public class ClientMarketSecurityConfig {

    @Bean
    public SecurityFilterChain clientMarketSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/market/**", "/client/**", "/stat/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/market/v1/markets/**").permitAll()
                        .requestMatchers("/client/v1/clients/**").permitAll()
                        .requestMatchers("/stat/v1/stats/**").permitAll()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .with(new FiltersConfig(), Customizer.withDefaults());
        return http.build();
    }
}
