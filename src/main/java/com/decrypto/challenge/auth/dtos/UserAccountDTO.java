package com.decrypto.challenge.auth.dtos;

import com.decrypto.challenge.auth._core.mapping.Mapping_UserAccount;
import com.decrypto.challenge.auth.entities.UserAccount;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiRelationships;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common.dto.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representa la cuenta de un usuario en el sistema.")
public class UserAccountDTO extends AbstractDTO implements IDTOMappable<UserAccount> {

    @NotBlank(message = "El username es obligatorio")
    @Schema(description = "Nombre de usuario único para autenticación.", example = "decrypto")
    private String username;

    @NotBlank(message = "La password es obligatoria")
    @Schema(description = "Contraseña del usuario para autenticación.", example = "challenge")
    private String password;

    @JsonIgnore
    @JsonApiRelationships("rol")
    @Schema(description = "Rol asignado al usuario.", example = "Admin", hidden = true)
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
