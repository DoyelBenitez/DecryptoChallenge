package com.decrypto.challenge.common._core.handlers;

import com.decrypto.challenge.common._core.jsonApi.JsonApiErrors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNoResourceFoundException(Exception ex, HttpServletRequest request) {
        log.error("No Resource Found error: {}", ex.getMessage());
        String uri = "/" + request.getContextPath() + "/error";
        ModelAndView mav = new ModelAndView("redirect:/error");
        mav.addObject("status", HttpStatus.NOT_FOUND.value());
        mav.addObject("error", "No Resource Found");
        mav.addObject("message", ex.getMessage());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonApiErrors> handleGlobalException(Exception ex) {
        log.error("Internal error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(CommonErrorsHandler.newInternalServerError());
    }
}
