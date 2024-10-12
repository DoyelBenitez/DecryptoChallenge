package com.decrypto.challenge.auth.services;

import com.decrypto.challenge.auth._core.enumerative.TokenExpirationEnum;
import com.decrypto.challenge.auth._core.providers.JwtTokenProvider;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.services.interfaces.IAuthService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class AuthService extends AbstractService implements IAuthService {

    @Autowired
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> signIn(UserAccountDTO userAccountDto) throws ServiceExceptionP {
        this.checkNull("generic.null", userAccountDto);
        this.checkNull("generic.null", userAccountDto.getUsername(), userAccountDto.getPassword());
        UsernamePasswordAuthenticationToken springUserAccount = new UsernamePasswordAuthenticationToken(userAccountDto.getUsername(), userAccountDto.getPassword());
        Authentication authenticatedUser = this.authenticationManager.authenticate(springUserAccount);
        String token = this.createTokenJwt(authenticatedUser);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        return result;
    }

    private String createTokenJwt(Authentication authentication){
        return JwtTokenProvider.generateToken(authentication, TokenExpirationEnum.SEVEN_DAYS.getExpirationMillis(), true);
    }
}
