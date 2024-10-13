package com.decrypto.challenge.country._core.mapping;

import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.entities.Country;

/**
 * @Author dbenitez
 */
public class Mapping_Country {

    public static Country basicMapping(CountryDTO countryDto) {
        Country countryEntity = new Country();
        countryEntity.setId(countryDto.getId());
        countryEntity.setDeleted(countryDto.getDeleted());
        countryEntity.setName(countryDto.getName());
        return countryEntity;
    }

    public static Country fullMapping(CountryDTO countryDto) {
        return basicMapping(countryDto);
    }

    public static CountryDTO basicMapping(Country countryEntity) {
        CountryDTO countryDto = new CountryDTO();
        countryDto.setId(countryEntity.getId());
        countryDto.setDeleted(countryEntity.getDeleted());
        countryDto.setName(countryEntity.getName());
        return countryDto;
    }

    public static CountryDTO fullMapping(Country countryEntity) {
        return basicMapping(countryEntity);
    }
}

