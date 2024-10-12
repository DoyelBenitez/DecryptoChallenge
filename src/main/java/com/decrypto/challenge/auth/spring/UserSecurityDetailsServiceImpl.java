package com.decrypto.challenge.auth.spring;

import com.decrypto.challenge.auth.dao.UserAccountDAO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class UserSecurityDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserAccountDAO userAccountDao;

    @Override
    public UserDetails loadUserByUsername(String value) {
        UserAccountDTO userAccountDto = this.userAccountDao.findByUsernameForLogin(value);
        if (userAccountDto == null) {
            throw new UsernameNotFoundException("No se encuentra el usuario: " + value);
        }
        return new CustomUserAccount(userAccountDto);
    }

}
