package com.decrypto.challenge.auth.dao;

import com.decrypto.challenge.auth._core.mapping.Mapping_UserAccount;
import com.decrypto.challenge.auth.dao.interfaces.IUserAccountDAO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.entities.UserAccount;
import com.decrypto.challenge.auth.repositories.IUserAccountRepository;
import com.decrypto.challenge.common._core.mapping.Mapping;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Repository
public class UserAccountDAO implements IUserAccountDAO {

    @Autowired
    private final IUserAccountRepository userAccountRepository;

    public void save(UserAccountDTO userAccountDto) {
        UserAccount userAccount = Mapping.fullMapping(userAccountDto);
        userAccount.initialize();
        this.userAccountRepository.save(userAccount);
    }

    public Boolean existBy(String username) {
        return this.userAccountRepository.existsUserAccountByUsername(username);
    }

    public UserAccountDTO findByUsernameForLogin(String username) {
        return this.userAccountRepository.findByUsername(username)
                .map(Mapping_UserAccount::fullMappingForLogIn)
                .orElse(null);
    }

    public UserAccountDTO findByUsername(String username) {
        return this.userAccountRepository.findByUsername(username)
                .map(Mapping::fullMapping)
                .orElse(null);
    }
}
