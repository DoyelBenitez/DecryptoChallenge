package com.decrypto.challenge.common._core.enums;

import lombok.Getter;

/**
 * @Author dbenitez
 */
@Getter
public enum ErrorHttpCode {

    BAD_REQUEST("dc:api:err:bad_request", "Bad Request", "400"),
    RESOURCE_NOT_FOUND("dc:api:err:resource_not_found", "Resource Not Found", "404"),
    RESOURCE_NOT_ALLOWED("dc:api:err:resource_not_allowed", "Resource Not Allowed", "405"),
    INTERNAL_SERVER_ERROR("dc:api:err:internal_server_error", "Internal Server Error", "500");

    private final String code;
    private final String title;
    private final String status;

    ErrorHttpCode(String code, String title, String status) {
        this.code = code;
        this.title = title;
        this.status = status;
    }

}

