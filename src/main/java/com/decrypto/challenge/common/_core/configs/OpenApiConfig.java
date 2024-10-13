package com.decrypto.challenge.common._core.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author dbenitez
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Decrypto Challenge API",
                version = "1.0.0",
                description = "API para Decrypto Challenge"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                Server prodServer = new Server();
                prodServer.setUrl("https://decryptochallenge-production.up.railway.app/challenge/api/");
                prodServer.setDescription("Servidor de Producción");

                Server devServer = new Server();
                devServer.setUrl("http://localhost:8080/challenge/api/");
                devServer.setDescription("Servidor de Desarrollo");
                return new OpenAPI().servers(List.of(devServer, prodServer));
        }

        @Bean
        public GroupedOpenApi allApi() {
                return GroupedOpenApi.builder()
                        .group("1- Todos")
                        .pathsToMatch("/**")
                        .build();
        }

        @Bean
        public GroupedOpenApi authenticationApi() {
                return GroupedOpenApi.builder()
                        .group("2- Autenticación")
                        .pathsToMatch("/auth/**")
                        .build();
        }

        @Bean
        public GroupedOpenApi countryApi() {
                return GroupedOpenApi.builder()
                        .group("3- Paises")
                        .pathsToMatch("/auth/**", "/country/**")
                        .build();
        }

        @Bean
        public GroupedOpenApi marketApi() {
                return GroupedOpenApi.builder()
                        .group("4- Comitente-Mercado")
                        .pathsToMatch("/auth/**", "/client/**", "/market/**")
                        .build();
        }
}
