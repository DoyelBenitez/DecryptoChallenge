package com.decrypto.challenge.common.services;

import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.MessageUtils;

import java.util.Collection;

/**
 * @Author dbenitez
 */
public abstract class AbstractService {

    public void checkNull(String keyMessage, Object... objects) throws ServiceExceptionP {
        for (Object obj : objects) {
            if (obj == null || (obj instanceof String && ((String) obj).isEmpty())) {
                throwException(keyMessage);
            }
        }
    }

    public <T> void checkNullOrEmpty(Collection<T> collection, String keyMessage) throws ServiceExceptionP {
        if (collection == null || collection.isEmpty()) {
            throwException(keyMessage);
        }
    }

    public void throwExceptionIf(boolean condition, String keyMessage) throws ServiceExceptionP {
        if (condition) {
            throwException(keyMessage);
        }
    }

    public void throwExceptionIf(boolean condition, Exception exception) throws ServiceExceptionP {
        if (condition) {
            throw new ServiceExceptionP(MessageUtils.searchMessage(exception.getMessage()));
        }
    }

    private void throwException(String keyMessage) throws ServiceExceptionP {
        throw new ServiceExceptionP(keyMessage);
    }
}

