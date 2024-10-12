package com.decrypto.challenge.common._core.mapping;

/**
 * @Author dbenitez
 */
public interface IDTOMappable<T extends IEntityMappable<?>> {

    T basicMapping();
    T fullMapping();
}
