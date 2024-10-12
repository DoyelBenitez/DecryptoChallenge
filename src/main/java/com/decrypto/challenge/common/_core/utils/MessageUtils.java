package com.decrypto.challenge.common._core.utils;

import com.decrypto.challenge.common._core.configs.MessageConfig;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Author dbenitez
 */
@NoArgsConstructor
public class MessageUtils {

    public static String searchMessage(String clave) {
        try {
            return MessageConfig.messageSource().getMessage(clave, null, LocaleContextHolder.getLocale());
        } catch (Exception error) {
            throw new RuntimeException("Hubo un problema al intentar recuperar el mensaje. MessageUtils -> searchMessage - " + clave);
        }
    }

    public static Boolean exist(String clave) {
        try {
            MessageConfig.messageSource().getMessage(clave, null, LocaleContextHolder.getLocale());
            return true;
        } catch (Exception error) {
            return false;
        }
    }
}
