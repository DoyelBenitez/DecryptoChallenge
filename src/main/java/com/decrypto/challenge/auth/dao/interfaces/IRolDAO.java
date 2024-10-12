package com.decrypto.challenge.auth.dao.interfaces;

import com.decrypto.challenge.auth.dtos.RolDTO;

/**
 * @Author dbenitez
 */
public interface IRolDAO {

    void save(RolDTO rolDto);

    Boolean existsBy(String name);

    RolDTO findBy(Long id);

    RolDTO findBy(String name);
}

