package com.decrypto.challenge.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author dbenitez
 */

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    protected Long id;
    @Column(nullable = false)
    private Boolean deleted;

    public void initialize() {
        this.deleted = false;
    }
}
