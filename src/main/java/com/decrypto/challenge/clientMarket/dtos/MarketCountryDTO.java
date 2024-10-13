package com.decrypto.challenge.clientMarket.dtos;

import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common.dto.AbstractDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un país en el sistema.")
@JsonApiType("País")
public class MarketCountryDTO extends AbstractDTO {

    @NotBlank(message = "El nombre del país es obligatorio.")
    @Schema(description = "El nombre del país. Este campo es obligatorio.", example = "Argentina")
    private String name;
}
