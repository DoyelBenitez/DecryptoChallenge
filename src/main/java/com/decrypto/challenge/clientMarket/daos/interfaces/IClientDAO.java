package com.decrypto.challenge.clientMarket.daos.interfaces;

import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;

import java.util.List;

/**
 * @Author dbenitez
 */
public interface IClientDAO {

    void save(ClientDTO clientDto);

    void delete(String description);

    void recovery(String description) throws ServiceExceptionP;

    ClientDTO update(String description, ClientDTO clientDto) throws ServiceExceptionP;

    ClientDTO find(String description);

    ClientDTO find(Long id);

    List<ClientDTO> findAll();

    Boolean existsBy(String description);

    Boolean existsBy(Long id);

    Boolean existsNotDeletedBy(String description);

    Boolean existsDeletedBy(String description);
}
