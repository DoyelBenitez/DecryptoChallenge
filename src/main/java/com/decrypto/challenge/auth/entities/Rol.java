package com.decrypto.challenge.auth.entities;

import com.decrypto.challenge.auth._core.mapping.Mapping_Rol;
import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.common._core.mapping.IEntityMappable;
import com.decrypto.challenge.common.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Rol")
public class Rol extends AbstractEntity implements IEntityMappable<RolDTO> {

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public RolDTO basicMapping() {
        return Mapping_Rol.basicMapping(this);
    }

    @Override
    public RolDTO fullMapping() {
        return Mapping_Rol.fullMapping(this);
    }
}
