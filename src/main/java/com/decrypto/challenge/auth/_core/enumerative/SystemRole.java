package com.decrypto.challenge.auth._core.enumerative;

import lombok.Getter;

/**
 * @Author dbenitez
 */
@Getter
public enum SystemRole {

    ROOT("Admin"),
    ADMIN("User");

    private String role;
    SystemRole(String role) {
        this.role = role;
    }
}
