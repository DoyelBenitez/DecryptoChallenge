package com.decrypto.challenge.auth.dtos;

import com.decrypto.challenge.auth._core.mapping.Mapping_Rol;
import com.decrypto.challenge.auth.entities.Rol;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common.dto.AbstractDTO;
import lombok.*;

import java.util.List;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonApiType("Rol")
public class RolDTO extends AbstractDTO implements IDTOMappable<Rol> {

    private String name;

    @Override
    public Rol basicMapping() {
        return Mapping_Rol.basicMapping(this);
    }

    @Override
    public Rol fullMapping() {
        return Mapping_Rol.fullMapping(this);
    }
}
