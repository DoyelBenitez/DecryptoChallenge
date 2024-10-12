package com.decrypto.challenge.common._core.handlers;

import com.decrypto.challenge.common._core.jsonApi.JsonApiErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author dbenitez
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<JsonApiErrors> handleHttpNotSupportedExceptions(HttpRequestMethodNotSupportedException ex) {
        log.error("Not allowed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).
                body(CommonErrorsHandler.newResourceNotAllowed(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonApiErrors> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Bad request: {}", ex.getMessage());
        Map<String, String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(CommonErrorsHandler.newBadRequest(errorMessages));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonApiErrors> handleGlobalException(Exception ex) {
        log.error("Internal error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(CommonErrorsHandler.newInternalServerError());
    }
}
