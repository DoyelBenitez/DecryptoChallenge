package com.decrypto.challenge.common._core.jsonApi;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dbenitez
 */
@Getter
@Setter
public class JsonApiErrors {

    private List<JsonApiError> errors = new ArrayList<>();

    private JsonApiErrors() {}

    public static JsonApiErrors create() {
        return new JsonApiErrors();
    }

    public JsonApiErrors addError(JsonApiError error) {
        this.errors.add(error);
        return this;
    }

}

