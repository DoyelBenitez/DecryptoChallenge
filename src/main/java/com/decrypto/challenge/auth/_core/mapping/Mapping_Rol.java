package com.decrypto.challenge.auth._core.mapping;

import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.entities.Rol;

/**
 * @Author dbenitez
 */
public class Mapping_Rol {

    public static Rol basicMapping(RolDTO rolDto) {
        Rol rol = new Rol();
        rol.setId(rolDto.getId());
        rol.setDeleted(rolDto.getDeleted());
        rol.setName(rolDto.getName());
        return rol;
    }

    public static Rol fullMapping(RolDTO rolDto) {
        return basicMapping(rolDto);
    }

    public static RolDTO basicMapping(Rol rol) {
        RolDTO rolDto = new RolDTO();
        rolDto.setId(rol.getId());
        rolDto.setName(rol.getName());
        return rolDto;
    }

    public static RolDTO fullMapping(Rol rol) {
        return basicMapping(rol);
    }

}
