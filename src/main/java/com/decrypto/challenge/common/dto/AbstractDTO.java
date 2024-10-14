package com.decrypto.challenge.common.dto;

import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

/**
 * @Author dbenitez
 */
@Getter
@Setter
public abstract class AbstractDTO {

    @Id
    @JsonIgnore
    @JsonApiId
    public Long id;
    @JsonIgnore
    public Boolean deleted;

    public void initialize() {
        this.deleted = false;
    }

}
