package com.decrypto.challenge.clientMarket.repositories;

import com.decrypto.challenge.clientMarket.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author dbenitez
 */
public interface IClientRepository extends JpaRepository<Client, Long> {

    Boolean existsClientByDescription(String description);
    Boolean existsClientByDescriptionAndDeletedIsFalse(String description);
    Boolean existsClientByDescriptionAndDeletedIsTrue(String description);
    Optional<Client> findClientById(Long id);
    Optional<Client> findClientByDescription(String description);
    Optional<Client> findClientByDescriptionAndDeletedIsFalse(String description);
    Optional<List<Client>> findAllByDeletedIsFalse();
    Optional<List<Client>> findAllByIdIn(List<Long> id);
}
