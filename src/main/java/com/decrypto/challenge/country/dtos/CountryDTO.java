package com.decrypto.challenge.country.dtos;

import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common.dto.AbstractDTO;
import com.decrypto.challenge.country._core.mapping.Mapping_Country;
import com.decrypto.challenge.country.entities.Country;
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
@Schema(description = "Representa un país en el sistema.")
@JsonApiType("País")
public class CountryDTO extends AbstractDTO implements IDTOMappable<Country> {

    @NotBlank(message = "El nombre del país es obligatorio.")
    @Schema(description = "El nombre del país. Este campo es obligatorio.", example = "Argentina")
    private String name;

    @Override
    public Country basicMapping() {
        return Mapping_Country.basicMapping(this);
    }

    @Override
    public Country fullMapping() {
        return Mapping_Country.fullMapping(this);
    }
}

