package com.decrypto.challenge.common._core.utils;

import com.decrypto.challenge.common._core.jsonApi.JsonApiGenerator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseHttpUtils {

    /**
     * httpStatusOK
     */

    public static ResponseEntity<?> httpStatusOK() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity<Object> httpStatusOK(String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(message);
        return ResponseEntity.ok(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusOK(Object object) {
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object);
        return ResponseEntity.ok(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusOK(Object object, String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object, message);
        return ResponseEntity.ok(jsonApi.toPrettyString());
    }

    /**
     * httpStatusBadRequest
     */

    public static ResponseEntity<?> httpStatusBadRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public static ResponseEntity<Object> httpStatusBadRequest(String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusBadRequest(Object object) {
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusBadRequest(Object object, String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonApi.toPrettyString());
    }

    /**
     * httpStatusUnauthorized
     */

    public static ResponseEntity<?> httpStatusUnauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public static ResponseEntity<Object> httpStatusUnauthorized(String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusUnauthorized(Object object) {
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusUnauthorized(Object object, String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object, message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonApi.toPrettyString());
    }

    /**
     * httpStatusInternalServerError
     */

    public static ResponseEntity<?> httpStatusInternalServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public static ResponseEntity<Object> httpStatusInternalServerError(String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusInternalServerError(Object object) {
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonApi.toPrettyString());
    }

    public static ResponseEntity<Object> httpStatusInternalServerError(Object object, String i18nKey) {
        String message = MessageUtils.searchMessage(i18nKey);
        ObjectNode jsonApi = JsonApiGenerator
                .create()
                .generateJsonApi(object, message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonApi.toPrettyString());
    }
}