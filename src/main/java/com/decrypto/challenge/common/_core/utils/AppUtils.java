package com.decrypto.challenge.common._core.utils;

import java.util.List;

/**
 * @Author dbenitez
 */
public class AppUtils {

    public static Boolean isNotNullOrEmpty(Object object) {
        if (object instanceof List) {
            return !((List<?>) object).isEmpty();
        }
        return (object != null && object != "");
    }
}
