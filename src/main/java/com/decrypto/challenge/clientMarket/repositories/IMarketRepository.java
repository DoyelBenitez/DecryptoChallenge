package com.decrypto.challenge.clientMarket.repositories;

import com.decrypto.challenge.clientMarket.entities.Market;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author dbenitez
 */
public interface IMarketRepository extends CrudRepository<Market, Long> {

    Boolean existsMarketByCode(String code);
    Boolean existsMarketByCodeAndDeletedIsFalse(String code);
    Boolean existsMarketByCodeAndDeletedIsTrue(String code);
    Optional<Market> findMarketById(Long id);
    Optional<Market> findMarketByCode(String code);
    Optional<Market> findMarketByCodeAndDeletedIsFalse(String code);
    List<Market> findAllByDeletedIsFalse();
    Optional<List<Market>> findAllByCodeInAndDeletedIsFalse(List<String> code);
}
