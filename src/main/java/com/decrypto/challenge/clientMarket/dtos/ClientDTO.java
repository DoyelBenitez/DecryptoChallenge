package com.decrypto.challenge.clientMarket.dtos;

import com.decrypto.challenge.clientMarket._core.mapping.Mapping_Client;
import com.decrypto.challenge.clientMarket.entities.Client;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common.dto.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends AbstractDTO implements IDTOMappable<Client> {

    private String description;
    private List<MarketDTO> markets;

    @Override
    public Client basicMapping() {
        return Mapping_Client.basicMapping(this);
    }

    @Override
    public Client fullMapping() {
        return Mapping_Client.fullMapping(this);
    }
}

