package com.decrypto.challenge.country.daos;

import com.decrypto.challenge.common._core.mapping.Mapping;
import com.decrypto.challenge.country.daos.interfaces.ICountryDAO;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.entities.Country;
import com.decrypto.challenge.country.repositories.ICountryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Repository
public class CountryDAO implements ICountryDAO {

    @Autowired
    private final ICountryRepository countryRepository;

    public void save(CountryDTO countryDto) {
        Country country = Mapping.fullMapping(countryDto);
        country.initialize();
        this.countryRepository.save(country);
    }

    public void delete(String name) {
        Country country = this.findCountryByName(name);
        country.setDeleted(true);
        this.countryRepository.save(country);
    }

    public void physicalDelete(String name) {
        Country country = this.countryRepository.findCountryByName(name)
                .orElseThrow(() -> new EntityNotFoundException("El país no existe"));
        this.countryRepository.delete(country);
    }

    public void recovery(String name) {
        this.countryRepository.findCountryByName(name)
                .map(country -> {
                    country.setDeleted(false);
                    return this.countryRepository.save(country);
                })
                .orElseThrow(() -> new EntityNotFoundException("El país no existe"));
    }

    public CountryDTO update(String name, CountryDTO countryDto) {
        Country country = this.findCountryByName(name);
        country.setName(countryDto.getName());
        country = this.countryRepository.save(country);
        return Mapping.fullMapping(country);
    }

    public CountryDTO find(String name) {
        Country country = this.findCountryByName(name);
        return Mapping.fullMapping(country);
    }

    public CountryDTO find(Long id) {
        return this.countryRepository.findCountryById(id)
                .map(Mapping::fullMapping)
                .orElseThrow(() -> new EntityNotFoundException("El país no existe"));
    }

    public List<CountryDTO> findAll() {
        List<Country> countries = this.countryRepository.findAllByDeletedIsFalse();
        return Mapping.fullMapping(countries);
    }

    public List<CountryDTO> findAll(List<Long> ids) {
        List<Country> countries = this.countryRepository.findAllByIdIn(ids);
        return Mapping.fullMapping(countries);
    }

    public Boolean existsBy(String name) {
        return this.countryRepository.existsCountryByName(name);
    }

    public Boolean existsBy(Long id) {
        return this.countryRepository.existsById(id);
    }

    public Boolean existsNotDeletedBy(String name) {
        return this.countryRepository.existsCountryByNameAndDeletedIsFalse(name);
    }

    public Boolean existsDeletedBy(String name) {
        return this.countryRepository.existsCountryByNameAndDeletedIsTrue(name);
    }

    private Country findCountryByName(String name) {
        return this.countryRepository.findCountryByNameAndDeletedIsFalse(name)
                .orElseThrow(() -> new EntityNotFoundException("El país no existe"));
    }
}
