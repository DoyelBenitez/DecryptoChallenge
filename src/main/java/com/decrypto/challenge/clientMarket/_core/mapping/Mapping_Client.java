package com.decrypto.challenge.clientMarket._core.mapping;

import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.clientMarket.entities.Client;
import com.decrypto.challenge.common._core.mapping.Mapping;

/**
 * @Author dbenitez
 */
public class Mapping_Client {

    public static Client basicMapping(ClientDTO clientDto) {
        Client clientEntity = new Client();
        clientEntity.setId(clientDto.getId());
        clientEntity.setDeleted(clientDto.getDeleted());
        clientEntity.setDescription(clientDto.getDescription());
        return clientEntity;
    }

    public static Client fullMapping(ClientDTO clientDto) {
        Client clientEntity = basicMapping(clientDto);
        clientEntity.setMarkets(Mapping.basicMapping(clientDto.getMarkets()));
        return clientEntity;
    }

    public static ClientDTO basicMapping(Client clientEntity) {
        ClientDTO clientDto = new ClientDTO();
        clientDto.setId(clientEntity.getId());
        clientDto.setDescription(clientEntity.getDescription());
        return clientDto;
    }

    public static ClientDTO fullMapping(Client clientEntity) {
        ClientDTO clientDto = basicMapping(clientEntity);
        clientDto.setMarkets(Mapping.basicMapping(clientEntity.getMarkets()));
        return clientDto;
    }
}

