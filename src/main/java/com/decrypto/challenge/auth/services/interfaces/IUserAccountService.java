package com.decrypto.challenge.auth.services.interfaces;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

/**
 * @Author dbenitez
 */
public interface IUserAccountService {

    void save(UserAccountDTO userAccountDto) throws ServiceExceptionP;

    Boolean existBy(String username) throws ServiceExceptionP;

    UserAccountDTO findByUsername(String username) throws ServiceExceptionP;
}
