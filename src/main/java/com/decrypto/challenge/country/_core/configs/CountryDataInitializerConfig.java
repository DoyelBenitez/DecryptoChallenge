package com.decrypto.challenge.country._core.configs;

import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.services.interfaces.ICountryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author dbenitez
 */

@AllArgsConstructor
@Configuration
public class CountryDataInitializerConfig {

    @Autowired
    private final ICountryService countryService;

    @Bean
    public CommandLineRunner initCountryDatabase() {
        return args -> {
            createIfNotExists("Argentina");
            createIfNotExists("Uruguay");
        };
    }

    private void createIfNotExists(String name) throws ServiceExceptionP {
        if (!this.countryService.existsBy(name)) {
            CountryDTO countryDto = new CountryDTO();
            countryDto.setName(name);
            this.countryService.save(countryDto);
            System.out.println("El país " + name + " fue creado.");
        } else {
            System.out.println("El país " + name + " ya existe.");
        }
    }
}
