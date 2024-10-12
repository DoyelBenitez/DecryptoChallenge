package com.decrypto.challenge.auth._core.mapping;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.entities.UserAccount;
import com.decrypto.challenge.common._core.mapping.Mapping;
import com.decrypto.challenge.common._core.utils.AppUtils;

/**
 * @Author dbenitez
 */
public class Mapping_UserAccount {

    public static UserAccount basicMapping(UserAccountDTO userAccountDto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userAccountDto.getId());
        userAccount.setDeleted(userAccountDto.getDeleted());
        userAccount.setUsername(userAccountDto.getUsername());
        userAccount.setPassword(userAccountDto.getPassword());
        return userAccount;
    }

    public static UserAccount fullMapping(UserAccountDTO userAccountDto) {
        UserAccount userAccount = basicMapping(userAccountDto);
        userAccount.setRole(Mapping.fullMapping(userAccountDto.getRole()));
        return userAccount;
    }

    public static UserAccountDTO basicMapping(UserAccount userAccount) {
        UserAccountDTO userAccountDto = new UserAccountDTO();
        userAccountDto.setId(userAccount.getId());
        userAccountDto.setUsername(userAccount.getUsername());
        return userAccountDto;
    }

    public static UserAccountDTO fullMapping(UserAccount userAccount) {
        UserAccountDTO userAccountDto = basicMapping(userAccount);
        userAccountDto.setRole(Mapping.fullMapping(userAccount.getRole()));
        return userAccountDto;
    }

    public static UserAccountDTO basicMappingForLogIn(UserAccount userAccount) {
        UserAccountDTO userAccountDto = new UserAccountDTO();
        userAccountDto.setId(userAccount.getId());
        userAccountDto.setDeleted(userAccount.getDeleted());
        userAccountDto.setUsername(userAccount.getUsername());
        userAccountDto.setPassword(userAccount.getPassword());
        return userAccountDto;
    }

    public static UserAccountDTO fullMappingForLogIn(UserAccount userAccount) {
        if (AppUtils.isNotNullOrEmpty(userAccount)) {
            UserAccountDTO userAccountDto = basicMappingForLogIn(userAccount);
            userAccountDto.setRole(Mapping.fullMapping(userAccount.getRole()));
            return userAccountDto;
        }
        return null;
    }
}
