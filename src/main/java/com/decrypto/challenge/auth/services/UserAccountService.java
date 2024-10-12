package com.decrypto.challenge.auth.services;

import com.decrypto.challenge.auth.dao.interfaces.IUserAccountDAO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.services.interfaces.IUserAccountService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class UserAccountService extends AbstractService implements IUserAccountService {

    @Autowired
    private final IUserAccountDAO userAccountDAO;
    @Autowired
    private final BCryptPasswordEncoder bCrypt;

    public void save(UserAccountDTO userAccountDto) throws ServiceExceptionP {
        this.checkNull("generic.null", userAccountDto);
        this.checkNull("generic.null", userAccountDto.getUsername(), userAccountDto.getPassword(), userAccountDto.getRole());
        this.checkFormatUsername(userAccountDto);
        userAccountDto.setPassword(this.bCrypt.encode(userAccountDto.getPassword()));
        this.userAccountDAO.save(userAccountDto);
    }

    public Boolean existBy(String username) throws ServiceExceptionP {
        this.checkNull("generic.null", username);
        return this.userAccountDAO.existBy(username);
    }

    public UserAccountDTO findByUsername(String username) throws ServiceExceptionP {
        this.checkNull("generic.null", username);
        return this.userAccountDAO.findByUsername(username);
    }

    private void checkFormatUsername(UserAccountDTO userAccountDto) throws ServiceExceptionP {
        this.throwExceptionIf(!userAccountDto.getUsername().matches("^[a-zA-Z0-9._]+$"), "auth.username.format");
    }

}
