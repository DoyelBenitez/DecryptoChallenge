package com.decrypto.challenge.clientMarket.entities;

import com.decrypto.challenge.clientMarket._core.mapping.Mapping_Client;
import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.common._core.mapping.IEntityMappable;
import com.decrypto.challenge.common.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
@Entity
public class Client extends AbstractEntity implements IEntityMappable<ClientDTO> {

    @Column(nullable = false, unique = true)
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Market> markets;

    @Override
    public ClientDTO basicMapping() {
        return Mapping_Client.basicMapping(this);
    }

    @Override
    public ClientDTO fullMapping() {
        return Mapping_Client.fullMapping(this);
    }
}
