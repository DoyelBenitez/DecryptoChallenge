package com.decrypto.challenge.clientMarket.services;

import com.decrypto.challenge.clientMarket.daos.interfaces.IMarketDAO;
import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.ICountryClientStrategy;
import com.decrypto.challenge.clientMarket.services.interfaces.IMarketService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.AppUtils;
import com.decrypto.challenge.common.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class MarketService extends AbstractService implements IMarketService {

    @Autowired
    private final IMarketDAO marketDao;
    @Autowired
    private final ICountryClientStrategy countryClient;

    public void save(MarketDTO marketDto) throws ServiceExceptionP {
        this.checkNull("generic.null", marketDto);
        this.checkNull("generic.null", marketDto.getCode(), marketDto.getCountry());
        this.checkNull("generic.null", marketDto.getCountry().getName());
        marketDto.setCode(AppUtils.convertToUpperCase(marketDto.getCode()));
        this.throwExceptionIf(this.marketDao.existsNotDeletedBy(marketDto.getCode()), "generic.alreadyExist");
        this.findCountryByName(marketDto);
        if (this.marketDao.existsDeletedBy(marketDto.getCode())) {
            this.recover(marketDto);
            return;
        }
        this.marketDao.save(marketDto);
    }

    public void delete(String code) throws ServiceExceptionP {
        this.checkNull("generic.null", code);
        code = AppUtils.convertToUpperCase(code);
        this.throwExceptionIf(!this.marketDao.existsNotDeletedBy(code), "generic.notExist");
        this.marketDao.delete(code);
    }

    public MarketDTO update(String currentCode, MarketDTO marketDto) throws ServiceExceptionP {
        this.checkNull("generic.null", marketDto);
        this.checkNull("generic.null", marketDto.getCode(), marketDto.getCountry());
        this.checkNull("generic.null", marketDto.getCountry().getName());

        MarketCountryDTO marketCountryDto = this.countryClient.findBy(marketDto.getCountry().getName());
        this.checkNull("country.notExist", marketCountryDto);
        marketDto.setCountry(marketCountryDto);

        currentCode = AppUtils.convertToUpperCase(currentCode);
        marketDto.setCode(AppUtils.convertToUpperCase(marketDto.getCode()));
        // Verificar existencia del Market
        this.throwExceptionIf(!this.marketDao.existsNotDeletedBy(currentCode), "generic.notExist");
        if (!currentCode.equals(marketDto.getCode())) {
            // Verificar conflicto con el codigo nuevo
            this.throwExceptionIf(this.marketDao.existsNotDeletedBy(marketDto.getCode()), "generic.alreadyExist");
        }

        // Verificar si hay un conflicto con un Market eliminado
        this.throwExceptionIf(this.marketDao.existsDeletedBy(marketDto.getCode()), "generic.alreadyExistDeleted");
        return this.marketDao.update(currentCode, marketDto);
    }

    public MarketDTO find(String code) throws ServiceExceptionP {
        this.checkNull("generic.null", code);
        code = AppUtils.convertToUpperCase(code);
        MarketDTO marketDto = this.marketDao.findMarket(code);
        this.checkNull("generic.notExist", marketDto);
        this.findCountryById(marketDto);
        return marketDto;
    }

    public List<MarketDTO> findAll() throws ServiceExceptionP {
        List<MarketDTO> markets = this.marketDao.findAll();
        List<Long> ids = markets.stream()
                .map(market -> market.getCountry().getId())
                .collect(Collectors.toList());
        List<MarketCountryDTO> marketCountryDtoList = this.findAllCountry(ids);
        Map<Long, MarketCountryDTO> countryMap = marketCountryDtoList.stream()
                .collect(Collectors.toMap(MarketCountryDTO::getId, Function.identity()));
        for (MarketDTO marketDto : markets) {
            MarketCountryDTO countryDto = countryMap.get(marketDto.getCountry().getId());
            if (countryDto != null) {
                marketDto.setCountry(countryDto);
            }
        }
        return markets;
    }

    private List<MarketCountryDTO> findAllCountry(List<Long> ids) throws ServiceExceptionP {
        List<MarketCountryDTO> marketCountryDtoList = this.countryClient.findAll(ids);
        this.checkNull("country.notExist", marketCountryDtoList);
        return marketCountryDtoList;
    }

    public Boolean existsBy(String code) throws ServiceExceptionP {
        this.checkNull("generic.null", code);
        return this.marketDao.existsBy(code);
    }

    private void recover(MarketDTO marketDto) throws ServiceExceptionP {
        this.checkNull("generic.null", marketDto);
        this.checkNull("generic.null", marketDto.getCode(), marketDto.getCountry());
        this.checkNull("generic.null", marketDto.getCode(), marketDto.getCountry().getId());
        this.throwExceptionIf(!this.marketDao.existsDeletedBy(marketDto.getCode()), "generic.notExist");
        this.marketDao.recovery(marketDto);
    }

    private void findCountryByName(MarketDTO marketDto) throws ServiceExceptionP {
        MarketCountryDTO marketCountryDto = this.countryClient.findBy(marketDto.getCountry().getName());
        this.checkNull("country.notExist", marketCountryDto);
        marketDto.setCountry(marketCountryDto);
    }

    private void findCountryById(MarketDTO marketDto) throws ServiceExceptionP {
        MarketCountryDTO marketCountryDto = this.countryClient.findBy(marketDto.getCountry().getId());
        this.checkNull("country.notExist", marketCountryDto);
        marketDto.setCountry(marketCountryDto);
    }

}
