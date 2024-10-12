package com.decrypto.challenge.auth._core.exceptions;

/**
 * @Author dbenitez
 */
public class JwtExceptionP extends RuntimeException {

    public JwtExceptionP() {
        super("Hubo un error al momento de validar el token");
    }

    public JwtExceptionP(String message) {
        super(message);
    }
}
