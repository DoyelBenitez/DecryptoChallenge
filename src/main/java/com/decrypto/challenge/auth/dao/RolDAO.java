package com.decrypto.challenge.auth.dao;

import com.decrypto.challenge.auth.dao.interfaces.IRolDAO;
import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.entities.Rol;
import com.decrypto.challenge.auth.repositories.IRolRepository;
import com.decrypto.challenge.common._core.mapping.Mapping;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Repository
public class RolDAO implements IRolDAO {

    @Autowired
    private final IRolRepository rolRepository;

    public void save(RolDTO rolDto) {
        Rol rol = Mapping.fullMapping(rolDto);
        rol.initialize();
        this.rolRepository.save(rol);
    }

    public Boolean existsBy(String name) {
        return this.rolRepository.existsByName(name);
    }

    public RolDTO findBy(Long id) {
        return this.rolRepository.findById(id)
                .map(Mapping::fullMapping)
                .orElse(null);
    }

    public RolDTO findBy(String name) {
        return this.rolRepository.findByName(name)
                .map(Mapping::fullMapping)
                .orElse(null);
    }
}
