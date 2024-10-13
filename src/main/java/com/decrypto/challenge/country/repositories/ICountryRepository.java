package com.decrypto.challenge.country.repositories;

import com.decrypto.challenge.country.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author dbenitez
 */
public interface ICountryRepository extends JpaRepository<Country, Long> {

    Boolean existsCountryByName(String name);
    Boolean existsCountryByNameAndDeletedIsFalse(String name);
    Boolean existsCountryByNameAndDeletedIsTrue(String name);
    Optional<Country> findCountryById(Long id);
    Optional<Country> findCountryByName(String name);
    Optional<Country> findCountryByNameAndDeletedIsFalse(String name);
    List<Country> findAllByDeletedIsFalse();
}
