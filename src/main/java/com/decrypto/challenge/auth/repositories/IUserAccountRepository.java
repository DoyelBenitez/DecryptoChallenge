package com.decrypto.challenge.auth.repositories;

import com.decrypto.challenge.auth.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author dbenitez
 */
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);
    Boolean existsUserAccountByUsername(String username);
}
