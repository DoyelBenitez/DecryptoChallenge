package com.decrypto.challenge.common._core.mapping;

/**
 * @Author dbenitez
 */
public interface IEntityMappable<T extends IDTOMappable<?>> {

    T basicMapping();
    T fullMapping();
}
