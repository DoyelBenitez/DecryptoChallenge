package com.decrypto.challenge.clientMarket.daos;

import com.decrypto.challenge.clientMarket.daos.interfaces.IClientDAO;
import com.decrypto.challenge.clientMarket.repositories.IClientRepository;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.mapping.Mapping;
import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.clientMarket.entities.Client;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Repository
public class ClientDAO implements IClientDAO {

    @Autowired
    private IClientRepository clientRepository;

    public void save(ClientDTO clientDto) {
        Client country = Mapping.fullMapping(clientDto);
        country.initialize();
        this.clientRepository.save(country);
    }

    public void delete(String description) {
        this.clientRepository.findClientByDescriptionAndDeletedIsFalse(description)
                .map(client -> {
                    client.setDeleted(true);
                    return this.clientRepository.save(client);
                });
    }

    public void recovery(String description) throws ServiceExceptionP {
        this.clientRepository.findClientByDescription(description)
                .map(country -> {
                    country.setDeleted(false);
                    return this.clientRepository.save(country);
                })
                .orElseThrow(() -> new ServiceExceptionP("generic.wrongData"));
    }

    public ClientDTO update(String description, ClientDTO clientDto) throws ServiceExceptionP {
        return this.clientRepository.findClientByDescriptionAndDeletedIsFalse(description)
                .map(client -> {
                    client.setDescription(clientDto.getDescription());
                    client.setMarkets(Mapping.basicMapping(clientDto.getMarkets()));
                    client = this.clientRepository.save(client);
                    return Mapping.fullMapping(client);
                })
                .orElseThrow(() -> new ServiceExceptionP("generic.wrongData"));
    }

    public ClientDTO find(String description) {
        return this.clientRepository.findClientByDescriptionAndDeletedIsFalse(description)
                .map(Mapping::fullMapping)
                .orElse(null);
    }

    public ClientDTO find(Long id) {
        return this.clientRepository.findClientById(id)
                .map(Mapping::fullMapping)
                .orElse(null);
    }

    public List<ClientDTO> findAll() {
        return this.clientRepository.findAllByDeletedIsFalse()
                .map(Mapping::fullMapping)
                .orElse(new ArrayList<>());
    }

    public Boolean existsBy(String description) {
        return this.clientRepository.existsClientByDescription(description);
    }

    public Boolean existsBy(Long id) {
        return this.clientRepository.existsById(id);
    }

    public Boolean existsNotDeletedBy(String description) {
        return this.clientRepository.existsClientByDescriptionAndDeletedIsFalse(description);
    }

    public Boolean existsDeletedBy(String description) {
        return this.clientRepository.existsClientByDescriptionAndDeletedIsTrue(description);
    }

}

