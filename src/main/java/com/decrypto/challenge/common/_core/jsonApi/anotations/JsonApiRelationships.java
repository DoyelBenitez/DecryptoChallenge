package com.decrypto.challenge.common._core.jsonApi.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author dbenitez
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)  // Anotación para campos
public @interface JsonApiRelationships {
    String value() default "";
}
