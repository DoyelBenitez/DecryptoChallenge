package com.decrypto.challenge.clientMarket._core.mapping;

import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.clientMarket.entities.Market;
import com.decrypto.challenge.common._core.mapping.Mapping;

/**
 * @Author dbenitez
 */
public class Mapping_Market {

    public static Market basicMapping(MarketDTO marketDto) {
        Market marketEntity = new Market();
        marketEntity.setId(marketDto.getId());
        marketEntity.setDeleted(marketDto.getDeleted());
        marketEntity.setCode(marketDto.getCode());
        marketEntity.setDescription(marketDto.getDescription());
        marketEntity.setIdPais(marketDto.getCountry().getId());
        return marketEntity;
    }

    public static Market fullMapping(MarketDTO marketDto) {
        Market marketEntity = basicMapping(marketDto);
        marketEntity.setClients(Mapping.basicMapping(marketDto.getClients()));
        return marketEntity;
    }

    public static MarketDTO basicMapping(Market marketEntity) {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setId(marketEntity.getId());
        marketDto.setCode(marketEntity.getCode());
        marketDto.setDescription(marketEntity.getDescription());
        MarketCountryDTO marketCountryDto = new MarketCountryDTO();
        marketCountryDto.setId(marketEntity.getIdPais());
        marketDto.setCountry(marketCountryDto);
        return marketDto;
    }

    public static MarketDTO fullMapping(Market marketEntity) {
        MarketDTO marketDto = basicMapping(marketEntity);
        marketDto.setClients(Mapping.basicMapping(marketEntity.getClients()));
        return marketDto;
    }
}

