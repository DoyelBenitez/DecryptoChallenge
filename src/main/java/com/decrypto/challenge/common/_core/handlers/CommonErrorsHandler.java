package com.decrypto.challenge.common._core.handlers;

import com.decrypto.challenge.common._core.enums.ErrorHttpCode;
import com.decrypto.challenge.common._core.jsonApi.JsonApiError;
import com.decrypto.challenge.common._core.jsonApi.JsonApiErrors;

import java.util.UUID;

/**
 * @Author dbenitez
 */
public class CommonErrorsHandler {

    public static JsonApiErrors newBadRequest(Object message) {
        return JsonApiErrors
                .create()
                .addError(badRequest.withDetail(message));
    }

    public static JsonApiErrors newResourceNotFound(String message) {
        return JsonApiErrors
                .create()
                .addError(resourceNotFound.withDetail(message));
    }

    public static JsonApiErrors newResourceNotAllowed(String message) {
        return JsonApiErrors
                .create()
                .addError(resourceNotAllowed.withDetail(message));
    }

    public static JsonApiErrors newInternalServerError() {
        return JsonApiErrors
                .create()
                .addError(internalServerError);
    }

    private static final JsonApiError badRequest = JsonApiError.create()
            .withId(UUID.randomUUID().toString())
            .withCode(ErrorHttpCode.BAD_REQUEST.getCode())
            .withTitle(ErrorHttpCode.BAD_REQUEST.getTitle())
            .withStatus(ErrorHttpCode.BAD_REQUEST.getStatus());

    private static final JsonApiError resourceNotFound = JsonApiError.create()
            .withId(UUID.randomUUID().toString())
            .withCode(ErrorHttpCode.RESOURCE_NOT_FOUND.getCode())
            .withTitle(ErrorHttpCode.RESOURCE_NOT_FOUND.getTitle())
            .withStatus(ErrorHttpCode.RESOURCE_NOT_FOUND.getStatus());

    private static final JsonApiError resourceNotAllowed = JsonApiError.create()
            .withId(UUID.randomUUID().toString())
            .withCode(ErrorHttpCode.RESOURCE_NOT_ALLOWED.getCode())
            .withTitle(ErrorHttpCode.RESOURCE_NOT_ALLOWED.getTitle())
            .withStatus(ErrorHttpCode.RESOURCE_NOT_ALLOWED.getStatus());

    private static final JsonApiError internalServerError = JsonApiError.create()
            .withId(UUID.randomUUID().toString())
            .withCode(ErrorHttpCode.INTERNAL_SERVER_ERROR.getCode())
            .withTitle(ErrorHttpCode.INTERNAL_SERVER_ERROR.getTitle())
            .withStatus(ErrorHttpCode.INTERNAL_SERVER_ERROR.getStatus());
}
