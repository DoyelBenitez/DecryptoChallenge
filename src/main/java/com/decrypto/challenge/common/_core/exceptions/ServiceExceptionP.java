package com.decrypto.challenge.common._core.exceptions;

/**
 * @Author dbenitez
 */
public class ServiceExceptionP extends Exception {

    public ServiceExceptionP() {
        super("Excepcion levantada en el servicio");
    }

    public ServiceExceptionP(String msj) {
        super(msj);
    }
}
