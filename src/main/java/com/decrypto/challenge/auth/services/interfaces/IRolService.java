package com.decrypto.challenge.auth.services.interfaces;

import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

/**
 * @Author dbenitez
 */
public interface IRolService {

    void save(RolDTO rolDto) throws ServiceExceptionP;

    Boolean existsBy(String name) throws ServiceExceptionP;

    RolDTO findBy(Long id) throws ServiceExceptionP;

    RolDTO findBy(String name) throws ServiceExceptionP;
}
