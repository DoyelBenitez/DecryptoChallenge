package com.decrypto.challenge.auth.repositories;

import com.decrypto.challenge.auth.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author dbenitez
 */
public interface IRolRepository extends JpaRepository<Rol, Long> {

    Boolean existsByName(String rol);
    Optional<Rol> findByName(String rol);
}
