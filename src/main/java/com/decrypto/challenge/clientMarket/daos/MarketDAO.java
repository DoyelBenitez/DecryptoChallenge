package com.decrypto.challenge.clientMarket.daos;

import com.decrypto.challenge.clientMarket.daos.interfaces.IMarketDAO;
import com.decrypto.challenge.clientMarket.entities.Market;
import com.decrypto.challenge.clientMarket.repositories.IMarketRepository;
import com.decrypto.challenge.common._core.mapping.Mapping;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Repository
public class MarketDAO implements IMarketDAO {

    @Autowired
    private final IMarketRepository marketRepository;

    public void save(MarketDTO marketDto) {
        Market market = Mapping.fullMapping(marketDto);
        market.initialize();
        this.marketRepository.save(market);
    }

    public void delete(String code) {
        this.marketRepository.findMarketByCodeAndDeletedIsFalse(code)
                .map(market -> {
                    market.setDeleted(true);
                    return this.marketRepository.save(market);
                });
    }

    public void physicalDelete(String code) {
        this.marketRepository.findMarketByCode(code)
                .ifPresentOrElse(
                        this.marketRepository::delete, () -> {
                            throw new EntityNotFoundException("El mercado no existe");
                        });
    }

    public void recovery(MarketDTO marketDto) {
        this.marketRepository.findMarketByCode(marketDto.getCode())
                .map(market -> {
                    market.setDeleted(false);
                    market.setIdPais(marketDto.getCountry().getId());
                    market.setDescription(marketDto.getDescription());
                    return this.marketRepository.save(market);
                })
                .orElseThrow(() -> new EntityNotFoundException("El mercado no existe"));
    }

    public MarketDTO update(String code, MarketDTO marketDto) {
        return this.marketRepository.findMarketByCodeAndDeletedIsFalse(code)
                .map(market -> {
                    market.setCode(marketDto.getCode());
                    market.setDescription(marketDto.getDescription());
                    market.setIdPais(marketDto.getCountry().getId());
                    market = this.marketRepository.save(market);
                    return Mapping.fullMapping(market);
                })
                .orElse(null);
    }

    public MarketDTO findMarket(String code) {
        return this.marketRepository.findMarketByCodeAndDeletedIsFalse(code)
                .map(Mapping::fullMapping)
                .orElse(null);
    }

    public List<MarketDTO> findAll() {
        List<Market> markets = this.marketRepository.findAllByDeletedIsFalse();
        return Mapping.basicMapping(markets);
    }

    @Transactional
    public List<MarketDTO> findAll(List<String> codes) {
        return this.marketRepository.findAllByCodeInAndDeletedIsFalse(codes)
                .map(Mapping::fullMapping)
                .orElse(new ArrayList<>());
    }

    public Boolean existsBy(String code) {
        return this.marketRepository.existsMarketByCode(code);
    }

    public Boolean existsNotDeletedBy(String code) {
        return this.marketRepository.existsMarketByCodeAndDeletedIsFalse(code);
    }

    public Boolean existsDeletedBy(String code) {
        return this.marketRepository.existsMarketByCodeAndDeletedIsTrue(code);
    }

}
