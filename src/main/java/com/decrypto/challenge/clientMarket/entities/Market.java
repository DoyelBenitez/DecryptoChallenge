package com.decrypto.challenge.clientMarket.entities;

import com.decrypto.challenge.clientMarket._core.mapping.Mapping_Market;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
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
public class Market extends AbstractEntity implements IEntityMappable<MarketDTO> {

    @Column(nullable = false, unique = true)
    private String code;
    @Column
    private String description;
    @Column(nullable = false)
    private Long idPais;
    @ManyToMany(mappedBy = "markets", fetch = FetchType.LAZY)
    private List<Client> clients;

    @Override
    public MarketDTO basicMapping() {
        return Mapping_Market.basicMapping(this);
    }

    @Override
    public MarketDTO fullMapping() {
        return Mapping_Market.fullMapping(this);
    }
}
