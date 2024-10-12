package com.decrypto.challenge.common._core.jsonApi;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author dbenitez
 */

@Getter
@Setter
public class JsonApiError {

    private String id;
    private String status;
    private String code;
    private String title;
    private Object detail;

    private JsonApiError() {}

    public static JsonApiError create() {
        return new JsonApiError();
    }

    public JsonApiError withId(String id) {
        this.id = id;
        return this;
    }

    public JsonApiError withStatus(String status) {
        this.status = status;
        return this;
    }

    public JsonApiError withCode(String code) {
        this.code = code;
        return this;
    }

    public JsonApiError withTitle(String title) {
        this.title = title;
        return this;
    }

    public JsonApiError withDetail(Object detail) {
        this.detail = detail;
        return this;
    }

}