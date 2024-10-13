package com.decrypto.challenge.clientMarket.services.serviceClient;

import com.decrypto.challenge.clientMarket.services.interfaces.ICountryClientStrategy;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.services.interfaces.ICountryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
@Slf4j
public class InternalCountryClient implements ICountryClientStrategy {

    @Autowired
    private final ICountryService countryService;

    @Override
    public Boolean existsBy(String countryName) throws ServiceExceptionP {
        try {
            return this.countryService.existsBy(countryName);
        } catch (Exception error) {
            log.error("Error al verificar la existencia del país: {}", error.getMessage());
            throw new ServiceExceptionP("generic.notContinue");
        }
    }

    @Override
    public Boolean existsBy(Long id) throws ServiceExceptionP {
        try {
            return this.countryService.existsBy(id);
        } catch (Exception error) {
            log.error("Error al verificar la existencia del país: {}", error.getMessage());
            throw new ServiceExceptionP("generic.notContinue");
        }
    }

    @Override
    public MarketCountryDTO findBy(String countryName) throws ServiceExceptionP {
        try {
            CountryDTO countryDto = this.countryService.find(countryName);
            if (countryDto != null) {
                MarketCountryDTO marketCountryDto = new MarketCountryDTO();
                marketCountryDto.setId(countryDto.getId());
                marketCountryDto.setName(countryDto.getName());
                return marketCountryDto;
            }
        } catch (Exception error) {
            log.error("Error al buscar el país: {}", error.getMessage());
        }
        return null;
    }

    @Override
    public MarketCountryDTO findBy(Long id) throws ServiceExceptionP {
        try {
            CountryDTO countryDto = this.countryService.find(id);
            if (countryDto != null) {
                MarketCountryDTO marketCountryDto = new MarketCountryDTO();
                marketCountryDto.setId(countryDto.getId());
                marketCountryDto.setName(countryDto.getName());
                return marketCountryDto;
            }
        } catch (Exception error) {
            log.error("Error al buscar el país: {}", error.getMessage());
        }
        return null;
    }

    @Override
    public List<MarketCountryDTO> findAll(List<Long> ids) throws ServiceExceptionP {
        try {
            List<CountryDTO> countryDtoList = this.countryService.findAll(ids);
            List<MarketCountryDTO> marketCountryDTOList = new ArrayList<>();
            if (countryDtoList != null && !countryDtoList.isEmpty()) {
                for (CountryDTO countryDto : countryDtoList) {
                    MarketCountryDTO marketCountryDto = new MarketCountryDTO();
                    marketCountryDto.setId(countryDto.getId());
                    marketCountryDto.setName(countryDto.getName());
                    marketCountryDTOList.add(marketCountryDto);
                }
                return marketCountryDTOList;
            }
        } catch (Exception error) {
            log.error("Error al buscar el país: {}", error.getMessage());
        }
        return null;
    }
}
