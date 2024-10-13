package com.decrypto.challenge.country.services;

import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.AppUtils;
import com.decrypto.challenge.common.services.AbstractService;
import com.decrypto.challenge.country.daos.interfaces.ICountryDAO;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.services.interfaces.ICountryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class CountryService extends AbstractService implements ICountryService {

    @Autowired
    private final ICountryDAO countryDao;

    public void save(CountryDTO countryDto) throws ServiceExceptionP {
        this.checkNull("generic.null", countryDto);
        this.checkNull("generic.null", countryDto.getName());
        countryDto.setName(AppUtils.convertToTitleCase(countryDto.getName()));
        this.throwExceptionIf(this.countryDao.existsNotDeletedBy(countryDto.getName()), "generic.alreadyExist");
        if (this.countryDao.existsDeletedBy(countryDto.getName())) {
            this.recover(countryDto.getName());
            return;
        }
        this.countryDao.save(countryDto);
    }

    public void delete(String name) throws ServiceExceptionP {
        this.checkNull("generic.null", name);
        name = AppUtils.convertToTitleCase(name);
        this.throwExceptionIf(!this.countryDao.existsNotDeletedBy(name), "generic.notExist");
        this.countryDao.delete(name);
    }

    public CountryDTO update(String name, CountryDTO countryDto) throws ServiceExceptionP {
        this.checkNull("generic.null", name, countryDto);
        this.checkNull("generic.null", countryDto.getName());
        name = AppUtils.convertToTitleCase(name);
        // Verificar existencia del país
        this.throwExceptionIf(!this.countryDao.existsNotDeletedBy(name), "generic.notExist");
        countryDto.setName(AppUtils.convertToTitleCase(countryDto.getName()));
        // Verificar conflicto con el nombre nuevo
        this.throwExceptionIf(this.countryDao.existsNotDeletedBy(countryDto.getName()), "generic.alreadyExist");
        // Si hay un conflicto con un país eliminado, lo recupera
        if (this.countryDao.existsDeletedBy(countryDto.getName())) {
            this.countryDao.recovery(countryDto.getName());
            return countryDto;
        }
        return this.countryDao.update(name, countryDto);
    }

    public CountryDTO find(String name) throws ServiceExceptionP {
        this.checkNull("generic.null", name);
        name = AppUtils.convertToTitleCase(name);
        return this.countryDao.findCountry(name);
    }

    public List<CountryDTO> findAll() throws ServiceExceptionP {
        return this.countryDao.findAll();
    }

    public Boolean existsBy(String name) throws ServiceExceptionP {
        this.checkNull("generic.null", name);
        return this.countryDao.existsBy(name);
    }

    private void recover(String name) throws ServiceExceptionP {
        this.checkNull("generic.null", name);
        this.throwExceptionIf(!this.countryDao.existsDeletedBy(name), "generic.notExist");
        this.countryDao.recovery(name);
    }

}
