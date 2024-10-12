package com.decrypto.challenge.auth.services;

import com.decrypto.challenge.auth.dao.interfaces.IRolDAO;
import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.services.interfaces.IRolService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class RolService extends AbstractService implements IRolService {

    @Autowired
    private final IRolDAO rolDao;

    public void save(RolDTO rolDto) throws ServiceExceptionP {
        this.checkNull("generic.null", rolDto);
        this.checkNull("generic.null", rolDto.getName());
        this.rolDao.save(rolDto);
    }

    public Boolean existsBy(String name) throws ServiceExceptionP {
        this.checkNull("generic.null", name);
        return this.rolDao.existsBy(name);
    }

    public RolDTO findBy(Long id) throws ServiceExceptionP {
        this.checkNull("generic.null", id);
        return this.rolDao.findBy(id);
    }

    public RolDTO findBy(String name) throws ServiceExceptionP {
        this.checkNull("generic.null", name);
        return this.rolDao.findBy(name);
    }
}
