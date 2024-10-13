package com.decrypto.challenge.auth._core.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author dbenitez
 */
@Order(2)
@Configuration
@EnableWebSecurity
public class TokenConfig {

    @Value("${security.jwt.secret-key-path}")
    private String secretKeyPath;
    @Value("${security.jwt.key-dynamic}")
    private Boolean keyDynamic;
    @Value("${security.jwt.token.expire-in-milliseconds}")
    private long expirationMilliseconds;

    private String key;

    @Bean
    public String secretKey() {
        this.generateSecretKey();
        return this.key;
    }

    @Bean
    public long expirationMilliseconds() {
        return expirationMilliseconds;
    }

    private void generateSecretKey() {
        try {
            String secretKeyPath = this.resolveSecretKeyPath();
            if (this.keyDynamic) {
                this.deleteIfExist(secretKeyPath);
            }
            if (!this.existFile(secretKeyPath)) {
                this.key = generateKey();
                this.saveKey(this.key, secretKeyPath);
            } else {
                this.key = Files.readString(Path.of(secretKeyPath));
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar o guardar jwtSecret.key", e);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar o guardar jwtSecret.key", e);
        }
    }

    private String generateKey() {
        byte[] keyBytes = new byte[64];
        new SecureRandom().nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    private void saveKey(String key, String secretKeyPath) throws IOException {
        Files.writeString(Path.of(secretKeyPath), key);
    }

    private void deleteIfExist(String secretKeyPath) throws IOException {
        Files.deleteIfExists(Path.of(secretKeyPath));
    }

    private Boolean existFile(String secretKeyPath) {
        return Files.exists(Path.of(secretKeyPath));
    }

    private String resolveSecretKeyPath() {
        return this.secretKeyPath;
    }

}
