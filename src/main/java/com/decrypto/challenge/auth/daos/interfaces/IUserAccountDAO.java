package com.decrypto.challenge.auth.daos.interfaces;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;

/**
 * @Author dbenitez
 */
public interface IUserAccountDAO {

    void save(UserAccountDTO userAccountDto);

    Boolean existBy(String username);

    UserAccountDTO findByUsername(String username);
}

