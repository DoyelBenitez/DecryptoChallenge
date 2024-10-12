package com.decrypto.challenge.auth.entities;

import com.decrypto.challenge.auth._core.mapping.Mapping_UserAccount;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.common._core.mapping.IEntityMappable;
import com.decrypto.challenge.common.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserAccount")
public class UserAccount extends AbstractEntity implements IEntityMappable<UserAccountDTO> {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    private Rol role;

    @Override
    public UserAccountDTO basicMapping() {
        return Mapping_UserAccount.basicMapping(this);
    }

    @Override
    public UserAccountDTO fullMapping() {
        return Mapping_UserAccount.fullMapping(this);
    }
}
