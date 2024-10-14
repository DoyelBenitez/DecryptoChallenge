package com.decrypto.challenge.clientMarket.services;

import com.decrypto.challenge.clientMarket.daos.interfaces.IStatsDAO;
import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.clientMarket.dtos.StatsDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.ICountryClientStrategy;
import com.decrypto.challenge.clientMarket.services.interfaces.IStatsService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class StatsService extends AbstractService implements IStatsService {

    @Autowired
    private final IStatsDAO statsDao;
    @Autowired
    private final ICountryClientStrategy countryClientStrategy;

    public List<StatsDTO> getStatsData() throws ServiceExceptionP {
        List<Object[]> statsData  = this.statsDao.getStatsData();
        if (statsData.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, MarketCountryDTO> countriesList = this.findAllCountries(statsData);
        Map<String, StatsDTO> countryMap = new LinkedHashMap<>();
        for (Object[] result : statsData) {
            String code = (String) result[0];
            Long countryId = (Long) result[1];
            Double percentage = ((BigDecimal) result[2]).doubleValue();
            String countryName = countriesList.get(countryId).getName();
            StatsDTO statsDTO = countryMap.computeIfAbsent(countryName, k -> {
                StatsDTO newDTO = new StatsDTO();
                newDTO.setCountry(countryName);
                newDTO.setMarket(new ArrayList<>());
                return newDTO;
            });
            Map<String, Map<String, Double>> marketEntry = new HashMap<>();
            marketEntry.put(code, Map.of("percentage", percentage));
            statsDTO.getMarket().add(marketEntry);
        }
        return new ArrayList<>(countryMap.values());
    }

    private Map<Long, MarketCountryDTO> findAllCountries(List<Object[]> statsData) throws ServiceExceptionP {
        Set<Long> idsCountry = statsData
                .stream()
                .map(result -> (Long) result[1])
                .collect(Collectors.toSet());
        List<MarketCountryDTO> marketCountryDtoList = this.countryClientStrategy.findAll(new ArrayList<>(idsCountry));
        return marketCountryDtoList.stream()
                .collect(Collectors.toMap(MarketCountryDTO::getId, Function.identity()));
    }
}
