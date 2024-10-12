package com.decrypto.challenge.auth.services.interfaces;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

import java.util.Map;

/**
 * @Author dbenitez
 */
public interface IAuthService {

    Map<String, Object> signIn(UserAccountDTO userAccountDto) throws ServiceExceptionP;
}
