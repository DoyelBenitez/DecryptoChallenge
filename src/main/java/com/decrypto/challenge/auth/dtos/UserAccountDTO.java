package com.decrypto.challenge.auth.dtos;

import com.decrypto.challenge.auth._core.mapping.Mapping_UserAccount;
import com.decrypto.challenge.auth.entities.UserAccount;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiRelationships;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common.dto.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonApiType("Usuario")
public class UserAccountDTO extends AbstractDTO implements IDTOMappable<UserAccount> {

    @NotBlank(message = "El username es obligatorio")
    private String username;
    @NotBlank(message = "La password es obligatoria")
    private String password;
    @JsonIgnore
    @JsonApiRelationships("rol")
    private RolDTO role;

    @Override
    public UserAccount basicMapping() {
        return Mapping_UserAccount.basicMapping(this);
    }

    @Override
    public UserAccount fullMapping() {
        return Mapping_UserAccount.fullMapping(this);
    }
}
